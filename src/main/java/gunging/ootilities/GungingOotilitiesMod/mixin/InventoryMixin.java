package gunging.ootilities.GungingOotilitiesMod.mixin;

import gunging.ootilities.GungingOotilitiesMod.events.ExtensionEventBroadcaster;
import gunging.ootilities.GungingOotilitiesMod.events.extension.ItemFlowExtensionReason;
import gunging.ootilities.GungingOotilitiesMod.mixininterfaces.WithStatsStack;
import gunging.ootilities.GungingOotilitiesMod.mixininterfaces.WithTransitiveStack;
import gunging.ootilities.GungingOotilitiesMod.ootilityception.OotilityNumbers;
import gunging.ootilities.GungingOotilitiesMod.stats.core.*;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;

@Mixin(Inventory.class)
public abstract class InventoryMixin implements WithTransitiveStack {

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

    @Shadow @Final public NonNullList<ItemStack> armor;

    @Shadow @Final public NonNullList<ItemStack> offhand;

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

    @Unique @Nullable TransitiveStack gungingoom$stats;

    @Override
    public @NotNull StatStacked gungingoom$getParentStack() {
        return ((WithStatsStack) player).gungingoom$getStatStack();
    }

    @Override
    public @NotNull TransitiveStack gungingoom$getContainedStatStacks() {
        if (gungingoom$stats == null) {
            gungingoom$stats = new TransitiveStack(this);
            gungingoom$stats.setParentStack(gungingoom$getParentStack());
            gungingoom$stats.preStatTotalsReloaded(sm -> gungingoom$refreshEquipmentStats());
            gungingoom$getParentStack().getChildStacks().add(gungingoom$stats);
            gungingoom$stats.parentalChainRegisterChanges();
        }
        return gungingoom$stats;
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    public void whenConstructed(Player pPlayer, CallbackInfo ci) {
        gungingoom$getContainedStatStacks();
    }

    @Unique
    public void gungingoom$refreshEquipmentStats() {
        gungingoom$getContainedStatStacks().getChildStacks().clear();

        // Armor
        for (ItemStack item : armor) {
            if (OotilityNumbers.isAir(item)) { continue; }
            WithStatsStack asStats = (WithStatsStack) (Object) item;
            gungingoom$getContainedStatStacks().getChildStacks().add(asStats.gungingoom$getStatStack());
        }

        // Offhand
        for (ItemStack item : offhand) {
            if (OotilityNumbers.isAir(item)) { continue; }
            WithStatsStack asStats = (WithStatsStack) (Object) item;
            gungingoom$getContainedStatStacks().getChildStacks().add(asStats.gungingoom$getStatStack());
        }

        // Mainhand
        ItemStack main = getSelected();
        if (!OotilityNumbers.isAir(main)) {
            WithStatsStack asStats = (WithStatsStack) (Object) main;
            gungingoom$getContainedStatStacks().getChildStacks().add(asStats.gungingoom$getStatStack()); }
    }

    @Override
    public @NotNull ArrayList<StatStacked> gungingoom$getChildStacks() {
        return gungingoom$getContainedStatStacks().getChildStacks();
    }
}
