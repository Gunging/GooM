package gunging.ootilities.GungingOotilitiesMod.stats.events;

import net.minecraft.client.player.LocalPlayer;
import org.jetbrains.annotations.NotNull;

/**
 * Fires when the local player's stats are recalculated in the client side
 *
 * @author Gunging
 * @since 1.0.0
 */
public class LocalPlayerStatsRecalculatedEvent extends ClientsidePlayerStatsRecalculatedEvent {

    /**
     * @param player Player whose stats were recalculated
     *
     * @author Gunging
     * @since 1.0.0
     */
    public LocalPlayerStatsRecalculatedEvent(@NotNull LocalPlayer player) {
        super(player);
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override public LocalPlayer getEntity() {
        return (LocalPlayer) super.getEntity();
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override  public boolean isLocal() { return true; }
}
