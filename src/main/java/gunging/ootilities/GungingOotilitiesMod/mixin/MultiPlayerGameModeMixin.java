package gunging.ootilities.GungingOotilitiesMod.mixin;

import gunging.ootilities.GungingOotilitiesMod.GungingOotilitiesMod;
import gunging.ootilities.GungingOotilitiesMod.events.ExtensionEventBroadcaster;
import gunging.ootilities.GungingOotilitiesMod.events.extension.ItemFlowExtensionReason;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;

@Mixin(MultiPlayerGameMode.class)
public abstract class MultiPlayerGameModeMixin {

    @Shadow @Final private Minecraft minecraft;

    @Shadow private int carriedIndex;

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
            ItemStack two = gungingoom$originalOffhand.get(i);

            // If any of these differ, then the offhand changed. It is not redundant
            if (!one.equals(two, false)) { return false; }
        }
        return true;
    }

    @Inject(method = "ensureHasSentCarriedItem", at = @At("HEAD"))
    protected void onEnsureHasSentCarriedItemCall(CallbackInfo ci) {

        // If a player actually exists
        if (this.minecraft.player == null) { return; }

        // Remember their original slot
        gungingoom$originalSlot = carriedIndex;

        // No changes? Sleep
        if (this.carriedIndex == this.minecraft.player.getInventory().selected) { return; }

        // Changes! Send event
        ExtensionEventBroadcaster.BroadcastEquipmentChangeEvent(ItemFlowExtensionReason.SERVERBOUND_SET_CARRIED_ITEM_SEND_ENSURE_HAS_SENT_CARRIED_ITEM, true, EquipmentSlot.MAINHAND, this.minecraft.player);
    }

    @Inject(method = "useItemOn", at = @At("HEAD"))
    protected void onUseItemOnCall(LocalPlayer pPlayer, InteractionHand pHand, BlockHitResult pResult, CallbackInfoReturnable<InteractionResult> cir) {
        gungingoom$prepareOriginals(this.minecraft.player);
    }
    @Inject(method = "useItemOn", at = @At("RETURN"))
    protected void onUseItemOnReturn(LocalPlayer pPlayer, InteractionHand pHand, BlockHitResult pResult, CallbackInfoReturnable<InteractionResult> cir) {

        // Was there a change in the main hand?
        if (!gungingoom$isMainhandRedundant(this.minecraft.player, true)) {

            // Mainhand change
            ExtensionEventBroadcaster.BroadcastEquipmentChangeEvent(ItemFlowExtensionReason.SERVERBOUND_USE_ITEM_ON_SEND_USE_ITEM_ON, true, EquipmentSlot.MAINHAND, this.minecraft.player);
        }

        // Was there a change in the offhand?
        if (!gungingoom$isOffhandRedundant(this.minecraft.player)) {

            // Mainhand change
            ExtensionEventBroadcaster.BroadcastEquipmentChangeEvent(ItemFlowExtensionReason.SERVERBOUND_USE_ITEM_ON_SEND_USE_ITEM_ON, true, EquipmentSlot.OFFHAND, this.minecraft.player);
        }
    }

    @Inject(method = "attack", at = @At("HEAD"))
    protected void onAttackCall(Player pPlayer, Entity pTargetEntity, CallbackInfo ci) {
        gungingoom$prepareOriginals(this.minecraft.player);
    }
    @Inject(method = "attack", at = @At("RETURN"))
    protected void onAttackReturn(Player pPlayer, Entity pTargetEntity, CallbackInfo ci) {

        // Was there a change in the main hand?
        if (!gungingoom$isMainhandRedundant(this.minecraft.player, true)) {

            // Mainhand change
            ExtensionEventBroadcaster.BroadcastEquipmentChangeEvent(ItemFlowExtensionReason.SERVERBOUND_INTERACT_SEND_ATTACK, true, EquipmentSlot.MAINHAND, this.minecraft.player);
        }

        // Was there a change in the offhand?
        if (!gungingoom$isOffhandRedundant(this.minecraft.player)) {

            // Mainhand change
            ExtensionEventBroadcaster.BroadcastEquipmentChangeEvent(ItemFlowExtensionReason.SERVERBOUND_INTERACT_SEND_ATTACK, true, EquipmentSlot.OFFHAND, this.minecraft.player);
        }
    }

    @Inject(method = "interact", at = @At("HEAD"))
    protected void onInteractCall(Player pPlayer, Entity pTarget, InteractionHand pHand, CallbackInfoReturnable<InteractionResult> cir) {
        gungingoom$prepareOriginals(this.minecraft.player);
    }
    @Inject(method = "interact", at = @At("RETURN"))
    protected void onInteractReturn(Player pPlayer, Entity pTarget, InteractionHand pHand, CallbackInfoReturnable<InteractionResult> cir) {

        // Was there a change in the main hand?
        if (!gungingoom$isMainhandRedundant(this.minecraft.player, true)) {

            // Mainhand change
            ExtensionEventBroadcaster.BroadcastEquipmentChangeEvent(ItemFlowExtensionReason.SERVERBOUND_INTERACT_SEND_INTERACT, true, EquipmentSlot.MAINHAND, this.minecraft.player);
        }

        // Was there a change in the offhand?
        if (!gungingoom$isOffhandRedundant(this.minecraft.player)) {

            // Mainhand change
            ExtensionEventBroadcaster.BroadcastEquipmentChangeEvent(ItemFlowExtensionReason.SERVERBOUND_INTERACT_SEND_INTERACT, true, EquipmentSlot.OFFHAND, this.minecraft.player);
        }
    }

    @Inject(method = "interactAt", at = @At("HEAD"))
    protected void onInteractAtCall(Player pPlayer, Entity pTarget, EntityHitResult pRay, InteractionHand pHand, CallbackInfoReturnable<InteractionResult> cir) {
        gungingoom$prepareOriginals(this.minecraft.player);
    }
    @Inject(method = "interactAt", at = @At("RETURN"))
    protected void onInteractAtReturn(Player pPlayer, Entity pTarget, EntityHitResult pRay, InteractionHand pHand, CallbackInfoReturnable<InteractionResult> cir) {

        // Was there a change in the main hand?
        if (!gungingoom$isMainhandRedundant(this.minecraft.player, true)) {

            // Mainhand change
            ExtensionEventBroadcaster.BroadcastEquipmentChangeEvent(ItemFlowExtensionReason.SERVERBOUND_INTERACT_SEND_INTERACT_AT, true, EquipmentSlot.MAINHAND, this.minecraft.player);
        }

        // Was there a change in the offhand?
        if (!gungingoom$isOffhandRedundant(this.minecraft.player)) {

            // Mainhand change
            ExtensionEventBroadcaster.BroadcastEquipmentChangeEvent(ItemFlowExtensionReason.SERVERBOUND_INTERACT_SEND_INTERACT_AT, true, EquipmentSlot.OFFHAND, this.minecraft.player);
        }
    }

    @Inject(method = "handleCreativeModeItemAdd", at = @At("HEAD"))
    protected void onHandleCreativeModeItemAddCall(ItemStack pStack, int pSlotId, CallbackInfo ci) {

        GungingOotilitiesMod.Log("GOOP MPG Creative Add Index [" + pSlotId + "]");
    }
    @Inject(method = "handleCreativeModeItemAdd", at = @At("RETURN"))
    protected void onHandleCreativeModeItemAddReturn(ItemStack pStack, int pSlotId, CallbackInfo ci) {
        if (this.minecraft.player == null) { return; }
        // ItemFlowExtensionReason.SERVERBOUND_SET_CREATIVE_MODE_SLOT_PACKET_SEND_ITEM_ADD

        GungingOotilitiesMod.Log("GOOP MPG handleCreativeModeItemAdd <Coming Soon>");
    }

    @Inject(method = "handleCreativeModeItemDrop", at = @At("HEAD"))
    protected void onHandleCreativeModeItemDropCall(ItemStack pStack, CallbackInfo ci) {
        gungingoom$prepareOriginals(this.minecraft.player);
    }
    @Inject(method = "handleCreativeModeItemDrop", at = @At("RETURN"))
    protected void onHandleCreativeModeItemDropReturn(ItemStack pStack, CallbackInfo ci) {

        // Was there a change in the main hand?
        if (!gungingoom$isMainhandRedundant(this.minecraft.player, true)) {

            // Mainhand change
            ExtensionEventBroadcaster.BroadcastEquipmentChangeEvent(ItemFlowExtensionReason.SERVERBOUND_SET_CREATIVE_MODE_SLOT_PACKET_SEND_ITEM_DROP, true, EquipmentSlot.MAINHAND, this.minecraft.player);
        }
    }

    @Inject(method = "handleInventoryMouseClick", at = @At("HEAD"))
    protected void onHandleInventoryMouseClickCall(int pContainerId, int pSlotId, int pMouseButton, ClickType pClickType, Player pPlayer, CallbackInfo ci) {
        GungingOotilitiesMod.Log("GOOP MPG Mouse Click Index [" + pSlotId + "] |Container ID|: " + pContainerId);
    }
    @Inject(method = "handleInventoryMouseClick", at = @At("RETURN"))
    protected void onHandleInventoryMouseClickReturn(int pContainerId, int pSlotId, int pMouseButton, ClickType pClickType, Player pPlayer, CallbackInfo ci) {
        // ItemFlowExtensionReason.SERVERBOUND_CONTAINER_CLICK_SEND_HANDLE_MOUSE_CLICK

        GungingOotilitiesMod.Log("GOOP MPG handleCreativeModeItemAdd <Coming Soon>");
    }
}
