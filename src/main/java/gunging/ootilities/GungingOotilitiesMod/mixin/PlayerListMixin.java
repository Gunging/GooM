package gunging.ootilities.GungingOotilitiesMod.mixin;

import gunging.ootilities.GungingOotilitiesMod.events.ExtensionEventBroadcaster;
import gunging.ootilities.GungingOotilitiesMod.events.extension.ItemFlowExtensionReason;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.entity.EquipmentSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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

    @Inject(method = "sendAllPlayerInfo", at = @At("RETURN"))
    protected void onSendAllPlayerInfoReturn(ServerPlayer pPlayer, CallbackInfo ci) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ExtensionEventBroadcaster.BroadcastEquipmentChangeEvent(ItemFlowExtensionReason.CLIENTBOUND_SET_CARRIED_ITEM_PACKET_SEND_SEND_ALL_PLAYER_INFO, false, slot, pPlayer);
        }
    }
}
