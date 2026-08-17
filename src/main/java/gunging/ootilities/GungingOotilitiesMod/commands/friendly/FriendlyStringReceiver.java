package gunging.ootilities.GungingOotilitiesMod.commands.friendly;

import org.jetbrains.annotations.NotNull;

/**
 * Represents something with the capability to receive string messages
 *
 * @author Gunging
 * @since 1.0.0
 */
@FunctionalInterface
public interface FriendlyStringReceiver {

    /**
     * @param bakedMessage The finished message after parsing its color codes
     *
     * @author Gunging
     * @since 1.0.0
     */
    void receive(@NotNull String bakedMessage);
}
