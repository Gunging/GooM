package gunging.ootilities.GungingOotilitiesMod.stats.events;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Fires when a player's stats are recalculated in the server side
 *
 * @author Gunging
 * @since 1.0.0
 */
public class ServersidePlayerStatsRecalculatedEvent extends PlayerStatsRecalculatedEvent {

    /**
     * @param player Player whose stats were recalculated
     *
     * @author Gunging
     * @since 1.0.0
     */
    public ServersidePlayerStatsRecalculatedEvent(@NotNull ServerPlayer player) {
        super(player);
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override public ServerPlayer getEntity() {
        return (ServerPlayer) super.getEntity();
    }
}
