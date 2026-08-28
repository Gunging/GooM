package gunging.ootilities.GungingOotilitiesMod.mixin;

import gunging.ootilities.GungingOotilitiesMod.events.ExtensionEventBroadcaster;
import gunging.ootilities.GungingOotilitiesMod.events.extension.ItemFlowExtensionReason;
import gunging.ootilities.GungingOotilitiesMod.mixininterfaces.WithStatsStack;
import gunging.ootilities.GungingOotilitiesMod.netcode.GOOMNetworkManager;
import gunging.ootilities.GungingOotilitiesMod.netcode.packets.clientbound.GMNClientboundInherentStatsEntity;
import gunging.ootilities.GungingOotilitiesMod.stats.core.StatInstance;
import gunging.ootilities.GungingOotilitiesMod.stats.core.StatStack;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundUpdateAttributesPacket;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.function.Consumer;

@Mixin(ServerEntity.class)
public abstract class ServerEntityMixin {

    //*
    @Shadow
    @Final
    private Entity entity;

    @Inject(method = "sendPairingData", at = @At("RETURN"))
    protected void onSendPairingDataReturn(ServerPlayer pPlayer, Consumer<Packet<ClientGamePacketListener>> pConsumer, CallbackInfo ci) {
        if (!(entity instanceof LivingEntity)) { return; }

        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ExtensionEventBroadcaster.BroadcastEquipmentChangeEvent(ItemFlowExtensionReason.CLIENTBOUND_SET_EQUIPMENT_SEND_SEND_PAIRING_DATA, false, slot, entity);
        }
    }
    //*/

    @Inject(method = "sendDirtyEntityData", at = @At("RETURN"))
    public void onSendingDirtyData(CallbackInfo ci) {
        if (entity instanceof LivingEntity) {

            // Send dirty data if any
            WithStatsStack asStats = (WithStatsStack) entity;
            StatStack asStack = asStats.gungingoom$getStatStack();
            if (!asStack.getDirtyInherent().isEmpty()) {
                GOOMNetworkManager.broadcastEntityUpdate(entity, new GMNClientboundInherentStatsEntity((LivingEntity) this.entity)); }
            asStack.getDirtyInherent().clear();
        }
    }
}
