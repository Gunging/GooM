package gunging.ootilities.GungingOotilitiesMod.commands.core.parsing;

import gunging.ootilities.GungingOotilitiesMod.commands.core.building.GCMExpectedArgument;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A single argument provided by the user. This
 * is at its core a string of text, mabe with
 * a little more context.
 *
 * @author Gunging
 * @since 1.0.0
 */
public class GCPProvidedArgument<Value> {

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public @NotNull String getExplicit() { return explicit; }

    /**
     * The argument provided by the user, as-is
     *
     * @since 1.0.0
     */
    @NotNull final String explicit;

    /**
     * If the value of this argument was provided, not by the user,
     * but assumed by default due to this being an optional argument
     *
     * @since 1.0.0
     */
    boolean defaulted;

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public boolean isDefaulted() { return defaulted; }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public void setDefaulted(boolean defaulted) {
        this.defaulted = defaulted;
    }

    /**
     * @param explicit The text provided by the user
     *
     * @author Gunging
     * @since 1.0.0
     */
    public GCPProvidedArgument(@NotNull String explicit) { this.explicit = explicit; }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public void setParsed(@Nullable Value parsed) { this.parsed = parsed; }

    /**
     * he explicit value provided here, but parsed
     *
     * @since 1.0.0
     */
    @Nullable Value parsed;

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Nullable public Value getParsed() { return parsed; }

    /**
     * The Command Argument that has bid its claim over this Provided Argument
     *
     * @since 1.0.0
     */
    @Nullable GCMExpectedArgument<Value> identified;

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public @Nullable GCMExpectedArgument<Value> getIdentified() { return identified; }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public void setIdentified(@Nullable GCMExpectedArgument<Value> identified) { this.identified = identified; }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public @Nullable String getParsingError() { return parsingError; }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public void setParsingError(@Nullable String error) { this.parsingError = error; }

    /**
     * Sometimes, parsing errors are not hard "errors" but just
     * bad based on context. An error is supposed to be guaranteed
     * to never succeed, and therefore invalidates the entire command.
     * <br><br>
     * In the event that the current syntax error is not inevitably
     * an error, and can succeed if the circumstances were a bit better
     * without the user changing the input, then it is a <b>soft</b> failure
     * and this should return true.
     *
     * @return If this same input can succeed, even if this time it failed
     *         to parse. As in, this same argument the user wrote has a
     *         possibility of succeeding without being modified.
     *
     * @author Gunging
     * @since 1.0.0
     */
    public boolean parsingErrorIsSoft() { return false; }

    /**
     * Errors that resulted from trying to parse a
     * value provided by the user. If this argument
     * is optional, this error will be ignored and
     * the default value will be used... at the cost
     * of consuming one of the optional argument credits
     * of the Command Stack.
     *
     * @since 1.0.0
     */
    @Nullable String parsingError;

    /**
     * @return If the explicit value could be parsed successfully
     *
     * @author Gunging
     * @since 1.0.0
     */
    public boolean isSuccessful() { return parsingError == null; }
}
