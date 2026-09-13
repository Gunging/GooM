package gunging.ootilities.GungingOotilitiesMod.events.controlling;

import gunging.ootilities.GungingOotilitiesMod.GungingOotilitiesMod;
import gunging.ootilities.GungingOotilitiesMod.events.extension.ServersideEntityEquipmentChangeEvent;
import gunging.ootilities.GungingOotilitiesMod.mixininterfaces.WithStatsStack;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

/**
 * The server-side and common events listener
 *
 * @since 1.0.0
 * @author Gunging
 */
@Mod.EventBusSubscriber(modid = GungingOotilitiesMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ServersideControlListener {

    /**
     * When the equipment changes, naturally we must recalculate the stat totals of this player
     *
     * @param event The event indicating that a player's equipment was modified
     *
     * @since 1.0.0
     * @author Gunging
     */
    @SubscribeEvent
    public static void onPlayerEquipmentChanges(@NotNull ServersideEntityEquipmentChangeEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer)) { return; }
        ServerPlayer player = (ServerPlayer) event.getEntity();
        WithStatsStack asStats = (WithStatsStack) player.getInventory();
        asStats.gungingoom$getStatStack().parentalChainRegisterChanges();
    }
}
