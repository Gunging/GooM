package gunging.ootilities.GungingOotilitiesMod.mixin;

import gunging.ootilities.GungingOotilitiesMod.events.ExtensionEventBroadcaster;
import gunging.ootilities.GungingOotilitiesMod.events.extension.ItemFlowExtensionReason;
import net.minecraft.network.protocol.game.*;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;

@Mixin(value = ServerGamePacketListenerImpl.class)
public abstract class ServerGamePacketListenerMixin {

    @Shadow public abstract ServerPlayer getPlayer();

    @Shadow public ServerPlayer player;
    @Unique
    public int gungingoom$originalSlot;

    @Unique
    public ItemStack gungingoom$originalMainhand;

    @Unique
    @NotNull
    public ArrayList<ItemStack> gungingoom$originalOffhand = new ArrayList<>();

    @Unique
    void gungingoom$prepareOriginals(@org.jetbrains.annotations.Nullable Player who) {

        // If a player actually exists
        if (who == null) { return; }

        // Remember their original slot
        gungingoom$originalSlot = who.getInventory().selected;
        gungingoom$originalMainhand = who.getInventory().getSelected();

        gungingoom$originalOffhand.clear();
        gungingoom$originalOffhand.addAll(who.getInventory().offhand);
    }

    @Unique
    boolean gungingoom$isMainhandRedundant(@org.jetbrains.annotations.Nullable Player who, boolean deep) {
        if (who == null) { return true; }

        return
                // Redundant if the selection is unchanged
                gungingoom$originalSlot == who.getInventory().selected &&

                        // If searching in deep mode, also check identical items
                        (!deep || gungingoom$originalMainhand.equals(who.getInventory().getSelected(), false));
    }

    @Unique
    boolean gungingoom$isOffhandRedundant(@org.jetbrains.annotations.Nullable Player who) {
        if (who == null) { return true; }

        for (int i = 0; i < who.getInventory().offhand.size(); i++) {
            ItemStack one = who.getInventory().offhand.get(i);
            ItemStack two = gungingoom$originalOffhand.size() > i ? gungingoom$originalOffhand.get(i) : null;

            // If any of these differ, then the offhand changed. It is not redundant
            if (!one.equals(two, false)) { return false; }
        }
        return true;
    }

    /*  // Commenting reason: Seems to always run duplicated with CLIENTBOUND_SET_EQUIPMENT_SEND_HANDLE_EQUIPMENT_CHANGES that already handles MAINHAND change
    @Inject(method = "handleSetCarriedItem", at = @At("HEAD"))
    protected void onCarriedItemCall(@NotNull ServerboundSetCarriedItemPacket pPacket, CallbackInfo ci) {
        // Check diffs
        if (this.getPlayer().getInventory().selected != pPacket.getSlot()) {
            ExtensionEventBroadcaster.BroadcastEquipmentChangeEvent(ItemFlowExtensionReason.SERVERBOUND_SET_CARRIED_ITEM_HANDLE, false, EquipmentSlot.MAINHAND, this.getPlayer());
        }
    }   //*/

    @Inject(method = "handlePickItem", at = @At("RETURN"))
    protected void onHandlePickItemCall(ServerboundPickItemPacket pPacket, CallbackInfo ci) {
        ExtensionEventBroadcaster.BroadcastEquipmentChangeEvent(ItemFlowExtensionReason.CLIENTBOUND_SET_CARRIED_ITEM_PACKET_SEND_HANDLE_PICK_ITEM, false, EquipmentSlot.MAINHAND, this.getPlayer());
    }

    @Inject(method = "handleUseItemOn", at = @At("HEAD"))
    protected void onHandleUseItemOnCall(ServerboundUseItemOnPacket pPacket, CallbackInfo ci) {
        gungingoom$prepareOriginals(this.getPlayer());
    }
    @Inject(method = "handleUseItemOn", at = @At("RETURN"))
    protected void onHandleUseItemOnReturn(ServerboundUseItemOnPacket pPacket, CallbackInfo ci) {

        // Was there a change in the main hand?
        if (!gungingoom$isMainhandRedundant(this.getPlayer(), true)) {

            // Mainhand change
            ExtensionEventBroadcaster.BroadcastEquipmentChangeEvent(ItemFlowExtensionReason.SERVERBOUND_USE_ITEM_ON_HANDLE, false, EquipmentSlot.MAINHAND, this.getPlayer());
        }

        // Was there a change in the offhand?
        if (!gungingoom$isOffhandRedundant(this.getPlayer())) {

            // Mainhand change
            ExtensionEventBroadcaster.BroadcastEquipmentChangeEvent(ItemFlowExtensionReason.SERVERBOUND_USE_ITEM_ON_HANDLE, false, EquipmentSlot.OFFHAND, this.getPlayer());
        }
    }

