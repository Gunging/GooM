package gunging.ootilities.GungingOotilitiesMod.exploring.entities;

import gunging.ootilities.GungingOotilitiesMod.exploring.ItemExplorerElaborator;
import gunging.ootilities.GungingOotilitiesMod.exploring.ItemStackLocation;
import gunging.ootilities.GungingOotilitiesMod.exploring.players.ISPPlayerLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * An actual slot in the inventory of an entity,
 * ready to read or modify that item yes.
 *
 * @since 1.0.0
 * @author Gunging
 */
public class ISEEntityLocation implements ItemStackLocation<Entity> {
    /**
     * The entity owner of this inventory slot
     *
     * @since 1.0.0
     */
    @NotNull final Entity entity;

    /**
     * @since 1.0.0
     * @author Gunging
     */
    @NotNull public Entity getEntity() { return entity; }

    @Override public @NotNull Entity getHolder() { return entity; }

    /**
     * What statement was used to produce this Item Stack Location
     *
     * @since 1.0.0
     */
    @NotNull final ISEEntityStatement statement;

    /**
     * @since 1.0.0
     * @author Gunging
     */
    @Override
    @NotNull public ISEEntityStatement getStatement() { return statement; }

    /**
     * @param entity The entity owner of this inventory slot
     * @param slot The statement that resulted in this slot, just to have its definition available
     *
     * @since 1.0.0
     * @author Gunging
     */
    public ISEEntityLocation(@NotNull Entity entity, @NotNull ISEEntityStatement slot) {
        this.entity = entity;
        this.statement = slot;
    }

    @Override
    public @NotNull Class<Entity> getHolderClass() { return Entity.class; }

    @Override
    public void setItemStack(@Nullable ItemStack stack) { getStatement().writeItemStack(getEntity(), stack); }

    @Override
    public @Nullable ItemStack getItemStack() { return getStatement().readItemStack(getEntity()); }

    @Override
    public boolean equals(@Nullable Object obj) {

        // It has to be an ItemStack Location
        if (!(obj instanceof ItemStackLocation<?>)) { return false; }

        // I cast... manual breathing
        ItemStackLocation<?> other = (ItemStackLocation<?>) obj;

        // Check UUID of holder
        UUID ofHolder = null;
        if (obj instanceof ISEEntityLocation) {
            ofHolder = ((ISEEntityLocation) obj).getHolder().getUUID();
        } else if (obj instanceof ISPPlayerLocation) {
            ofHolder = ((ISPPlayerLocation) obj).getHolder().getUUID();
        }

        // No holder? No service
        if (ofHolder == null) { return false; }

        // Different holders? Not equal!
        if (!ofHolder.equals(getHolder().getUUID())) { return false; }

        // Any of the slots may be elaborated? Abstractly, not equals
        if (!other.getStatement().isFundamental()) { return false; }
        if (!getStatement().isFundamental()) { return false; }

        // Finally, must match slot
        return other.getStatement().equals(getStatement());
    }

    /**
     * @since 1.0.0
     * @author Gunging
     */
    @Override  public String toString() { return "|" + getHolder().getName().getString() + "|" + getStatement(); }
}
