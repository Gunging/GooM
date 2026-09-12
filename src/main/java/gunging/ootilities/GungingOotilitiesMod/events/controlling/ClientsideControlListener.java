package gunging.ootilities.GungingOotilitiesMod.events.controlling;

import gunging.ootilities.GungingOotilitiesMod.GungingOotilitiesMod;
import gunging.ootilities.GungingOotilitiesMod.mixininterfaces.WithStatsStack;
import gunging.ootilities.GungingOotilitiesMod.netcode.GOOMNetworkManager;
import gunging.ootilities.GungingOotilitiesMod.netcode.packets.serverbound.GMNServerboundStatementSyncRequest;
import gunging.ootilities.GungingOotilitiesMod.ootilityception.OotilityNumbers;
import gunging.ootilities.GungingOotilitiesMod.stats.core.StatInstance;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

/**
 * The client-side-specific events listener
 *
 * @since 1.0.0
 * @author Gunging
 */
@Mod.EventBusSubscriber(modid = GungingOotilitiesMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientsideControlListener {

    /**
     * @param event The event fired when loading a world
     *
     * @since 1.0.0
     * @author Gunging
     */
    //@SubscribeEvent Handled in Player List Mixin
    public static void onServerJoin(@NotNull PlayerEvent.PlayerLoggedInEvent event) {
        LocalPlayer me = Minecraft.getInstance().player;
        if (me == null) { return; }
        if (me.getUUID().equals(event.getEntity().getUUID())) {

            // Request Statements
            GMNServerboundStatementSyncRequest request = new GMNServerboundStatementSyncRequest();
            GOOMNetworkManager.playerToServer(request);
        }
    }

    /**
     * @param event Event fired when generating an item tooltip
     *
     * @since 1.0.0
     * @author Gunging
     */
    //@SubscribeEvent
    public static void onItemTooltips(@NotNull ItemTooltipEvent event) {

        // Identify Stats Stack
        ItemStack asItem = event.getItemStack();
        WithStatsStack asStats = (WithStatsStack) (Object) asItem;
        if (asStats.gungingoom$getStatStack().getStatTotals().isEmpty()) { return; }

        // If it has any stats
        // Include GooM Stats in this list
        for (StatInstance<?> stat : asStats.gungingoom$getStatStack().getStatTotals().values()) {
            MutableComponent mutablecomponent = OotilityNumbers.applyStyle(Component.empty().append(" • "), OotilityNumbers.bitShiftRGB(230, 230, 100)).append(OotilityNumbers.applyStyle(Component.empty().append(stat.serializeFull()), OotilityNumbers.bitShiftRGB(230, 230, 230)));
            event.getToolTip().add(mutablecomponent);
        }
    }
}
