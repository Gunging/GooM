package gunging.ootilities.GungingOotilitiesMod.mixin;

import gunging.ootilities.GungingOotilitiesMod.events.ExtensionEventBroadcaster;
import gunging.ootilities.GungingOotilitiesMod.events.extension.ItemFlowExtensionReason;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Attackable, net.minecraftforge.common.extensions.IForgeLivingEntity {

    public LivingEntityMixin(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Inject(method = "handleEquipmentChanges", at = @At("RETURN"))
    protected void onHandleEquipmentChangesReturn(Map<EquipmentSlot, ItemStack> pEquipments, CallbackInfo ci) {

        // Send an update for every change here
        for (Map.Entry<EquipmentSlot, ItemStack> slot : pEquipments.entrySet()) {
            ExtensionEventBroadcaster.BroadcastEquipmentChangeEvent(ItemFlowExtensionReason.CLIENTBOUND_SET_EQUIPMENT_SEND_HANDLE_EQUIPMENT_CHANGES, false, slot.getKey(), (LivingEntity) (Object) this);
        }
    }

    /*
    @Inject(method = "take", at = @At("RETURN"))
    protected void onTakeReturn(Entity pEntity, int pAmount, CallbackInfo ci) {
        if (!(pEntity instanceof ItemEntity)) { return; }
        ItemStack pickedUp = ((ItemEntity) pEntity).getItem();

        // Send an update for every change here
        for (EquipmentSlot slot : EquipmentSlot.values()) {

            ISLEntityInventory stackLocation = new ISLEntityInventory((LivingEntity) (Object) this, ISESlotSpecializations.getByEquipmentSlot(slot));

            ItemStack inEquipped = stackLocation.getItemStack();
            if (inEquipped == null) { continue; }
            if (!inEquipped.equals(pickedUp, true)) { continue; }

            ExtensionEventBroadcaster.BroadcastEquipmentChangeEvent(ItemFlowExtensionReason.CLIENTBOUND_TAKE_ITEM_ENTITY_SEND_TAKE, false, slot, this);
        }
    }   //*/
}
