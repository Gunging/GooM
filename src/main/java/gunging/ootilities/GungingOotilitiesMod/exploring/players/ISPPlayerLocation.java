package gunging.ootilities.GungingOotilitiesMod.exploring.players;

import gunging.ootilities.GungingOotilitiesMod.exploring.ItemStackLocation;
import gunging.ootilities.GungingOotilitiesMod.exploring.entities.ISEEntityLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * An actual slot in the inventory of a player,
 * ready to read or modify that item yes.
 *
 * @since 1.0.0
 * @author Gunging
 */
public class ISPPlayerLocation implements ItemStackLocation<Player> {

    /**
     * The player owner of this inventory slot
     *
     * @since 1.0.0
     */
    @NotNull final Player player;

    /**
     * @since 1.0.0
     * @author Gunging
     */
    @NotNull public Player getPlayer() { return player; }

    /**
     * The kind of inventory slot being targeted
     *
     * @since 1.0.0
     */
    @NotNull final ISPPlayerStatement statement;

    /**
     * @since 1.0.0
     * @author Gunging
     */
    @Override
    @NotNull public ISPPlayerStatement getStatement() { return statement; }

    /**
     * The index of the slot of the inventory
     *
     * @since 1.0.0
     */
    final int index;

    /**
     * @since 1.0.0
     * @author Gunging
     */
    public int getIndex() { return index; }

    /**
     * A slot pertaining to a special inventory slot family of this player
     *
     * @param player The player owner of this inventory slot
     * @param slot The kind of inventory slot being targeted
     * @param index The index of the slot of the inventory
     *
     * @since 1.0.0
     * @author Gunging
     */
    public ISPPlayerLocation(@NotNull Player player, @NotNull ISPPlayerStatement slot, int index) {
        this.player = player;
        this.statement = slot;
        this.index = index;
    }

    /**
     * A special slot of this player
     *
     * @param player The player owner of this inventory slot
     * @param slot The special inventory slot being targeted
     *
     * @since 1.0.0
     * @author Gunging
     */
    public ISPPlayerLocation(@NotNull Player player, @NotNull ISPPlayerStatement slot) {
        this.player = player;
        this.statement = slot;
        this.index = -1;
    }

    /**
     * A slot pertaining to the normal inventory of this player
     *
     * @param player The player owner of this inventory slot
     * @param index The index of the slot of the inventory
     *
     * @since 1.0.0
     * @author Gunging
     */
    public ISPPlayerLocation(@NotNull Player player, int index) {
        this.player = player;
        this.statement = ISPExplorerStatements.STANDARD;
        this.index = index;
    }

    @Override
    public @NotNull Player getHolder() { return getPlayer(); }

    @Override
    public void setItemStack(@Nullable ItemStack stack) { getStatement().writeItemStack(getPlayer(), stack); }

    @Override
    public @Nullable ItemStack getItemStack() { return getStatement().readItemStack(getPlayer()); }

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
}
