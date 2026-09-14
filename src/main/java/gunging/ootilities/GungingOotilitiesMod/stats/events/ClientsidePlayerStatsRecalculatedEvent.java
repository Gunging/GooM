package gunging.ootilities.GungingOotilitiesMod.stats.events;

import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Fires when a player's stats are recalculated in the client side
 *
 * @author Gunging
 * @since 1.0.0
 */
public class ClientsidePlayerStatsRecalculatedEvent extends PlayerStatsRecalculatedEvent {

    /**
     * @param player Player whose stats were recalculated
     *
     * @author Gunging
     * @since 1.0.0
     */
    public ClientsidePlayerStatsRecalculatedEvent(@NotNull AbstractClientPlayer player) {
        super(player);
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override public AbstractClientPlayer getEntity() {
        return (AbstractClientPlayer) super.getEntity();
    }

    /**
     * @return If the player whose stats were recalculated is the local player
     *
     * @author Gunging
     * @since 1.0.0
     */
    public boolean isLocal() { return getEntity() instanceof LocalPlayer; }

    /**
     * @param player An entity known to be a player
     *
     * @return The appropriate Player Stats Recalculated event
     */
    @NotNull public static PlayerStatsRecalculatedEvent forPlayer(@NotNull Player player) {

        // Do Local
        if (player instanceof LocalPlayer) {
            return new LocalPlayerStatsRecalculatedEvent((LocalPlayer) player);

        // Do remote
        } else if (player instanceof AbstractClientPlayer) {
            return new ClientsidePlayerStatsRecalculatedEvent((AbstractClientPlayer) player);

        // Unknown
        } else {
            return new PlayerStatsRecalculatedEvent(player);
        }
    }
}
