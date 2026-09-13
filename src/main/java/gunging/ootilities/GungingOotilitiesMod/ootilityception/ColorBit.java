package gunging.ootilities.GungingOotilitiesMod.ootilityception;

import gunging.ootilities.GungingOotilitiesMod.commands.friendly.FriendlyFeedbackPalette;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a color code and its content
 *
 * @since 1.0.0
 * @author Gunging
 */
public class ColorBit {

    /**
     * The color code to format this message
     *
     * @since 1.0.0
     */
    @NotNull String format;

    /**
     * @since 1.0.0
     * @author Gunging
     */
    public @NotNull String getFormat() {
        return format;
    }

    /**
     * @since 1.0.0
     * @author Gunging
     */
    public void setFormat(@NotNull String format) {
        this.format = format;
    }

    /**
     * The message to be formatted
     *
     * @since 1.0.0
     */
    @NotNull String content;

    /**
     * @since 1.0.0
     * @author Gunging
     */
    public @NotNull String getContent() {
        return content;
    }

    /**
     * @since 1.0.0
     * @author Gunging
     */
    public void setContent(@NotNull String content) {
        this.content = content;
    }

    /**
     * @param format The color code to format this message
     * @param content The message to be formatted
     *
     * @since 1.0.0
     * @author Gunging
     */
    public ColorBit(@NotNull String format, @NotNull String content) {
        this.content = content;
        this.format = format;
    }

    /**
     * @return Turns this color bit into a mutable component
     *
     * @since 1.0.0
     * @author Gunging
     */
    @NotNull public MutableComponent forgeBake() { return forgeBake(null); }

    /**
     * @return Turns this color bit into a mutable component
     *
     * @since 1.0.0
     * @author Gunging
     */
    @NotNull public MutableComponent forgeBake(@Nullable FriendlyFeedbackPalette palette) {

        // Try as palette
        if (palette != null) {
            MutableComponent ret = tryBakeAsPalette(palette);
            if (ret != null) { return ret; }
        }

         // Try as hex
        MutableComponent ret = tryBakeAsHex();
        if (ret != null) { return ret; }

        // Empty literal
        return Component.literal(content);
    }

    /**
     * @return Turns this color bit into a mutable component
     *
     * @since 1.0.0
     * @author Gunging
     */
    @Nullable public MutableComponent tryBakeAsHex() {

        // Expects hex format
        if (!format.startsWith("#")) { return null; }
        int len = format.length();
        if (len < 7 || len > 9) { return null; }

        // Parse RGB
        int r = OotilityNumbers.HexIntParse(format.substring( 1, 3 ));
        int g = OotilityNumbers.HexIntParse(format.substring( 3, 5 ));
        int b = OotilityNumbers.HexIntParse(format.substring( 5, 7 ));
        if (r < 0 || g < 0 || b < 0) { return null; }

        // Build
        return OotilityNumbers.applyStyle(Component.literal(content), OotilityNumbers.bitShiftRGB(r, g, b));
    }

    /**
     * @return Turns this color bit into a mutable component
     *
     * @since 1.0.0
     * @author Gunging
     */
    @Nullable public MutableComponent tryBakeAsPalette(@NotNull FriendlyFeedbackPalette palette) {

        // Not palette
        if (format.length() != 1) { return null; }

        // Read palette char
        char code = format.charAt(0);

        // Scan each character in looking for a color code
        int codeStyle = -1;
        switch (code) {
            case 'b': codeStyle = palette.getBodyFormat(); break;
            case 'e': codeStyle = palette.getExampleFormat(); break;
            case 'i', 'u': codeStyle = palette.getInputFormat(); break;
            case 's': codeStyle = palette.getSuccessFormat(); break;
            case 'f': codeStyle = palette.getFailureFormat(); break;
            case 'r': codeStyle = palette.getResultFormat(); break;
            default: break;
        }

        // Not interested in locations that contain no format codes
        if (codeStyle < 0) { return null; }

        // Bake as Palette
        return OotilityNumbers.applyStyle(Component.literal(content), codeStyle);
    }
}
