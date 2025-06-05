package gunging.ootilities.GungingOotilitiesMod.mixin;

import gunging.ootilities.GungingOotilitiesMod.events.ExtensionEventBroadcaster;
import gunging.ootilities.GungingOotilitiesMod.events.extension.ItemFlowExtensionReason;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public abstract class ClientPacketListenerMixin {

    @Final
    @Shadow
    private Minecraft minecraft;

    @Shadow private ClientLevel level;

    @Unique
    public int gungingoom$originalSlot;

    @Unique
    public ItemStack gungingoom$originalItem;

    @Unique
    void gungingoom$prepareOriginals(@Nullable Player who) {
        if (who == null) { return; }
        gungingoom$originalSlot = who.getInventory().selected;
        gungingoom$originalItem = who.getInventory().getSelected();
    }

    @Unique
    boolean gungingoom$isRedundant(@Nullable Player who, boolean deep) {
        if (who == null) { return true; }

        return
                // Redundant if the selection is unchanged
                gungingoom$originalSlot == who.getInventory().selected &&

                // If searching in deep mode, also check identical items
                (!deep || gungingoom$originalItem.equals(who.getInventory().getSelected(), false));
    }

    @Inject(method = "handleSetCarriedItem", at = @At("HEAD"))
    protected void onCarriedItemCall(ClientboundSetCarriedItemPacket pPacket, CallbackInfo ci) {
        gungingoom$prepareOriginals(this.minecraft.player);
    }

    @Inject(method = "handleSetCarriedItem", at = @At("RETURN"))
    protected void onCarriedItemReturn(ClientboundSetCarriedItemPacket pPacket, CallbackInfo ci) {
        if (gungingoom$isRedundant(this.minecraft.player, false)) { return; }
        ExtensionEventBroadcaster.BroadcastEquipmentChangeEvent(ItemFlowExtensionReason.CLIENTBOUND_SET_CARRIED_ITEM_PACKET_HANDLE, true, EquipmentSlot.MAINHAND, this.minecraft.player);
    }

    @Inject(method = "handleSetEquipment", at = @At("RETURN"))
    protected void onHandleSetEquipmentReturn(ClientboundSetEquipmentPacket pPacket, CallbackInfo ci) {
        Entity ent = this.level.getEntity(pPacket.getEntity());
        if (ent == null) { return; }
        for (Pair<EquipmentSlot, ItemStack> pair : pPacket.getSlots()) {
            ExtensionEventBroadcaster.BroadcastEquipmentChangeEvent(ItemFlowExtensionReason.CLIENTBOUND_SET_EQUIPMENT_HANDLE, true, pair.getFirst(), ent);
        }

    }

    /*
    @Inject(method = "handleTakeItemEntity", at = @At("RETURN"))
    protected void onHandleTakeItemReturn(ClientboundTakeItemEntityPacket pPacket, CallbackInfo ci) {
        // ItemFlowExtensionReason.CLIENTBOUND_TAKE_ITEM_ENTITY_HANDLE

        GungingOotilitiesMod.Log("GOOP CPL handleTakeItemEntity <Coming Soon>");
    }

    @Inject(method = "handleContainerSetSlot", at = @At("HEAD"))
    protected void onHandleContainerSetSlotCall(ClientboundContainerSetSlotPacket pPacket, CallbackInfo ci) {
        if (this.minecraft.player == null) { return; }

        // Identify main hand and cursor transactions
        boolean isMainhand = ((pPacket.getSlot() - 36) == this.minecraft.player.getInventory().selected);
        boolean isCursor = ((pPacket.getContainerId() == -1) && !(this.minecraft.screen instanceof CreativeModeInventoryScreen));

        GungingOotilitiesMod.Log("GOOP CPL SetSlot Index [" + pPacket.getSlot() + "] |Container ID|: " + pPacket.getContainerId());
    }

    @Inject(method = "handleContainerSetSlot", at = @At("RETURN"))
    protected void onHandleContainerSetSlotReturn(ClientboundContainerSetSlotPacket pPacket, CallbackInfo ci) {
        if (this.minecraft.player == null) { return; }
        // ItemFlowExtensionReason.CLIENTBOUND_CONTAINER_SET_SLOT_HANDLE

        GungingOotilitiesMod.Log("GOOP CPL handleContainerSetSlot <Coming Soon>");
    }  //*/
}
