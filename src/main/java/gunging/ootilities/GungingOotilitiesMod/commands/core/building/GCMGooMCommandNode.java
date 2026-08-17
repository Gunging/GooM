package gunging.ootilities.GungingOotilitiesMod.commands.core.building;

import gunging.ootilities.GungingOotilitiesMod.commands.core.parsing.*;
import gunging.ootilities.GungingOotilitiesMod.commands.forge.GCCCommandRegistry;
import gunging.ootilities.GungingOotilitiesMod.commands.friendly.FriendlyFeedbackCategory;
import gunging.ootilities.GungingOotilitiesMod.commands.friendly.FriendlyFeedbackProvider;
import gunging.ootilities.GungingOotilitiesMod.ootilityception.OotilityNumbers;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

/**
 * A command node that follows the standard GooM Command implementation
 *
 * @author Gunging
 * @since 1.0.0
 */
public abstract class GCMGooMCommandNode extends GCMCommandNode {

    /**
     * @param keyword               The argument in the command line that represents this node
     * @param commandName           Name of this command
     * @param commandDescription    Short description of this command
     *
     * @author Gunging
     * @since 1.0.0
     */
    public GCMGooMCommandNode(@NotNull String keyword, @NotNull String commandName, @NotNull String commandDescription) {
        super(keyword, commandName, commandDescription);
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @NotNull ArrayList<String> tabComplete(@NotNull GCPContextOptions source, @NotNull String[] args) {

        // Cheaper less-accurate method
        if (doNotParseForTabComplete()) {

            // Calculate args to suggest
            int minimalCurrentArg = args.length - 2;
            int optionalDissonance = countOptionalDeviation(minimalCurrentArg);
            int maximumCurrentArg = minimalCurrentArg + optionalDissonance;

            // Suggest those argument suggestions
            ArrayList<String> ret = new ArrayList<>();
            for (int i = minimalCurrentArg; i <= maximumCurrentArg; i++) {
                GCMExpectedArgument<?> exArgument = getArgumentsByIndex()[i];
                ret.addAll(exArgument.getUbiquitousSuggestions());
            }

            return ret;
        }

        /*
         * We must interpret this command to correctly suggest
         * args past the ambiguity resulting from optional args
         */
        GCPCommandInterpreter interpreter = new GCPCommandInterpreter();
        GCPArgumentStack transformed = interpreter.transform(argsToFullPath(args));
        GCPArgumentStack interpreted = interpreter.interpret(getRoot(), transformed);
        if (!(interpreted instanceof GCPCommandStack)) {
            ArrayList<String> ret = new ArrayList<>();
            ret.add("<GCMGooMCommandNode Fatal Error>");
            return ret; }
        GCPCommandStack stacked = (GCPCommandStack) interpreted;
        stacked.getOptions().withContext(source);
        stacked.setOptionalCredits(getArguments().size() - stacked.size());

        // Subtract 1 due to arrays being ZERO indexed, and another 1 due to the command keyword
        int tabCompletedArgIndex = args.length - 2;
        for (int i = 0; i < tabCompletedArgIndex; i++) {

            // Attempt to read every prior argument
            GCMExpectedArgument<?> pastArgument = getArgumentsByIndex()[i];
            GCPProvidedArgument<?> parsed = pastArgument.read(stacked); // This also shifts indices appropriately

            // This optional argument was not provided, then we are in a future argument
            if (parsed.isDefaulted()) { tabCompletedArgIndex++; }
        }

        // Suggest all arguments up to the next non-optional one
        ArrayList<String> ret = new ArrayList<>();
        for (int i = tabCompletedArgIndex; i < getArgumentsByIndex().length; i++) {
            GCMExpectedArgument<?> exArgument = getArgumentsByIndex()[i];
            ret.addAll(exArgument.getSuggestions(stacked));
            if (!exArgument.isOptional()) { break; }
        }

        return ret;
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @Nullable String execute(@NotNull GCPContextOptions source, @NotNull String[] args, @Nullable FriendlyFeedbackProvider ffp) {
        return "";
    }

    /**
     * This represents quick preliminary checks, such as the number
     * of arguments making sense, before actually reading the arguments.
     * <br><br>
     * Also starts up the necessary variables to accommodate optional args.
     *
     * @param stack Arguments provided to this command
     *
     * @author Gunging
     * @since 1.0.0
     */
    public void prepareExecute(@NotNull GCPCommandStack stack) {

        // Preliminary check for good arguments
        int minimumRequiredArguments = getArguments().size() - getOptionalArgsCount();
        int maximumArguments = getArguments().size();
        if (minimumRequiredArguments > stack.size()) {
            stack.setLowParsingError("Not enough arguments. Received " + stack.size() + ", expected a minimum of " + minimumRequiredArguments + ". ");
            return; }
        if (maximumArguments < stack.size()) {
            stack.setLowParsingError("Too many arguments. Received " + stack.size() + ", expected a maximum of " + maximumArguments + ". ");
            return; }

        // Math that involves optional args
        if (getOptionalArgsCount() > 0) {

            // Calculate the number of optional arguments we expect
            stack.setOptionalCredits(maximumArguments - stack.size());
        }
    }

    /**
     * Executes this command based on these inputs
     *
     * @param stack Arguments provided to this command
     * @param ffp The feedback provider stack
     *
     * @return The output of this operation, <code>null</code> if and only if it failed
     *
     * @author Gunging
     * @since 1.0.0
     */
    @Nullable public abstract String execute(@NotNull GCPCommandStack stack, @Nullable FriendlyFeedbackProvider ffp);

    /**
     * @param subdivision The prefix subdivision for this command
     * @param shortDescription A very concise description of what this does
     * @param longDescription A more elaborate description paragraph to be chopped.
     *
     * @author Gunging
     * @since 1.0.0
     */
    public void buildHelp(@NotNull String subdivision, @NotNull String shortDescription, @NotNull String longDescription) {

        // Build /help
        getHelp().activatePrefix(true, subdivision);
        getHelp().log(FriendlyFeedbackCategory.INFORMATION, shortDescription);

        // Summarize syntax
        getHelp().activatePrefix(true, null);
        StringBuilder syntax = new StringBuilder("$r/... ");
        syntax.append(getKeyword());
        for (GCMExpectedArgument<?> argument : getArgumentsByIndex()) { syntax.append(" ").append(argument.forSyntaxDisplay()); }
        getHelp().log(FriendlyFeedbackCategory.INFORMATION, syntax.toString());

        // Add long description
        getHelp().activatePrefix(false, null);
        for (String helpLine : OotilityNumbers.chop(longDescription, GCCCommandRegistry.HELP_PARAGRAPH_WIDTH, "$b")) {
            getHelp().log(FriendlyFeedbackCategory.INFORMATION, helpLine);
        }

        // Describe arguments
        for (GCMExpectedArgument<?> argument : getArgumentsByIndex()) {
            getHelp().log(FriendlyFeedbackCategory.INFORMATION, "$u  " + argument.forSyntaxDisplay() + "$b " + argument.getArgumentDescription());
        }
    }
}
