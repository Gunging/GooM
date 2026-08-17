package gunging.ootilities.GungingOotilitiesMod.commands.core.building;

import gunging.ootilities.GungingOotilitiesMod.GungingOotilitiesMod;
import gunging.ootilities.GungingOotilitiesMod.commands.core.parsing.GCPContextOptions;
import gunging.ootilities.GungingOotilitiesMod.commands.friendly.FriendlyFeedbackProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

/**
 * A node that terminates the command tree, with
 * actual arguments that actually does something
 *
 * @author Gunging
 * @since 1.0.0
 */
public abstract class GCMCommandNode extends GCMNode {

    /**
     * A nice "display name" for this command
     *
     * @since 1.0.0
     */
    @NotNull final String commandName;

    /**
     * A concise description of this command, without
     * describing the arguments. The arguments are
     * described later.
     *
     * @since 1.0.0
     */
    @NotNull final String commandDescription;

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public @NotNull String getCommandDescription() { return commandDescription; }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public @NotNull String getCommandName() { return commandName; }

    /**
     * @param keyword The argument in the command line that represents this node
     * @param commandName Name of this command
     * @param commandDescription Short description of this command
     *
     * @author Gunging
     * @since 1.0.0
     */
    public GCMCommandNode(@NotNull String keyword, @NotNull String commandName, @NotNull String commandDescription) {
        super(keyword);
        this.commandName = commandName;
        this.commandDescription = commandDescription;
    }

    /**
     * The arguments this command admits, it must be possible to reduce them to a single string
     *
     * @since 1.0.0
     */
    @NotNull HashMap<String, GCMExpectedArgument<?>> arguments = new HashMap<>();

    /**
     * @param arg The argument to include. Positional, the order matters.
     *
     * @author Gunging
     * @since 1.0.0
     */
    public void addArgument(@NotNull GCMExpectedArgument<?> arg) {
        if (arguments.containsKey(arg.getArgumentKeyword())) {
            GungingOotilitiesMod.Log("GCMCommandNode.addArgument(GCMExpectedArgument) Error: Repeated command argument keyword [" + arg.getArgumentKeyword() + "]");
            return;
        }

        // A single non-standalone argument will complicate tab completion
        if (!(arg instanceof GCMStandaloneArgument<?>)) { complexAutocompleteArguments = true; }

        arguments.put(arg.getArgumentKeyword(), arg);
        arg.setIndex(arguments.size() - 1);
        if (arg.isOptional()) { optionalArgsCount++; }

        /*
         * It really shouldn't matter if I put it here, despite being computationally
         * wasteful. Otherwise, it has to be called in every command that inherits
         * this after they finish adding their arguments and that's just annoying in
         * the long term.
         */
        prepArgumentsByIndex();
    }

    /**
     * The arguments list but ordered by index
     *
     * @since 1.0.0
     */
    @NotNull GCMExpectedArgument<?>[] argumentsByIndex = new GCMExpectedArgument[0];

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public GCMExpectedArgument<?>[] getArgumentsByIndex() { return argumentsByIndex; }

    /**
     * When a user provides a command, the presence of optional arguments
     * obscures the later arguments into ambiguity: <br>
     * <code>/command input0 input2</code><br><br>
     * How would you know arg1 was not provided? We won't until we try
     * to actually parse the command, which is computationally wasteful
     * for Tab Completion at least. Then, it is useful to know the degree
     * of ambiguity of the later arguments, in this case 1 because only
     * arg1 was optional, then input2 may be suitable for arg 1 and 2.
     *
     * @param upToArgument The index of the argument up to which to tally the deviation
     *
     * @return The number of optional arguments up to this point.
     *
     * @author Gunging
     * @since 1.0.0
     */
    public int countOptionalDeviation(int upToArgument) {

        // Stating with no deviation
        int ret = 0;

        // Check every argument by their index
        for (int i = 0; i <= upToArgument; i++) {

            // Any optional argument increases deviation
            if (getArgumentsByIndex()[i].isOptional()) {
                ret++;
            } }

        // That's the result
        return ret;
    }

    /**
     * @return If parsing for Tab Complete is computationally expensive
     *         or for whatever reason must be avoided. If so, the
     *         tab complete suggestions may confuse optional arguments.
     *
     * @author Gunging
     * @since 1.0.0
     */
    public boolean doNotParseForTabComplete() { return false; }

    /**
     * Recreates the Arguments By Index array
     *
     * @author Gunging
     * @since 1.0.0
     */
    public void prepArgumentsByIndex() {

        // Reset the array
        argumentsByIndex = new GCMExpectedArgument[arguments.size()];
        for (GCMExpectedArgument<?> arg : arguments.values()) { argumentsByIndex[arg.getIndex()] = arg; }
    }

    /**
     * If this command has at least one argument that depends
     * on a previous argument to provide tab complete suggestions
     *
     * @since 1.0.0
     */
    boolean complexAutocompleteArguments;

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public boolean hasComplexAutocompleteArguments() { return complexAutocompleteArguments; }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public HashMap<String, GCMExpectedArgument<?>> getArguments() { return arguments; }

    /**
     * The number of optional arguments in this command
     *
     * @since 1.0.0
     */
    int optionalArgsCount;

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public int getOptionalArgsCount() { return optionalArgsCount; }

    /**
     * Executes this command based on these inputs
     *
     * @param args The arguments that follow, where the ZEROTH argument is
     *             this node itself. Only command nodes will get more than
     *             2 elements in this array, whereas all other nodes have
     *             two elements where the last argument is the following node
     *
     * @param ffp The feedback provider stack
     *
     * @return The output of this operation, <code>null</code> if and only if it failed
     *
     * @author Gunging
     * @since 1.0.0
     */
    @Nullable public abstract String execute(@NotNull GCPContextOptions source, @NotNull String[] args, @Nullable FriendlyFeedbackProvider ffp);
}
