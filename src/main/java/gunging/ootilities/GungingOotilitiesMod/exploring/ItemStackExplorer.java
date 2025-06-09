package gunging.ootilities.GungingOotilitiesMod.exploring;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

/**
 * Represents an abstract inventory slot. Not only
 * abstract in slot, but inventory GUI itself.
 * <p><p>
 * For example, the simplest case slot '0' of the normal
 * player inventory: Simple and straight-forward.
 * <p>
 * Or maybe, slot 0 of the enderchest? It could be!
 * <p>
 * Or perhaps a keyword and not an index like the "mainhand" slot.
 * Strictly speaking this is the slot currently selected of the
 * hotbar, so it could be anything 0-8 depending on when it is
 * evaluated.
 * <p>
 * Or what about a range? Multiple slots! Or inside
 * shulker boxes, or custom inventories altogether.
 *
 * @author Gunging
 * @since 1.0.0
 */
public interface ItemStackExplorer<Elaborator extends ItemExplorerElaborator<? extends E>, E> {

    /**
     * @return The explorer statement, the slot being searched for.
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull ItemExplorerStatement<Elaborator, E> getStatement();

    /**
     * Some "Item Stack Slots" encode for multiple slots,
     * for example "0-9" is a total of 10 different slots,
     * so in this array you would receive 10 different Inventory
     * Locations each encoding for a single slot.
     * <p>
     * However, this can also be used to resolve especial
     * inventory locations, such as
     *
     * @param elaborator The context by which to elaborate the
     *                   meaning of this compound ItemStack slot,
     *                   or maybe it already is a singular slot?
     *                   In that case it would just return itself.
     *
     * @return A different ItemStack explorer for every slot
     *         this ItemStack explorer encodes for. May return
     *         a list with only itself.
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull ArrayList<? extends ItemStackExplorer<Elaborator, E>> elaborate(@NotNull Elaborator elaborator);

    /**
     * Return a ItemStack Location that points toward the slot found by this explorer.
     * Presumably, it is an explorer that targets a single specific slot somewhere,
     * and if you originally had an explorer that targets multiple slots, you
     * already elaborated it.
     *
     * @param elaborator The context by which to ground this abstract ItemStack slot. Basically,
     *                   rather than "exploring" possibilities, choose the real one to use.
     *
     * @return A real ItemStack slot/location ready to be interacted with, or <code>null</code> if it failed.
     *
     * @see #elaborate(ItemExplorerElaborator)
     *
     * @author Gunging
     * @since 1.0.0
     */
    @Nullable ItemStackLocation<? extends E> realize(@NotNull Elaborator elaborator);
}
