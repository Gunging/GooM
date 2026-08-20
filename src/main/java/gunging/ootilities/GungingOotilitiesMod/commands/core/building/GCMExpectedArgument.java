package gunging.ootilities.GungingOotilitiesMod.commands.core.building;

import gunging.ootilities.GungingOotilitiesMod.commands.core.parsing.GCPCommandStack;
import gunging.ootilities.GungingOotilitiesMod.commands.core.parsing.GCPProvidedArgument;
import gunging.ootilities.GungingOotilitiesMod.commands.friendly.FriendlyFeedbackProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

/**
 * A single argument of a command. In general this
 * will stick to itself, but sometimes arguments
 * depend on prior arguments.
 *
 * @param <Value> The object class of the parsed argument
 *
 * @author Gunging
 * @since 1.0.0
 */
public abstract class GCMExpectedArgument<Value> {

    /**
     * The name of this argument
     *
     * @since 1.0.0
     */
    @NotNull final String argumentKeyword;

    /**
     * A short description of this argument
     *
     * @since 1.0.0
     */
    @NotNull final String argumentDescription;

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public boolean isOptional() { return optional; }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public @NotNull String getArgumentDescription() { return argumentDescription; }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public @NotNull String getArgumentKeyword() { return argumentKeyword; }

    /**
     * Whether this argument may be omitted
     *
     * @since 1.0.0
     */
    boolean optional;

    /**
     * If this is optional, the default value of this argument
     *
     * @since 1.0.0
     */
    @Nullable Value defaultValue;

    /**
     * @param argumentKeyword The name of this argument
     * @param argumentDescription A short description of this argument
     *
     * @author Gunging
     * @since 1.0.0
     */
    public GCMExpectedArgument(@NotNull String argumentKeyword, @NotNull String argumentDescription) {
        this.argumentKeyword = argumentKeyword;
        this.argumentDescription = argumentDescription;
    }

    /**
     * Providing a default value will mark this argument as optional
     *
     * @param def The default value to set for this.
     *
     * @return This same Expected Argument object, like a builder pattern.
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public GCMExpectedArgument<Value> withDefaultValue(@Nullable Value def) {
        this.defaultValue = def;
        optional = true;
        return this;
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Nullable public Value getDefaultValue() { return defaultValue; }

    /**
     * The argument index of this expected argument in its command
     *
     * @since 1.0.0
     */
    int index;

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public void setIndex(int i) { this.index = i; }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public int getIndex() { return index; }

