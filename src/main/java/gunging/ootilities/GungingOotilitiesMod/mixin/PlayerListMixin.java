package gunging.ootilities.GungingOotilitiesMod.mixin;

import gunging.ootilities.GungingOotilitiesMod.GungingOotilitiesMod;
import gunging.ootilities.GungingOotilitiesMod.events.ExtensionEventBroadcaster;
import gunging.ootilities.GungingOotilitiesMod.events.extension.ItemFlowExtensionReason;
import gunging.ootilities.GungingOotilitiesMod.exploring.ExplorerManager;
import gunging.ootilities.GungingOotilitiesMod.exploring.ItemExplorerStatement;
import gunging.ootilities.GungingOotilitiesMod.netcode.GOOMNetworkManager;
import gunging.ootilities.GungingOotilitiesMod.netcode.packets.clientbound.GMNClientLoginRequest;
import gunging.ootilities.GungingOotilitiesMod.netcode.packets.clientbound.GMNClientboundStatementSync;
import net.minecraft.network.Connection;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.entity.EquipmentSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;

@Mixin(PlayerList.class)
public abstract class PlayerListMixin {

    /*

    @Inject(method = "placeNewPlayer", at = @At("RETURN"))
    protected void onPlaceNewPlayerReturn(Connection pNetManager, ServerPlayer pPlayer, CallbackInfo ci) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ExtensionEventBroadcaster.BroadcastEquipmentChangeEvent(ItemFlowExtensionReason.CLIENTBOUND_SET_CARRIED_ITEM_PACKET_SEND_PLACE_NEW_PLAYER, false, slot, pPlayer);
        }
    }

    //*/

    @Inject(method = "placeNewPlayer", at = @At("RETURN"))
    public void onPlayerLogin(Connection pNetManager, ServerPlayer pPlayer, CallbackInfo ci) {

        // Iterate all namespaces
        for (String namespace : ExplorerManager.listStatementNamespaces()) {

            // To Sync
            ArrayList<ItemExplorerStatement<?,?>> toSync = ExplorerManager.listStatements(namespace);
            if (toSync.isEmpty()) { continue; }

            // Send information
            GMNClientboundStatementSync packet = new GMNClientboundStatementSync(namespace, toSync);
            GOOMNetworkManager.serverToPlayer(pPlayer, packet);
        }

        GMNClientLoginRequest request = new GMNClientLoginRequest();
        GOOMNetworkManager.serverToPlayer(pPlayer, request);
    }

    @Inject(method = "sendAllPlayerInfo", at = @At("RETURN"))
    protected void onSendAllPlayerInfoReturn(ServerPlayer pPlayer, CallbackInfo ci) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ExtensionEventBroadcaster.BroadcastEquipmentChangeEvent(ItemFlowExtensionReason.CLIENTBOUND_SET_CARRIED_ITEM_PACKET_SEND_SEND_ALL_PLAYER_INFO, false, slot, pPlayer);
        }
    }
}
