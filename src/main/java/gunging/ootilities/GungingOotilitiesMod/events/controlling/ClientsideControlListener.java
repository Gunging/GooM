package gunging.ootilities.GungingOotilitiesMod.events.controlling;

import gunging.ootilities.GungingOotilitiesMod.GungingOotilitiesMod;
import gunging.ootilities.GungingOotilitiesMod.events.extension.ClientsideEntityEquipmentChangeEvent;
import gunging.ootilities.GungingOotilitiesMod.events.extension.ServersideEntityEquipmentChangeEvent;
import gunging.ootilities.GungingOotilitiesMod.mixininterfaces.WithStatsStack;
import gunging.ootilities.GungingOotilitiesMod.mixininterfaces.WithTransitiveStack;
import gunging.ootilities.GungingOotilitiesMod.netcode.GOOMNetworkManager;
import gunging.ootilities.GungingOotilitiesMod.netcode.packets.serverbound.GMNServerboundStatementSyncRequest;
import gunging.ootilities.GungingOotilitiesMod.ootilityception.OotilityNumbers;
import gunging.ootilities.GungingOotilitiesMod.stats.core.StatInstance;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
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
    @SubscribeEvent
    public static void onItemTooltips(@NotNull ItemTooltipEvent event) {

        // Identify Stats Stack
        ItemStack asItem = event.getItemStack();
        WithStatsStack asStats = (WithStatsStack) (Object) asItem;
        if (asStats.gungingoom$getStatStack().getStatTotals().isEmpty()) { return; }

        // Include GooM Stats in this list
        for (StatInstance<?> stat : asStats.gungingoom$getStatStack().getStatTotals().values()) {
            for (String lore : stat.whenDisplayed()) {
                MutableComponent mutablecomponent = OotilityNumbers.colorize(lore);
                event.getToolTip().add(mutablecomponent);
            }
        }
    }

    /**
     * When the equipment changes, naturally we must recalculate the stat totals of this player
     *
     * @param event The event indicating that a player's equipment was modified
     *
     * @since 1.0.0
     * @author Gunging
     */
    @SubscribeEvent
    public static void onPlayerEquipmentChanges(@NotNull ClientsideEntityEquipmentChangeEvent event) {
        if (!(event.getEntity() instanceof Player)) { return; }
        Player player = (Player) event.getEntity();
        WithTransitiveStack asStats = (WithTransitiveStack) player.getInventory();
        asStats.gungingoom$getContainedStatStacks().parentalChainRegisterChanges();
    }
}
