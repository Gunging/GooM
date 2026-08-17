package gunging.ootilities.GungingOotilitiesMod.stats.registry;

import gunging.ootilities.GungingOotilitiesMod.stats.StatsManager;
import net.minecraftforge.eventbus.api.Event;
import org.jetbrains.annotations.NotNull;

/**
 * Event fired when stats will be registered
 *
 * @since 1.0.0
 * @author Gunging
 */
public class RegisterAllStatsEvent extends Event {

    /**
     * @since 1.0.0
     * @author Gunging
     */
    public @NotNull StatsManager getManager() {
        return manager;
    }

    /**
     * The manager doing the registering sweep
     *
     * @since 1.0.0
     */
    @NotNull final StatsManager manager;

    /**
     * @param manager The manager doing the registering sweep
     *
     * @since 1.0.0
     * @author Gunging
     */
    public RegisterAllStatsEvent(@NotNull StatsManager manager) {this.manager = manager;}
}
