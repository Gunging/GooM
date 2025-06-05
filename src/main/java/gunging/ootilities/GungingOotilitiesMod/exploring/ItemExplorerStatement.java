package gunging.ootilities.GungingOotilitiesMod.exploring;

import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

/**
 * This is the physical existing thing that we
 * will be exploring, it represents the actual
 * instance of something in the world that we
 * are about to explore.
 * <p></p>
 * For example, if you have a player and you know
 * you are looking for its 20th enderchest slot,
 * then the "statement" for the explorer is "ec20"
 *
 * @see ItemExplorerElaborator
 *
 * @since 1.0.0
 * @author Gunging
 */
public interface ItemExplorerStatement<Elaborator extends ItemExplorerElaborator<? extends E>, E> {

    /**
     * @param elaborator The entity that is requesting these slots, thus
     *                   elaborating something like "HOTBAR" will return
     *                   their hotbar slots... even if they were modified
     *                   by another mod.
     *
     * @return A distinct ItemStackExplorer that targets a single slot for
     *         every slot that is encoded for in this range or special slot.
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull ArrayList<ItemStackExplorer<Elaborator, E>> whenElaborated(@NotNull Elaborator elaborator);

    /**
     * @param elaborator The entity requesting these slots, then if they ask for
     *                   the "MAINHAND" it is a valid existing slot that can be
     *                   retrieved.
     *
     * @return A real ItemStack slot/location ready to be interacted with, or <code>null</code> if it failed.
     *
     * @author Gunging
     * @since 1.0.0
     */
    @Nullable ItemStackLocation<E> whenRealized(@NotNull Elaborator elaborator);

    /**
     * @param target The player requesting these slots, then if they ask for
     *               the "MAINHAND" it is a valid existing slot that can be
     *               retrieved.
     *
     * @return The item currently present in this location
     *
     * @author Gunging
     * @since 1.0.0
     */
    @Nullable ItemStack readItemStack(@NotNull E target);

    /**
     * @param target The player requesting these slots, then if they ask for
     *               the "MAINHAND" it is a valid existing slot that can be
     *               written onto.
     *
     * @param item The item to put into that slot
     *
     * @author Gunging
     * @since 1.0.0
     */
    void writeItemStack(@NotNull E target, @Nullable ItemStack item);

    /**
     * Some slot specializations encode for multiple slots, in which case some
     * methods cannot be used. Indeed, it is complicated working with things
     * that mean multiple things at the same time.
     * <p>
     * Anyway, if this is true, then this is a "most-basic" building block
     * that encodes for one thing only and needs no more elaboration.
     *
     * @return If this Slot Specialization may not be elaborated further
     *
     * @author Gunging
     * @since 1.0.0
     */
    boolean isFundamental();
}
