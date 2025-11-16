package gunging.ootilities.GungingOotilitiesMod.events.controlling;

import gunging.ootilities.GungingOotilitiesMod.GungingOotilitiesMod;
import gunging.ootilities.GungingOotilitiesMod.netcode.GOOMNetworkManager;
import gunging.ootilities.GungingOotilitiesMod.netcode.packets.serverbound.GMNServerboundStatementSyncRequest;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
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
    public static void  onServerJoin(@NotNull PlayerEvent.PlayerLoggedInEvent event) {
        LocalPlayer me = Minecraft.getInstance().player;
        if (me == null) { return; }
        if (me.getUUID().equals(event.getEntity().getUUID())) {

            // Request Statements
            GMNServerboundStatementSyncRequest request = new GMNServerboundStatementSyncRequest();
            GOOMNetworkManager.playerToServer(request);
        }
    }
}
