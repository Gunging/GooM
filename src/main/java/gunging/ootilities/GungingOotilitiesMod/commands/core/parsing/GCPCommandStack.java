package gunging.ootilities.GungingOotilitiesMod.commands.core.parsing;

import gunging.ootilities.GungingOotilitiesMod.GungingOotilitiesMod;
import gunging.ootilities.GungingOotilitiesMod.commands.core.building.GCMCommandNode;
import gunging.ootilities.GungingOotilitiesMod.ootilityception.OotilityNumbers;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * A fully-processed Argument Stack, considering that now
 * knowing the command it encodes for the arguments for
 * the command were interpreted. The arguments may not
 * be valid tho, but with this we already know if they are.
 *
 * @author Gunging
 * @since 1.0.0
 */
public class GCPCommandStack extends GCPArgumentStack {

    /**
     * @param command The command that will be executing these arguments
     * @param args The arguments to feed the command
     * @param explicit The full command sent by the user.
     *
     * @author Gunging
     * @since 1.0.0
     */
    public GCPCommandStack(@NotNull GCMCommandNode command, @NotNull GCPArgumentStack args, @Nullable GCPArgumentStack explicit) {
        super(args);
        this.command = command;
        this.explicit = explicit;
    }

    /**
     * @param successfulSearch A search stack that succeeded in finding a command.
     *
     * @author Gunging
     * @since 1.0.0
     */
    @SuppressWarnings("DataFlowIssue")
    public GCPCommandStack(@NotNull GCPSearchedStack successfulSearch) {
        super(successfulSearch.commandArguments);
        assert successfulSearch.foundCommand() : "This constructor REQUIRES a successfully-searched stack";
        this.command = successfulSearch.command;
        this.explicit = successfulSearch;
        this.options = successfulSearch.getOptions().clone();
    }

    /**
     * The command that will be executing these arguments
     *
     * @since 1.0.0
     */
    @NotNull final GCMCommandNode command;

    /**
     * The command that was sent by the user. Optional.
     *
     * @since 1.0.0
     */
    @Nullable final GCPArgumentStack explicit;

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public @Nullable GCPArgumentStack getExplicit() {
        return explicit;
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public @NotNull GCMCommandNode getCommand() {
        return command;
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public int getOptionalCredits() {
        return optionalCredits;
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public void setOptionalCredits(int optionalCredits) {
        this.optionalCredits = optionalCredits;
    }

    /**
     * How many optional arguments must resolve for this
     * command to make sense.
     * <br><br>
     * Suppose the maximum number of arguments is 8, out
     * of which 3 are optional. Then the user provides 6
     * arguments, you know 2 optional arguments were omitted,
     * this [2] is the number of optional argument credits.
     *
     * @since 1.0.0
     */
    int optionalCredits;

    /**
     * The special case when no optional arguments are provided
     * means all optional argument checks are discarded, since
     * by definition none of them were provided.
     *
     * @return If only the required arguments were provided
     *
     * @author Gunging
     * @since 1.0.0
     */
    public boolean isConfirmedNoOptionals() { return !isAmbiguousOptionals() && (getCommand().getOptionalArgsCount() <= optionalCredits); }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public @Nullable String getLowParsingError() { return lowParsingError; }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public void setLowParsingError(@Nullable String lowParsingError) { this.lowParsingError = lowParsingError; }

    /**
     * A string resulting from fundamental failures to parsing
     * the command, such as having the wrong number of args.
     * <br><br>
     * This being present means the arguments won't even try to
     * read the command, it is bad from the beginning.
     *
     * @since 1.0.0
     */
    @Nullable String lowParsingError;

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public int getOptionalsDefaulted() { return optionalsDefaulted; }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public void setOptionalsDefaulted(int optionalsDefaulted) { this.optionalsDefaulted = optionalsDefaulted; }

    /**
     * The number of optional arguments that have been assumed
     * so far to shift the future indices of expected arguments.
     *
     * @since 1.0.0
     */
    int optionalsDefaulted;

    /**
     * Called when an optional argument is assumed during argument parsing
     *
     * @author Gunging
     * @since 1.0.0
     */
    public void consumeOptionalCredit() {

        // This should never happen
        if (optionalCredits < 0) {
            GungingOotilitiesMod.Log("GCPCommandStack.consumeOptionalCredit() ERROR: No more credits should be possibly consumed in /" + OotilityNumbers.collapseList(new ArrayList<>(List.of(getCommand().argsToFullPath(new String[] { getCommand().getKeyword() } ))), " ") + " ");
        }

        // Consume
        optionalsDefaulted++;
        optionalCredits--;
    }

    /**
     * When TRUE, optionals may parse even when they couldn't possibly be provided because
     * the optional credits equal the maximum optional credits. This is important for
     * effective tab-completing, since some arguments may just not be provided yet
     *
     * @since 1.0.0
     */
    boolean ambiguousOptionals;

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public boolean isAmbiguousOptionals() { return ambiguousOptionals; }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public void setAmbiguousOptionals(boolean ambiguous) { this.ambiguousOptionals = ambiguous; }

    /**
     * If, when reading the arguments, any of them failed.
     * If so this command will not be able to execute.
     *
     * @since 1.0.0
     */
    boolean failure;

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public boolean isFailure() { return failure; }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public void setFailure(boolean fail) { this.failure = fail; }
}
