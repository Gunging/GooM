package gunging.ootilities.GungingOotilitiesMod.exploring.entities;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Deals with vanilla {@link net.minecraft.world.entity.EquipmentSlot} slots in Living Entities
 *
 * @since 1.0.0
 * @author Gunging
 */
public abstract class ISEEquipmentStatement extends ISEEntityStatement implements ISEEquipmentSlotted {

    /**
     * @param statementName The internal name of this explorer statement
     *
     * @author Gunging
     * @since 1.0.0
     */
    protected ISEEquipmentStatement(@NotNull ResourceLocation statementName) {
        super(statementName);
    }

    /**
     * @since 1.0.0
     * @author Gunging
     */
    @Override
    public @Nullable ItemStack readItemStack(@NotNull Entity target) {
        LivingEntity asLiving = target instanceof LivingEntity ? (LivingEntity) target : null;
        if (asLiving == null) { return null; }
        return ((LivingEntity) target).getItemBySlot(getEquipmentSlot());
    }
    /**
     * @since 1.0.0
     * @author Gunging
     */
    @Override
    public void writeItemStack(@NotNull Entity target, @Nullable ItemStack item) {
        target.setItemSlot(getEquipmentSlot(), item == null ? ItemStack.EMPTY : item);
    }
    /**
     * @since 1.0.0
     * @author Gunging
     */
    @Override
    public boolean equals(Object obj) {

        // Equipment Slot compatibility
        if (obj instanceof ISEEquipmentSlotted) {
            return getEquipmentSlot().equals(((ISEEquipmentSlotted) obj).getEquipmentSlot());
        }

        // Use superclass
        return super.equals(obj);
    }
}
