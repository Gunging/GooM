package gunging.ootilities.GungingOotilitiesMod.mixininterfaces;

import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

/**
 * A class that may have a player associated with it
 *
 * @author Gunging
 * @since 1.0.0
 */
public interface PlayerLinked {

    /**
     * @return The player that might be associated with this class
     *
     * @author Gunging
     * @since 1.0.0
     */
    @Nullable Player gungingoom$getAssociatedPlayer();
}
