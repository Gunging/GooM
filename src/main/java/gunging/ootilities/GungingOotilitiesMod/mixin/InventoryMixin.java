package gunging.ootilities.GungingOotilitiesMod.mixin;

import gunging.ootilities.GungingOotilitiesMod.events.ExtensionEventBroadcaster;
import gunging.ootilities.GungingOotilitiesMod.events.extension.ItemFlowExtensionReason;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Inventory.class)
public abstract class InventoryMixin {

    @Unique
    public int gungingoom$originalSlot;

    @Unique
    public ItemStack gungingoom$originalItem;

    @Unique
    void gungingoom$prepareOriginals() {

        // Remember their original slot
        gungingoom$originalSlot = this.selected;
        gungingoom$originalItem = this.getSelected();
    }

    @Unique
    boolean gungingoom$isRedundant(boolean deep) {

        return
                // Redundant if the selection is unchanged
                gungingoom$originalSlot == this.selected &&

                // If searching in deep mode, also check identical items
                (!deep || gungingoom$originalItem.equals(this.getSelected(), false));
    }

    @Shadow
    public int selected;

    @Shadow
    @NotNull
    public abstract ItemStack getSelected();

    @Shadow
    @Final
    @NotNull
    public Player player;

    @Inject(method = "setPickedItem", at = @At("RETURN"))
    protected void onSetPickedItemReturn(ItemStack pStack, CallbackInfo ci) {
        ExtensionEventBroadcaster.BroadcastEquipmentChangeEvent(ItemFlowExtensionReason.CLIENT_INVENTORY_SET_PICKED_ITEM, true, EquipmentSlot.MAINHAND, this.player);
    }

    @Inject(method = "pickSlot", at = @At("RETURN"))
    protected void onPickSlotReturn(int pIndex, CallbackInfo ci) {
        ExtensionEventBroadcaster.BroadcastEquipmentChangeEvent(ItemFlowExtensionReason.CLIENT_INVENTORY_PICK_SLOT, true, EquipmentSlot.MAINHAND, this.player);
    }

    @Inject(method = "swapPaint", at = @At("RETURN"))
    protected void onSwapPaintReturn(double pDirection, CallbackInfo ci) {
        ExtensionEventBroadcaster.BroadcastEquipmentChangeEvent(ItemFlowExtensionReason.CLIENT_INVENTORY_SWAP_PAINT, true, EquipmentSlot.MAINHAND, this.player);
    }

    @Inject(method = "replaceWith", at = @At("RETURN"))
    protected void onReplaceWithReturn(Inventory pPlayerInventory, CallbackInfo ci) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ExtensionEventBroadcaster.BroadcastEquipmentChangeEvent(ItemFlowExtensionReason.CLIENT_INVENTORY_REPLACE_WITH, true, slot, this.player);
        }
    }

    /*
    @Inject(method = "placeItemBackInInventory(Lnet/minecraft/world/item/ItemStack;Z)V", at = @At("HEAD"))
    protected void onPlaceBackInInventoryCall(ItemStack pStack, boolean pSendPacket, CallbackInfo ci) {
        gungingoom.Log("ASI INV Place Back to inventory");
    }
    @Inject(method = "placeItemBackInInventory(Lnet/minecraft/world/item/ItemStack;Z)V", at = @At("RETURN"))
    protected void onPlaceBackInInventoryReturn(ItemStack pStack, boolean pSendPacket, CallbackInfo ci) {
        // ItemFlowExtensionReason.CLIENTBOUND_CONTAINER_SET_SLOT_SEND_PLACE_ITEM_BACK_IN_INVENTORY

        gungingoom.Log("ASI INV placeItemBackInInventory <Coming Soon>");
    }   //*/
}
