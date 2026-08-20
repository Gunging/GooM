package gunging.ootilities.GungingOotilitiesMod;

import gunging.ootilities.GungingOotilitiesMod.commands.forge.GCCCommandRegistry;
import gunging.ootilities.GungingOotilitiesMod.exploring.ExplorerManager;
import gunging.ootilities.GungingOotilitiesMod.mod.GooMGamerules;
import gunging.ootilities.GungingOotilitiesMod.netcode.GOOMNetworkManager;
import gunging.ootilities.GungingOotilitiesMod.stats.StatsManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * An utilities library focused on abstract inventory management
 *
 * @author Gunging
 * @since 1.0.0
 */
@Mod(GungingOotilitiesMod.MODID)
public class GungingOotilitiesMod {

    /**
     * The ModID used everywhere that this modID is required
     *
     * @since 1.0.0
     */
    public static final String MODID = "gungingoom";

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public GungingOotilitiesMod(FMLJavaModLoadingContext context) {
        INSTANCE = this;

        // Register this mod onto Forge
        MinecraftForge.EVENT_BUS.register(this);
        context.getModEventBus().addListener(this::OnCommonSetup);

        // Startup the mod's systems
        getCommands().OnModLoadInitialize(context);
    }

    /**
     * The Instance of the Mod
     *
     * @since 1.0.0
     */
    static GungingOotilitiesMod INSTANCE;

    /**
     * @return The Instance of the Mod
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull
    public static GungingOotilitiesMod getInstance() { return INSTANCE; }

    //region Systems
    /**
     * The Manager for the system that allows you
     * to explore player inventories with finesse
     *
     * @since 1.0.0
     */
    ExplorerManager INVENTORY_EXPLORER = null;
    /**
     * @return The Explorer System manager
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public ExplorerManager getExplorer() {
        if (INVENTORY_EXPLORER!= null) { return INVENTORY_EXPLORER; }
        INVENTORY_EXPLORER = new ExplorerManager();
        return INVENTORY_EXPLORER;
    }

    /**
     * The Manager for the system that allows to
     * keep track and tally of stats
     *
     * @since 1.0.0
     */
    StatsManager STATS_SYS = null;
    /**
     * @return The Stats System manager
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public StatsManager getStats() {
        if (STATS_SYS!= null) { return STATS_SYS; }
        STATS_SYS = new StatsManager();
        return STATS_SYS;
    }

    /**
     * The Command Registry that handles GooM commands
     *
     * @since 1.0.0
     */
    GCCCommandRegistry COMMANDS = null;
    /**
     * @return The Stats System manager
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public GCCCommandRegistry getCommands() {
        if (COMMANDS!= null) { return COMMANDS; }
        COMMANDS = new GCCCommandRegistry();
        return COMMANDS;
    }

    /**
     * The operational configuration of this mod
     *
     * @since 1.0.0
     */
    GooMGamerules GAMERULES = null;
    /**
     * @return The Gamerules
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public GooMGamerules getGamerules() {
        if (GAMERULES!= null) { return GAMERULES; }
        GAMERULES = new GooMGamerules();
        return GAMERULES;
    }
    //endregion

    /**
     * @author Gunging
     * @since 1.0.0
     */
    private void OnCommonSetup(final FMLCommonSetupEvent event) {
        getExplorer().registerGooMStatements(true);
        getStats().registerAllStats();

        /*
         * The event does not run on the main thread, we must
         * enqueue this for it to run in the main thread.
         */

        event.enqueueWork(GOOMNetworkManager::register);
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public static void Log(@Nullable String log) {
        System.out.println("GREP [Not Secure] <Dev> " + log);
    }
}