    @Inject(method = "handleInteract", at = @At("HEAD"))
    protected void onHandleInteractCall(ServerboundInteractPacket pPacket, CallbackInfo ci) {
        gungingoom$prepareOriginals(this.getPlayer());
    }

    @Inject(method = "handleInteract", at = @At("RETURN"))
    protected void onHandleInteractReturn(ServerboundInteractPacket pPacket, CallbackInfo ci) {

        // Was there a change in the main hand?
        if (!gungingoom$isMainhandRedundant(this.getPlayer(), true)) {

            // Mainhand change
            ExtensionEventBroadcaster.BroadcastEquipmentChangeEvent(ItemFlowExtensionReason.SERVERBOUND_INTERACT_HANDLE, false, EquipmentSlot.MAINHAND, this.getPlayer());
        }

        // Was there a change in the offhand?
        if (!gungingoom$isOffhandRedundant(this.getPlayer())) {

            // Mainhand change
            ExtensionEventBroadcaster.BroadcastEquipmentChangeEvent(ItemFlowExtensionReason.SERVERBOUND_INTERACT_HANDLE, false, EquipmentSlot.OFFHAND, this.getPlayer());
        }
    }

    /*
    @Inject(method = "handleContainerClick", at = @At("HEAD"))
    protected void onHandleContainerClickCall(ServerboundContainerClickPacket pPacket, CallbackInfo ci) {
        GungingOotilitiesMod.Log("GOOP SGP handleContainerClick Index [" + pPacket.getSlotNum() + "] |Container ID|: " + pPacket.getContainerId() + " , Butt: " + pPacket.getButtonNum());
    }
    @Inject(method = "handleContainerClick", at = @At("RETURN"))
    protected void onHandleContainerClickReturn(ServerboundContainerClickPacket pPacket, CallbackInfo ci) {
        // ItemFlowExtensionReason.SERVERBOUND_CONTAINER_CLICK_HANDLE

        GungingOotilitiesMod.Log("GOOP SGP handleContainerClick <Coming Soon>");
    }

    @Inject(method = "handleSetCreativeModeSlot", at = @At("HEAD"))
    protected void onHandleSetCreativeModeSlotCall(ServerboundSetCreativeModeSlotPacket pPacket, CallbackInfo ci) {
        GungingOotilitiesMod.Log("GOOP SGP handleSetCreativeModeSlot Index [" + pPacket.getSlotNum() + "]");
    }
    @Inject(method = "handleSetCreativeModeSlot", at = @At("RETURN"))
    protected void onHandleSetCreativeModeSlotReturn(ServerboundSetCreativeModeSlotPacket pPacket, CallbackInfo ci) {
        // ItemFlowExtensionReason.SERVERBOUND_SET_CREATIVE_MODE_SLOT_PACKET_HANDLE

        GungingOotilitiesMod.Log("GOOP SGP handleSetCreativeModeSlot <Coming Soon>");
    }   //*/

    //*  // COMMENTING REASON: Seems to always just run duplicated with CLIENTBOUND_SET_EQUIPMENT_SEND_HANDLE_EQUIPMENT_CHANGES that already handles offhand / mainhand changes serverside
    @Inject(method = "handlePlayerAction", at = @At("HEAD"))
    protected void onHandlePlayerActionCall(@NotNull ServerboundPlayerActionPacket pPacket, CallbackInfo ci) {

        // Supported player actions
        switch (pPacket.getAction()) {
            case DROP_ITEM:
            case DROP_ALL_ITEMS:
            case SWAP_ITEM_WITH_OFFHAND:
                break;

            default: return;
        }

        // Prepare
        gungingoom$prepareOriginals(this.getPlayer());
    }

    @Inject(method = "handlePlayerAction", at = @At("RETURN"))
    protected void onHandlePlayerActionReturn(@NotNull ServerboundPlayerActionPacket pPacket, CallbackInfo ci) {

        // Supported player actions
        switch (pPacket.getAction()) {
            case DROP_ITEM:
            case DROP_ALL_ITEMS:
            case SWAP_ITEM_WITH_OFFHAND:
                break;

            default: return;
        }

        // Was there a change in the main hand?
        if (!gungingoom$isMainhandRedundant(this.getPlayer(), true)) {

            // Mainhand change
            ExtensionEventBroadcaster.BroadcastEquipmentChangeEvent(ItemFlowExtensionReason.SERVERBOUND_PLAYER_ACTION_HANDLE, false, EquipmentSlot.MAINHAND, this.getPlayer());
        }

        // Was there a change in the offhand?
        if (!gungingoom$isOffhandRedundant(this.getPlayer())) {

            // Mainhand change
            ExtensionEventBroadcaster.BroadcastEquipmentChangeEvent(ItemFlowExtensionReason.SERVERBOUND_PLAYER_ACTION_HANDLE, false, EquipmentSlot.OFFHAND, this.getPlayer());
        }
    }   //*/
}
