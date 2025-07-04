package gunging.ootilities.GungingOotilitiesMod.mixin;

import gunging.ootilities.GungingOotilitiesMod.events.ExtensionEventBroadcaster;
import gunging.ootilities.GungingOotilitiesMod.events.extension.ItemFlowExtensionReason;
import gunging.ootilities.GungingOotilitiesMod.exploring.players.ISPExplorerStatements;
import gunging.ootilities.GungingOotilitiesMod.exploring.players.ISPPlayerLocation;
import gunging.ootilities.GungingOotilitiesMod.mixininterfaces.PlayerLinked;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(AbstractContainerMenu.class)
public class AbstractContainerMenuMixin {


    @Inject(method = "initializeContents", at = @At("RETURN"))
    public void onInitializeCarriedItem(int pStateId, List<ItemStack> pItems, ItemStack pCarried, CallbackInfo ci) {
        if (pCarried == ItemStack.EMPTY) { return; }

        // Read player associated with this menu
        Player who = null;
        AbstractContainerMenu asMenu = (AbstractContainerMenu) (Object) this;
        if (asMenu instanceof PlayerLinked) {
            who = ((PlayerLinked) asMenu).gungingoom$getAssociatedPlayer(); }

        // No subject, no point
        if (who == null) { return; }
        ExtensionEventBroadcaster.BroadcastEquipmentChangeEvent(ItemFlowExtensionReason.SERVER_ON_INITIALIZE_CARRIED_ITEM, false, new ISPPlayerLocation(who, ISPExplorerStatements.CURSOR));

    }

    @Inject(method = "setCarried", at = @At("RETURN"))
    public void onSetCarriedItem(ItemStack pStack, CallbackInfo ci) {

        // Read player associated with this menu
        Player who = null;
        AbstractContainerMenu asMenu = (AbstractContainerMenu) (Object) this;
        if (asMenu instanceof PlayerLinked) {
            who = ((PlayerLinked) asMenu).gungingoom$getAssociatedPlayer(); }

        // No subject, no point
        if (who == null) { return; }
        ExtensionEventBroadcaster.BroadcastEquipmentChangeEvent(ItemFlowExtensionReason.SERVER_ON_SET_CARRIED_ITEM, false, new ISPPlayerLocation(who, ISPExplorerStatements.CURSOR));
    }
}
