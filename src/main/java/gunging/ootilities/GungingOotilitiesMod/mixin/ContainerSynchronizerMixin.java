package gunging.ootilities.GungingOotilitiesMod.mixin;

import gunging.ootilities.GungingOotilitiesMod.events.ExtensionEventBroadcaster;
import gunging.ootilities.GungingOotilitiesMod.events.extension.ItemFlowExtensionReason;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
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

@Mixin(targets = "net/minecraft/server/level/ServerPlayer$1")
public abstract class ContainerSynchronizerMixin {

    @Shadow @Final
    ServerPlayer this$0;

    @Unique
    public int gungingoom$originalSlot;

    @Unique
    public ItemStack gungingoom$originalItem;

    @Unique
    void gungingoom$prepareOriginals(@NotNull Player who) {

        // Remember their original slot
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

    /*

    @Inject(method = "sendInitialData", at = @At("RETURN"))
    protected void onSendInitialDataReturn(CallbackInfo ci) {

        // All the equipment is refreshed, so
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ExtensionEventBroadcaster.BroadcastEquipmentChangeEvent(ItemFlowExtensionReason.SERVER_SEND_INITIAL_DATA, false, slot, this$0); }
    }

    //*/

    @Inject(method = "sendCarriedChange", at = @At("RETURN"))
    protected void onSendCarriedChangeReturn(CallbackInfo ci) {
        ExtensionEventBroadcaster.BroadcastEquipmentChangeEvent(ItemFlowExtensionReason.CLIENTBOUND_CONTAINER_SET_SLOT_SEND_SEND_CARRIED_CHANGE, false, EquipmentSlot.MAINHAND, this$0);
    }
}
