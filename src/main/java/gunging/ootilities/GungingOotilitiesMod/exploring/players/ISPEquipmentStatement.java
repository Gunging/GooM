package gunging.ootilities.GungingOotilitiesMod.exploring.players;

import gunging.ootilities.GungingOotilitiesMod.exploring.entities.ISEEquipmentSlotted;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Deals with vanilla {@link net.minecraft.world.entity.EquipmentSlot} slots in Players
 *
 * @since 1.0.0
 * @author Gunging
 */
public abstract class ISPEquipmentStatement extends ISPPlayerStatement implements ISEEquipmentSlotted {

    /**
     * @param statementName The internal name of this explorer statement
     *
     * @author Gunging
     * @since 1.0.0
     */
    protected ISPEquipmentStatement(@NotNull ResourceLocation statementName) {
        super(statementName);
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @Nullable ItemStack readItemStack(@NotNull Player target) {
        return target.getItemBySlot(getEquipmentSlot());
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public void writeItemStack(@NotNull Player target, @Nullable ItemStack item) {
        target.setItemSlot(getEquipmentSlot(), item == null ? ItemStack.EMPTY : item);
    }

    /**
     * @author Gunging
     * @since 1.0.0
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
