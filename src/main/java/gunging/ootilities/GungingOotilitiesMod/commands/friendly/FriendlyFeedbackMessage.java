package gunging.ootilities.GungingOotilitiesMod.commands.friendly;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A wrapper class for a string to contain a bit more of
 * information about the style it shall follow.
 *
 * @author Gunging
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class FriendlyFeedbackMessage implements Cloneable {

    /**
     * The message wrapped herein, before parsing color codes.
     *
     * @since 1.0.0
     */
    @NotNull String message;

    /**
     * Whether to prepend the branding prefix when
     * executing this message's format.
     *
     * @since 1.0.0
     */
    boolean withPrefix = false;

    /**
     * Clones this <code>FriendlyFeedbackMessage</code>.
     *
     * @author Gunging
     * @since 1.0.0
     */
    @Override public FriendlyFeedbackMessage clone() {
        try { super.clone(); } catch (CloneNotSupportedException ignored) {}
        return new FriendlyFeedbackMessage(getMessage(), hasPrefix(), getSubdivision());
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public void setMessage(@NotNull String message) { this.message = message; }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public void usesPrefix(boolean usePrefix) {
        this.withPrefix = usePrefix;
    }

    /**
     * @see #hasPrefix()
     *
     * @author Gunging
     * @since 1.0.0
     */
    public void setSubdivision(@Nullable String subdivision) { this.withSubdivision = subdivision; }

    /**
     * If using prefix, this will be some extra
     * keyword included in the prefix. Very nice!
     *
     * @since 1.0.0
     */
    @Nullable String withSubdivision = null;

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public String getMessage() { return message; }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public boolean hasPrefix() { return withPrefix; }

    /**
     * @see #hasPrefix()
     *
     * @author Gunging
     * @since 1.0.0
     */
    @Nullable public String getSubdivision() { return withSubdivision; }

    /**
     * @param message The message wrapped herein, before parsing color codes.
     *
     * @author Gunging
     * @since 1.0.0
     */
    public FriendlyFeedbackMessage(@NotNull String message) {
        this(message, false, null);
    }

    /**
     * @param message The message wrapped herein, before parsing color codes.
     * @param subdivision Additional keyword to include with the prefix. Enables prefix.
     *
     * @author Gunging
     * @since 1.0.0
     */
    public FriendlyFeedbackMessage(@NotNull String message, @Nullable String subdivision) {
        this(message, true, subdivision);
    }

    /**
     * @param message The message wrapped herein, before parsing color codes.
     * @param usePrefix If prefix of this message should be enabled
     *
     * @author Gunging
     * @since 1.0.0
     */
    public FriendlyFeedbackMessage(@NotNull String message, boolean usePrefix) {
        this(message, usePrefix, null);
    }

    /**
     * @param message The message wrapped herein, before parsing color codes.
     * @param usePrefix If prefix of this message should be enabled
     * @param subdivision Additional keyword to include with the prefix
     *
     * @author Gunging
     * @since 1.0.0
     */
    public FriendlyFeedbackMessage(@NotNull String message, boolean usePrefix, @Nullable String subdivision) {
        this.message = message;
        withPrefix = usePrefix;
        withSubdivision = subdivision;
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override public String toString() { return getMessage(); }

    /**
     * Parses a message intended to be read in-game.
     * Basically supporting HEX codes in 1.16+
     * <br><br>
     * <b>This does not parse color codes</b> other than those concerning the palette.
     * <br><br>
     * Will delegate to {@link #forConsole(FriendlyFeedbackPalette)}
     * in previous minecraft versions because it is assumed
     * that the console colors have no HEX
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public MutableComponent forPlayer(@NotNull FriendlyFeedbackPalette pal) {
        MutableComponent actualMessage = Component.empty();

        // Add appropriate prefix
        if (hasPrefix()) {

            // Add (accounting for subdivision)
            actualMessage.append(pal.getPrefix(getSubdivision()));
        }

        // Add colored message
        actualMessage.append(pal.parseForPlayer(getMessage()));

        // Return built
        return actualMessage;
    }

    /**
     * Parses a message intended to be read through the console.
     * Consoles don't support many colors, in fact, they support
     * the same colors supported until MC 1.15
     * <p></p>
     * <b>This does not parse color codes</b> other than those concerning the palette.
     * <p></p>
     * Will also use this method if parsing a message in a version
     * of minecraft less than 1.16
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public String forConsole(@NotNull FriendlyFeedbackPalette pal) {
        StringBuilder actualMessage = new StringBuilder();

        // Add appropriate prefix
        if (hasPrefix()) {

            // Add (accounting for subdivision)
            actualMessage.append(pal.parseForConsole(pal.consolePrefix(getSubdivision())));
        }

        // Add colored message
        actualMessage.append(pal.parseForConsole(getMessage()));

        // Return built
        return actualMessage.toString();
    }
}
