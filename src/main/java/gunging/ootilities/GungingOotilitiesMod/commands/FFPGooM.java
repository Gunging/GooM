package gunging.ootilities.GungingOotilitiesMod.commands;

import gunging.ootilities.GungingOotilitiesMod.commands.friendly.FriendlyFeedbackPalette;
import gunging.ootilities.GungingOotilitiesMod.ootilityception.OotilityNumbers;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;

/**
 * The powerful GooM Palette that makes my commands look
 * <br>:sparkles: <b>P R E T T Y</b> :sparkles:
 *
 * @author Gunging
 * @since 1.0.0
 */
public class FFPGooM extends FriendlyFeedbackPalette {

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override public int getBodyFormat() { return OotilityNumbers.bitShiftRGB(204, 204, 204); }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override public @NotNull String consoleBodyFormat() { return "§7"; }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override public int getExampleFormat() { return OotilityNumbers.bitShiftRGB(158, 255, 252); }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @NotNull String consoleExampleFormat() { return "§b"; }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override public int getInputFormat() { return OotilityNumbers.bitShiftRGB(255, 253, 158); }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override public @NotNull String consoleInputFormat() { return "§f"; }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override public int getResultFormat() { return OotilityNumbers.bitShiftRGB(255, 226, 158); }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override public @NotNull String consoleResultFormat() { return "§e"; }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override public int getSuccessFormat() { return OotilityNumbers.bitShiftRGB(158, 255, 163); }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override public @NotNull String consoleSuccessFormat() { return "§a"; }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override public int getFailureFormat() { return OotilityNumbers.bitShiftRGB(255, 158, 158); }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override public @NotNull String consoleFailureFormat() { return "§c"; }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override public @NotNull MutableComponent getRawPrefix() {
        return Component.empty()
                .append(OotilityNumbers.applyStyle(Component.literal("【 "), OotilityNumbers.bitShiftRGB(89, 152, 194)))
                .append(OotilityNumbers.applyStyle(Component.literal("GooM"), OotilityNumbers.bitShiftRGB(255, 228, 158)))
                .append(Component.literal("#s"))
                .append(OotilityNumbers.applyStyle(Component.literal(" 】 "), OotilityNumbers.bitShiftRGB(89, 152, 194)));
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override public @NotNull String getRawPrefixConsole() { return "§3[§6GooM#s§3] "; }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override public int getSubdivisionFormat() { return OotilityNumbers.bitShiftFormat(255, 239, 199, true, false, false ,false); }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override public @NotNull String consoleSubdivisionFormat() { return "§e§o"; }
}
