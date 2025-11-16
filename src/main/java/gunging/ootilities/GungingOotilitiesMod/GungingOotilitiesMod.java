package gunging.ootilities.GungingOotilitiesMod;

import gunging.ootilities.GungingOotilitiesMod.exploring.ExplorerManager;
import gunging.ootilities.GungingOotilitiesMod.netcode.GOOMNetworkManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
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

        // Register this mod onto Forge
        MinecraftForge.EVENT_BUS.register(this);
        context.getModEventBus().addListener(this::OnCommonSetup);
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    private void OnCommonSetup(final FMLCommonSetupEvent event) {
        ExplorerManager.registerGooMStatements(true);

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
