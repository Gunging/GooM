package gunging.ootilities.GungingOotilitiesMod.exploring.entities;

import gunging.ootilities.GungingOotilitiesMod.exploring.ItemExplorerStatement;
import gunging.ootilities.GungingOotilitiesMod.exploring.ItemStackExplorer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

/**
 * Deals with the slots of an Entity's inventory
 *
 * @since 1.0.0
 * @author Gunging
 */
public abstract class ISEEntityStatement implements ItemExplorerStatement<ISEEntityElaborator, Entity> {

    /**
     * @see #setNetworkIndex(int)
     *
     * @since 1.0.0
     */
    int networkIndex;

    /**
     * @since 1.0.0
     * @author Gunging
     */
    @NotNull @Override public ISEEntityStatement setNetworkIndex(int n) { networkIndex = n; return this; }

    /**
     * @since 1.0.0
     * @author Gunging
     */
    @Override public int getNetworkIndex() { return networkIndex; }

    /**
     * @since 1.0.0
     * @author Gunging
     */
    @Override public String toString() { return getStatementName() + getOptions(); }

    /**
     * The internal name of this explorer statement
     *
     * @since 1.0.0
     */
    @NotNull final ResourceLocation statementName;

    /**
     * @since 1.0.0
     * @author Gunging
     */
    @Override  public @NotNull ResourceLocation getStatementName() { return statementName; }

    @Override
    public boolean equals(Object obj) {

        // Needs to be a statement to be equal
        if (!(obj instanceof ItemExplorerStatement<?,?>)) { return false; }
        ItemExplorerStatement<?,?> comparate = (ItemExplorerStatement<?,?>) obj;

        // Must match both name and options in its most fundamental form
        return (comparate.getNetworkIndex() == getNetworkIndex()) && comparate.getOptions().equals(getOptions());
    }

    /**
     * @since 1.0.0
     * @author Gunging
     */
    @Override public int hashCode() { return networkIndex; }

    /**
     * @param statementName The internal name of this explorer statement
     *
     * @since 1.0.0
     * @author Gunging
     */
    protected ISEEntityStatement(@NotNull ResourceLocation statementName) { this.statementName = statementName; }

    /**
     * @param elaborator The entity that is requesting these slots, thus
     *                   elaborating something like "HOTBAR" will return
     *                   their hotbar slots... even if they were modified
     *                   by another mod.
     *
     * @return A distinct ISEEntityInventory that targets a single slot for
     *         every slot that is encoded for in this range or special slot.
     *
     * @author Gunging
     * @since 1.0.0
     */
    public abstract @NotNull ArrayList<ItemStackExplorer<ISEEntityElaborator, Entity>> whenElaborated(@NotNull ISEEntityElaborator elaborator);

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
    @Nullable public abstract ISEEntityLocation whenRealized(@NotNull ISEEntityElaborator elaborator);

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
    @Nullable public abstract ItemStack readItemStack(@NotNull Entity target);

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
    public abstract void writeItemStack(@NotNull Entity target, @Nullable ItemStack item);

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
     public boolean isFundamental() { return true; }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @NotNull Class<Entity> getElaboratorTarget() { return Entity.class; }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @NotNull ItemStackExplorer<ISEEntityElaborator, Entity> prepareExplorer() { return new ISEEntityExplorer(this); }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @NotNull ISEEntityElaborator prepareElaborator(@NotNull Entity target) { return new ISEEntityElaborator(target); }
}
