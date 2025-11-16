package gunging.ootilities.GungingOotilitiesMod.instants;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

/**
 * An event fired when the player joins a server in the clientside
 *
 * @author Gunging
 * @since 1.0.0
 */
public class GOOMClientsidePlayerLoginEvent extends PlayerEvent {

    /**
     * @param player The player that logged in
     *
     * @author Gunging
     * @since 1.0.0
     */
    public GOOMClientsidePlayerLoginEvent(@NotNull Player player) {
        super(player);
    }
}
