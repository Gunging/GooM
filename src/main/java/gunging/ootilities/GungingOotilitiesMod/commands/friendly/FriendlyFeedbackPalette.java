package gunging.ootilities.GungingOotilitiesMod.commands.friendly;

import gunging.ootilities.GungingOotilitiesMod.ootilityception.OotilityNumbers;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * If you're going to do something, do it with style.
 * <br><br>
 * This interface allows to easily and consistently style
 * your feedback.
 * <br><br>
 * Formatting Codes:
 * <p><code><b>$b</b></code> Normal Body Text
 * </p><code><b>$e</b></code> Example Value / Recommendation
 * <p><code><b>$i</b></code> User Input
 * <p><code><b>$u</b></code> User Input
 * </p><code><b>$r</b></code> Operation Result
 * <p><code><b>$s</b></code> Success
 * </p><code><b>$f</b></code> Failure
 *
 * @author Gunging
 * @since 1.0.0
 */
@SuppressWarnings({"unused", "SpellCheckingInspection"})
public abstract class FriendlyFeedbackPalette {

    /**
     * Most of your message will be colored this way, it is the base text colour (with no highlighting of any kind).
     * <br><br>
     * Preferably a neutral color, not too dark nor too light (consider dark and light consoles).
     *
     * @see gunging.ootilities.GungingOotilitiesMod.ootilityception.OotilityNumbers#bitShiftFormat(int, int, int, boolean, boolean, boolean, boolean)
     *
     * @author Gunging
     * @since 1.0.0
     */
    public abstract int getBodyFormat();
    /**
     * Used when messages are sent to the console, or other context that doesn't support components.
     *
     * @see #getBodyFormat()
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public abstract String consoleBodyFormat();

    /**
     * If you are going to provide an example/recommended value to the user, highlight it this way with <b>$e</b>.
     * <br><br>
     * Example:
     * <br>
     * <code>Log("You should try setting consumeOnUse to <b>$etrue</b>")</code>
     *
     * @see gunging.ootilities.GungingOotilitiesMod.ootilityception.OotilityNumbers#bitShiftFormat(int, int, int, boolean, boolean, boolean, boolean)
     *
     * @author Gunging
     * @since 1.0.0
     */
    public abstract int getExampleFormat();
    /**
     * Used when messages are sent to the console, or other context that doesn't support components.
     *
     * @see #getExampleFormat()
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public abstract String consoleExampleFormat();

    /**
     * Tell the user what they are telling you with <b>$u</b>.
     * <br><br>
     * It may sound trivial, but a huge confusion may arise when you
     * tell a user you're expecting a numeric value, and they swear they
     * are sending a number, but they are writing this number in the
     * <i>wrong</i> place.
     * <br><br>
     * Example:
     * <br>
     * <code>Log("Expected a number instead of <b>$u" + userInput</b>)</code>
     *
     * @see gunging.ootilities.GungingOotilitiesMod.ootilityception.OotilityNumbers#bitShiftFormat(int, int, int, boolean, boolean, boolean, boolean)
     *
     * @author Gunging
     * @since 1.0.0
     */
    public abstract int getInputFormat();
    /**
     * Used when messages are sent to the console, or other context that doesn't support components.
     *
     * @see #getInputFormat()
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public abstract String consoleInputFormat();

    /**
     * Tell the user the result of your operation with <b>$r</b>.
     * <br><br>
     * Usually after processing their input or something.
     * <br><br>
     * Example:
     * <br>
     * <code>Log("Searched ores near you and found <b>$r" + oresFound</b>)</code>
     *
     * @see gunging.ootilities.GungingOotilitiesMod.ootilityception.OotilityNumbers#bitShiftFormat(int, int, int, boolean, boolean, boolean, boolean)
     *
     * @author Gunging
     * @since 1.0.0
     */
    public abstract int getResultFormat();
    /**
     * Used when messages are sent to the console, or other context that doesn't support components.
     *
     * @see #getResultFormat()
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public abstract String consoleResultFormat();

    /**
     * Sometimes one must be clearer when specifying if an operation
     * completed successfully. In such cases, highlight a word
     * indicating success with <b>$s</b>.
     * <br><br>
     * Example:
     * <br>
     * <code>Log("That animal <b>$smatched</b> $bthe name specified.")</code>
     *
     * @see gunging.ootilities.GungingOotilitiesMod.ootilityception.OotilityNumbers#bitShiftFormat(int, int, int, boolean, boolean, boolean, boolean)
     *
     * @author Gunging
     * @since 1.0.0
     */
    public abstract int getSuccessFormat();
    /**
     * Used when messages are sent to the console, or other context that doesn't support components.
     *
     * @see #getSuccessFormat()
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public abstract String consoleSuccessFormat();

    /**
     * Sometimes one must be clearer when specifying if an operation
     * failed or was cancelled. In such cases, highlight a word
     * indicating failure with <b>$f</b>.
     * <br><br>
     * Example:
     * <br>
     * <code>Log("That animal <b>$fdid not match</b> $bthe name specified.")</code>
     *
     * @see gunging.ootilities.GungingOotilitiesMod.ootilityception.OotilityNumbers#bitShiftFormat(int, int, int, boolean, boolean, boolean, boolean)
     *
     * @author Gunging
     * @since 1.0.0
     */
    public abstract int getFailureFormat();

