package gunging.ootilities.GungingOotilitiesMod.stats;

import gunging.ootilities.GungingOotilitiesMod.stats.core.StatDefinition;
import gunging.ootilities.GungingOotilitiesMod.stats.registry.GOOMStats;
import gunging.ootilities.GungingOotilitiesMod.stats.registry.RegisterAllStatsEvent;
import net.minecraftforge.common.MinecraftForge;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The manager class where Stats are registered
 *
 * @since 1.0.0
 * @author Gunging
 */
public class StatsManager {

    /**
     * The stats that are loaded and active
     *
     * @since 1.0.0
     */
    @NotNull final HashMap<String, StatDefinition<?>> registeredStats = new HashMap<>();

    /**
     * @param statID The Stat ID you are looking for
     *
     * @return The stat loaded with this ID
     *
     * @since 1.0.0
     * @author Gunging
     */
    @Nullable public StatDefinition<?> getStatDefinition(@Nullable String statID) { return registeredStats.get(statID); }

    /**
     * @return The internal IDs of all loaded stats
     *
     * @since 1.0.0
     * @author Gunging
     */
    @NotNull public ArrayList<String> getStatIDs() { return new ArrayList<>(registeredStats.keySet()); }

    /**
     * This means registering stats is closed now
     *
     * @since 1.0.0
     */
    boolean registeringClosed;

    /**
     * All stats must be registered upon startup (in theory)
     *
     * @param statDef The stat to register
     *
     * @since 1.0.0
     * @author Gunging
     */
    public void registerStat(@NotNull StatDefinition<?> statDef) {

        // We did it boys, registering is no more
        if (registeringClosed) { return; }

        // Register this stat
        registeredStats.put(statDef.getDefinitionID(), statDef);
    }

    /**
     * Registers all stats, sending out an event for
     * third parties to register their stats as well.
     *
     * @since 1.0.0
     * @author Gunging
     */
    public void registerAllStats() {
        if (registeringClosed) { return; }

        // Register default stats... if there were any
        registerStat(GOOMStats.AURA);
        registerStat(GOOMStats.BLESSING_OF_MANGOES);

        // Register external stats through event
        RegisterAllStatsEvent playerEvent = new RegisterAllStatsEvent(this);
        MinecraftForge.EVENT_BUS.post(playerEvent);

        registeringClosed = true;
    }
}
