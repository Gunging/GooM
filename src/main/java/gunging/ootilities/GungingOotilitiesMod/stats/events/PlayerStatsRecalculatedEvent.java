package gunging.ootilities.GungingOotilitiesMod.stats.events;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

/**
 * A fundamental event that fires every time a
 * player's stats are recalculated, allowing
 * listeners to respond to the new values.
 *
 * @author Gunging
 * @since 1.0.0
 */
public class PlayerStatsRecalculatedEvent extends StatsRecalculatedEvent {

    /**
     * @param player Player whose stats were recalculated
     *
     * @author Gunging
     * @since 1.0.0
     */
    public PlayerStatsRecalculatedEvent(@NotNull Player player) {
        super(player);
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override public Player getEntity() {
        return (Player) super.getEntity();
    }

    /**
     * @param player An entity known to be a player
     *
     * @return The appropriate Player Stats Recalculated event
     */
    @NotNull public static PlayerStatsRecalculatedEvent forPlayer(@NotNull Player player) {
        if (player instanceof ServerPlayer) {
            return new ServersidePlayerStatsRecalculatedEvent((ServerPlayer) player);
        } else {
            return ClientsidePlayerStatsRecalculatedEvent.forPlayer(player);
        }
    }
}
