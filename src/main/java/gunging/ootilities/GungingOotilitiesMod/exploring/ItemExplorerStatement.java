package gunging.ootilities.GungingOotilitiesMod.exploring;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
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
 * For example, if you have a player, and you know
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
     * <b>This must always encode an integer followed by a string, and only that. </b>
     *
     * @param buff Network buff to which encode this
     *
     * @since 1.0.0
     * @author Gunging
     */
    default void encode(@NotNull FriendlyByteBuf buff) {
        buff.writeInt(getNetworkIndex());
        buff.writeUtf(getOptions()); }

    /**
     * <b>Only touch if you know what you are doing.</b> Assigns an index to
     * this statement when registered so that it can be synced over the network
     * in the form of a single number. Furthermore, simplifies Hash and Equals
     * by checking if this is identical, since it is the same for all aliases
     * of the same statement name.
     * <br>
     * Equals will check that options match too, though.
     *
     * @param n The network index to assign this statement
     *
     * @since 1.0.0
     * @author Gunging
     */
    @NotNull ItemExplorerStatement<Elaborator, E> setNetworkIndex(int n);

    /**
     * @return The network index of this statement, not guaranteed to persist across sessions.
     *
     * @see #setNetworkIndex(int)
     *
     * @since 1.0.0
     * @author Gunging
     */
    int getNetworkIndex();

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

    /**
     * For example, enderchest slots' name is "gungingoom:ec" while the
     * head slot is "gungingoom:head", most often this is followed by the
     * options string to actually mean something "gungingoom:ec10" would be
     * the statement of the 10th enderchest slot.
     *
     * @return The name of this statement that will uniquely encode for it
     *         so that it can be referenced from anywhere, as well as saved
     *         persistently.
     *
     * @see #getOptions()
     *
     * @since 1.0.0
     * @author Gunging
     */
    @NotNull ResourceLocation getStatementName();

    /**
     * For example a number or a range, "3..10" to encode for slot indices 3
     * through 10, though it will only be complete when appended to this
     * statement's name. For example "gungingoom:main0..8" would be the
     * statement for the hotbar (or just use "gungingoom:hotbar").
     *
     * @return The configuration that identifies this specific instance of this statement.
     *
     * @see #getStatementName()
     *
     * @since 1.0.0
     * @author Gunging
     */
    @NotNull String getOptions();

    /**
     * @param options The "options" that will modify this statement.
     *
     * @return A new instance of this statement that complies with these options.
     *
     * @see #getOptions()
     * @see #getStatementName()
     *
     * @since 1.0.0
     * @author Gunging
     */
    @Nullable ItemExplorerStatement<Elaborator, E> withOptions(@NotNull String options);

    /**
     * @return The class of the target of the elaborator.
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull Class<E> getElaboratorTarget();

    /**
     * @return An explorer that uses this statement
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull ItemStackExplorer<Elaborator, E> prepareExplorer();

    /**
     * @return An elaborator wrapper for this target
     *
     * @param target The elaboration target whose inventory containers will be explored
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull Elaborator prepareElaborator(@NotNull E target);

    /**
     * @param elaborator The object that may be accepted by this Item Explorer Statement
     *
     * @return If this Item Explorer Statement can search this elaborator's containers/inventories
     *
     * @author Gunging
     * @since 1.0.0
     */
    default boolean acceptsElaborator(@Nullable Object elaborator) { return getElaboratorTarget().isInstance(elaborator); }
}
