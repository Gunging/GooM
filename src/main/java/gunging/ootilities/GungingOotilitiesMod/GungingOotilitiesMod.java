package gunging.ootilities.GungingOotilitiesMod;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
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

    public GungingOotilitiesMod(FMLJavaModLoadingContext context) {

        // Register this mod onto Forge
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static void Log(@Nullable String log) {
        System.out.println("GREP [Not Secure] <Dev> " + log);
    }
}
