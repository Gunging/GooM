package gunging.ootilities.GungingOotilitiesMod.exploring;

import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a specific inventory slot somewhere, ready to
 * be interacted with by reading or modifying its contents.
 * It is the final result of evaluating an ItemStack Explorer
 *
 * @see ItemStackExplorer
 *
 * @author Gunging
 * @since 1.0.0
 */
public interface ItemStackLocation<E> {

    /**
     * @return The container of this ItemStack Location,
     *         whatever that means in this context
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull E getHolder();

    /**
     * @param stack The ItemStack to place in this slot
     *
     * @author Gunging
     * @since 1.0.0
     */
    void setItemStack(@Nullable ItemStack stack);

    /**
     * @return The item stack currently in this slot
     */
    @Nullable ItemStack getItemStack();
}
