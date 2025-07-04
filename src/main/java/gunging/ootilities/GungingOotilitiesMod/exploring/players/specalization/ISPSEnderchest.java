package gunging.ootilities.GungingOotilitiesMod.exploring.players.specalization;

import gunging.ootilities.GungingOotilitiesMod.exploring.players.*;
import gunging.ootilities.GungingOotilitiesMod.ootilityception.IntegerNumberRange;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A specific slot of the enderchest
 *
 * @author Gunging
 * @since 1.0.0
 */
public class ISPSEnderchest extends ISPIndexedStatement {

    /**
     * Creates an "ANY" statement for all slots of the crafting grid
     *
     * @since 1.0.0
     * @author Gunging
     */
    public ISPSEnderchest(@NotNull ResourceLocation name) { super(name); }

    /**
     * @param slot The slot of the crafting grid in question
     *
     * @since 1.0.0
     * @author Gunging
     */
    public ISPSEnderchest(@NotNull ResourceLocation name, int slot) { super(name, slot); }

    /**
     * @param qnr The slot range of the crafting grid in question
     *
     * @since 1.0.0
     * @author Gunging
     */
    public ISPSEnderchest(@NotNull ResourceLocation name, @NotNull IntegerNumberRange qnr) { super(name, qnr); }

    /**
     * @param minSlot The first inventory slot of the range [inclusive]
     * @param maxSlot The last inventory slot of the range [inclusive]
     *
     * @since 1.0.0
     * @author Gunging
     */
    public ISPSEnderchest(@NotNull ResourceLocation name, int minSlot, int maxSlot) { super(name, minSlot, maxSlot); }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override public ISPSEnderchest of(@NotNull IntegerNumberRange qnr) {return (ISPSEnderchest) (new ISPSEnderchest(getStatementName(), qnr)).setNetworkIndex(getNetworkIndex()); }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override public int getMinimumRange(@NotNull Player target) { return 0; }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override public int getMaximumRange(@NotNull Player target) { return target.getEnderChestInventory().getContainerSize(); }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @Nullable ISPPlayerLocation whenRealized(@NotNull ISPPlayerElaborator elaborator) {
        return new ISPPlayerLocation(elaborator.getPlayer(), this);
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @Nullable ItemStack readItemStack(@NotNull Player target) {
        if (!getNumericSlot().isSimple()) { return null; }
        int min = getNumericSlot().getMinimumInclusive();
        if (min < getMinimumRange(target) || min >= getMaximumRange(target)) { return null; }

        return target.getEnderChestInventory().getItem(min);
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public void writeItemStack(@NotNull Player target, @Nullable ItemStack item) {
        if (!getNumericSlot().isSimple()) { return; }
        int min = getNumericSlot().getMinimumInclusive();
        if (min < getMinimumRange(target) || min >= getMaximumRange(target)) { return; }

        target.getEnderChestInventory().setItem(min, item == null ? ItemStack.EMPTY : item);
    }
}
