package gunging.ootilities.GungingOotilitiesMod.exploring.players;

import gunging.ootilities.GungingOotilitiesMod.exploring.ItemStackLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
    @NotNull final ISPPlayerStatement specialization;

    /**
     * @since 1.0.0
     * @author Gunging
     */
    @NotNull public ISPPlayerStatement getSpecialization() { return specialization; }

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
        this.specialization = slot;
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
        this.specialization = slot;
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
        this.specialization = ISPExplorerStatements.STANDARD;
        this.index = index;
    }

    @Override
    public @NotNull Player getHolder() { return getPlayer(); }

    @Override
    public void setItemStack(@Nullable ItemStack stack) { getSpecialization().writeItemStack(getPlayer(), stack); }

    @Override
    public @Nullable ItemStack getItemStack() { return getSpecialization().readItemStack(getPlayer()); }
}
