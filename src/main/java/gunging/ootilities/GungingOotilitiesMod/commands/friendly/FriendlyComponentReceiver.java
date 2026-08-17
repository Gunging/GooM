package gunging.ootilities.GungingOotilitiesMod.commands.friendly;

import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;

/**
 * Represents something with the capability to receive component messages
 *
 * @author Gunging
 * @since 1.0.0
 */
@FunctionalInterface
public interface FriendlyComponentReceiver {

    /**
     * @param bakedMessage The finished message after parsing its color codes
     *
     * @author Gunging
     * @since 1.0.0
     */
    void receive(@NotNull MutableComponent bakedMessage);
}