    /**
     * Used when messages are sent to the console, or other context that doesn't support components.
     *
     * @see #getFailureFormat()
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public abstract String consoleFailureFormat();

    /**
     * Probably the prefix of your plugin, or some brand-like trademark in its raw form.
     * <br><br>
     * The symbol key <b>#s</b> stands for some 'subdivision' which will
     * be inserted if not null.
     * <br><br>
     * Example: (<code>&3[&eGooM#s&3] </code>)<p>
     * <i>Note the trailing space in this prefix example, it is intentional. </i>
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public abstract MutableComponent getRawPrefix();
    /**
     * Ready-to-use prefix for a message, with optional subdivision text included
     *
     * @param subdivision Optional subdivision keyword
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull MutableComponent getPrefix(@Nullable String subdivision) {
        MutableComponent ret = Component.empty();

        // Must transcribe the raw prefix
        for (Component sibling : getRawPrefix().getSiblings()) {

            // Skip or insert subdivision
            if (sibling.getString().equals("#s")) {

                // Append subdivision in subdivision style
                if (subdivision != null) {
                    ret.append(OotilityNumbers.applyStyle(Component.literal(" " + subdivision), getSubdivisionFormat())); }

            // Not subdivision? Append normally
            } else { ret.append(sibling); }
        }

        // Done
        return ret;
    }
    /**
     * Used when messages are sent to the console, or other context that doesn't support components.
     *
     * @see #getRawPrefix()
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public abstract String getRawPrefixConsole();
    /**
     * Used when messages are sent to the console, or other context that doesn't support components.
     *
     * @see #getPrefix(String)
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull String consolePrefix(@Nullable String subdivision) {

        if (subdivision != null) {

            // Append subdivision
            return getRawPrefixConsole().replace("#s", " " + consoleSubdivisionFormat() + subdivision);

        } else {

            // Remove #s
            return getRawPrefixConsole().replace("#s", "");
        }
    }
    /**
     * In your prefix, you may specify a word or something for the sub-product
     * or whatever (which is fancy), but is not always displayed. If it is,
     * it will be preceded by this color code.
     *
     * @see gunging.ootilities.GungingOotilitiesMod.ootilityception.OotilityNumbers#bitShiftFormat(int, int, int, boolean, boolean, boolean, boolean)
     *
     * @author Gunging
     * @since 1.0.0
     */
    public abstract int getSubdivisionFormat();
    /**
     * Used when messages are sent to the console, or other context that doesn't support components.
     *
     * @see #getSubdivisionFormat()
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public abstract String consoleSubdivisionFormat();

    /**
     * @param message Message which color codes to bake through components.
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public MutableComponent parseForPlayer(@NotNull String message) {
        MutableComponent ret = Component.empty();

        int lastL = 0;
        int lastStyle = getBodyFormat();
        for (int l = 0; l < (message.length() - 1); l++) {

            // Scan each character in looking for a color code
            int codeStyle = -1;
            if (message.charAt(l) == '$') {
                char code = message.charAt(l + 1);
                switch (code) {
                    case 'b': codeStyle = getBodyFormat(); break;
                    case 'e': codeStyle = getExampleFormat(); break;
                    case 'i', 'u': codeStyle = getInputFormat(); break;
                    case 's': codeStyle = getSuccessFormat(); break;
                    case 'f': codeStyle = getFailureFormat(); break;
                    case 'r': codeStyle = getResultFormat(); break;
                    default: break;
                }
            }

            // Not interested in locations that contain no format codes
            if (codeStyle < 0) { continue; }

            // Include in the result the previous text
            String excerpt = message.substring(lastL, l);
            if (!excerpt.isEmpty()) { ret.append(OotilityNumbers.applyStyle(Component.literal(excerpt), lastStyle)); }

            // Update metrics
            lastL = l + 2;
            lastStyle = codeStyle;
        }

        // Append the last segment
        if (lastL < message.length()) {
            String excerpt = message.substring(lastL);
            if (!excerpt.isEmpty()) { ret.append(OotilityNumbers.applyStyle(Component.literal(excerpt), lastStyle)); }
        }

        // Ay
        return ret;
    }
    /**
     * Used when messages are sent to the console, or other context that doesn't support components.
     *
     * @see #parseForPlayer(String)
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public String parseForConsole(@NotNull String message) {

        // Ay
        return message
                .replace("$b", consoleBodyFormat())
                .replace("$e", consoleExampleFormat())
                .replace("$i", consoleInputFormat())
                .replace("$u", consoleInputFormat())
                .replace("$s", consoleSuccessFormat())
                .replace("$f", consoleFailureFormat())
                .replace("$r", consoleResultFormat());
    }
}