    /**
     * This method will preferably not be used in favour
     * of the one with context, but sometimes there is
     * no choice
     *
     * @return Suggestions that require no context.
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public abstract ArrayList<String> getUbiquitousSuggestions();

    /**
     * @return Suggestions to tab-complete based on existing context
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public abstract ArrayList<String> getSuggestions(@NotNull GCPCommandStack stack);

    /**
     * Reads this argument WITHOUT shifting indices in the case of optional arguments.
     * <b>Use this only if you really know what you are doing.</b>
     *
     * @param stack The arguments provided to this command.
     *
     * @return This input, wrapped in an argument provider that can interpret it
     *
     * @see #read(GCPCommandStack)
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public abstract GCPProvidedArgument<Value> provide(@NotNull GCPCommandStack stack);

    /**
     * Provided a command stack, it finds the Explicit Argument pertaining to me.
     * <br><br>
     * It is important to use this method since it shifts its index appropriately
     * compared to the optional arguments that have been omitted.
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public GCPProvidedArgument<?> getMyExplicit(@NotNull GCPCommandStack stack) {
        return stack.get(getIndex() - stack.getOptionalsDefaulted());
    }

    /**
     * Apart from providing the input, it will handle the shifting of indices
     * characteristic of optional arguments. This is the correct way of reading
     * arguments.
     *
     * @param stack The arguments provided to this command.
     *
     * @return This input, wrapped in an argument provider that can interpret it
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public GCPProvidedArgument<Value> read(@NotNull GCPCommandStack stack) {

        // Provide
        GCPProvidedArgument<Value> ret = provide(stack);
        ret.setIdentified(this);

        // Inscribe index changes due to optional omitted arguments
        if (isOptional()) {

            // Was this argument omitted? Consume an optional credit and absorb error
            // Or perhaps, we know for sure this argument was NOT provided
            if (stack.isConfirmedNoOptionals() || ((stack.getOptionalCredits() > 0) && (!ret.isSuccessful()))) {

                // Absorb error, set default value
                ret.setParsed(getDefaultValue());
                ret.setParsingError(null);
                ret.setDefaulted(true);

                // Account for this exception in future index pulls
                stack.consumeOptionalCredit();
            }
        }

        // That is the result
        return ret;
    }

    /**
     * @return Returns the {@link #getArgumentKeyword()} of this argument in
     * square brackets if optional, or in angles if required.
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public String forSyntaxDisplay() {
        return isOptional() ? "[" + getArgumentKeyword() + "]" : "<" + getArgumentKeyword() + ">";
    }

    /**
     * Returns an argument, succeeds only if it originally parsed right and was not null.
     * <br><br>
     * <b>Absolutely important to check {@link GCPCommandStack#isFailure()} before accessing expected arguments returned from this. </b>
     *
     * @param stack The command stack to read this from
     * @param ffp Friendly Feedback Provider to write the error onto
     *
     * @return Reads the value in here, fails the stack if it was null
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public Value expected(@NotNull GCPCommandStack stack, @Nullable FriendlyFeedbackProvider ffp) {

        // Read
        GCPProvidedArgument<Value> parsed = read(stack);

        // Force a generic error message I guess
        if (parsed.getParsed() == null && parsed.getParsingError() == null) {
            parsed.setParsingError("$bRequired argument $e" + getArgumentKeyword() + "$b missing from '" + parsed.getExplicit() + "'. "); }

        // Log any parsing errors that occurred
        if (parsed.getParsingError() != null) {

            // Log as failure when soft
            if (parsed.parsingErrorIsSoft()) {
                FriendlyFeedbackProvider.logFailure(ffp, parsed.getParsingError());

            // Log as error when hard
            } else {
                FriendlyFeedbackProvider.logError(ffp, parsed.getParsingError()); }

            // Damn! I can't believe it is returning null to a non-null!
            stack.setFailure(true);
            return null;
        }

        // Okay now, no error and successful that's sold
        return parsed.getParsed();
    }

    /**
     * Returns an argument, and even if the argument failed to parse it can succeed if a non-null "stock" was
     * available to return instead. If so, the parsing error is ignored and the stock is used successfully-
     * <br><br>
     * <b>Absolutely important to check {@link GCPCommandStack#isFailure()} before accessing expected arguments returned from this. </b>
     *
     * @param stack The command stack to read this from
     *
     * @param stock If the parsed value was null, replace it with this and the command will succeed.
     *              If this is also null then the command will ultimately fail though.
     *
     * @param ffp Friendly Feedback Provider to write the error onto
     *
     * @return Reads the value in here, fails the stack if it was null
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public Value stocked(@NotNull GCPCommandStack stack, @Nullable Value stock, @Nullable FriendlyFeedbackProvider ffp) {

        // Read
        GCPProvidedArgument<Value> parsed = read(stack);

        // If the stock may be used to alter fate
        if (stock != null && parsed.getParsed() == null) {

            // Accept stock and absorb any error
            parsed.setParsed(stock);
            parsed.setParsingError(null); }

        // Force a generic error message if still invalid
        if (parsed.getParsed() == null && parsed.getParsingError() == null) {
            parsed.setParsingError("$bRequired argument $e" + getArgumentKeyword() + "$b missing from '" + parsed.getExplicit() + "'. "); }

        // Log any parsing errors that occurred
        if (parsed.getParsingError() != null) {

            // Log as failure when soft
            if (parsed.parsingErrorIsSoft()) {
                FriendlyFeedbackProvider.logFailure(ffp, parsed.getParsingError());

                // Log as error when hard
            } else {
                FriendlyFeedbackProvider.logError(ffp, parsed.getParsingError()); }

            // Damn! I can't believe it is returning null to a non-null!
            stack.setFailure(true);
            return null;
        }

        // Okay now, no error and successful that's sold
        return parsed.getParsed();
    }

    /**
     * Returns an argument, which may have purposedly parsed/defaulted to "null" without it being a parsing error,
     * as in, it is intended behaviour for it to become "null" in this situation. If the value is null and no parsing
     * error was generated, the supplied value is used instead. However, in case of parsing errors, the supplied value
     * is ignored even if it had fitted.
     * <br><br>
     * <b>Absolutely important to check {@link GCPCommandStack#isFailure()} before accessing expected arguments returned from this. </b>
     *
     * @param stack The command stack to read this from
     *
     * @param supply If the parsed value was null, but not as a result of a parsing error,
     *              replace it with this and the command will succeed.
     *              If this is also null then the command will ultimately fail though.
     *
     * @param ffp Friendly Feedback Provider to write the error onto
     *
     * @return Reads the value in here, fails the stack if it was null
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public Value supplied(@NotNull GCPCommandStack stack, @Nullable Value supply, @Nullable FriendlyFeedbackProvider ffp) {

        // Read
        GCPProvidedArgument<Value> parsed = read(stack);

        // If the stock may be used to alter fate
        if (supply != null && parsed.getParsed() == null && parsed.isSuccessful()) {

            // Accept stock and absorb any error
            parsed.setParsed(supply);
            parsed.setParsingError(null); }

        // Force a generic error message if still invalid
        if (parsed.getParsed() == null && parsed.getParsingError() == null) {
            parsed.setParsingError("$bRequired argument $e" + getArgumentKeyword() + "$b missing from '" + parsed.getExplicit() + "'. "); }

        // Log any parsing errors that occurred
        if (parsed.getParsingError() != null) {

            // Log as failure when soft
            if (parsed.parsingErrorIsSoft()) {
                FriendlyFeedbackProvider.logFailure(ffp, parsed.getParsingError());

                // Log as error when hard
            } else {
                FriendlyFeedbackProvider.logError(ffp, parsed.getParsingError()); }

            // Damn! I can't believe it is returning null to a non-null!
            stack.setFailure(true);
            return null;
        }

        // Okay now, no error and successful that's sold
        return parsed.getParsed();
    }
}
