package gunging.ootilities.GungingOotilitiesMod.exploring.players.specalization;

import gunging.ootilities.GungingOotilitiesMod.exploring.ItemStackExplorer;
import gunging.ootilities.GungingOotilitiesMod.exploring.players.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

/**
 * Targets specific slots of the crafting grid.
 *
 * @author Gunging
 * @since 1.0.0
 */
public class ISPSCrafting extends ISPIndexedStatement {

    /**
     * Creates an "ANY" statement for all slots of the crafting grid
     *
     * @since 1.0.0
     * @author Gunging
     */
    public ISPSCrafting() { super(); }

    /**
     * @param slot The slot of the crafting grid in question
     *
     * @since 1.0.0
     * @author Gunging
     */
    public ISPSCrafting(int slot) { super(slot); }

    /**
     * @param minSlot The first inventory slot of the range [inclusive]
     * @param maxSlot The last inventory slot of the range [inclusive]
     *
     * @since 1.0.0
     * @author Gunging
     */
    public ISPSCrafting(int minSlot, int maxSlot) { super(minSlot, maxSlot); }

    @Override public ISPIndexedStatement of(int minSlot, int maxSlot) {return new ISPSCrafting(minSlot, maxSlot); }

    @Override
    public @NotNull ArrayList<ItemStackExplorer<ISPPlayerElaborator, Player>> whenElaborated(@NotNull ISPPlayerElaborator elaborator) {
        ArrayList<ItemStackExplorer<ISPPlayerElaborator, Player>> ret = new ArrayList<>();

        // If there is an upper bound, elaborate
        if (getNumericSlot() < getUpperNumericBound()) {

            // Darn modded minecraft! I cant even assume the inventory size (plugin developer moment)
            int maxInventorySize = 4;

            // I mean, just add every one of those slots inclusive of the upper and lower bound
            for (int i = getNumericSlot(); i <= getUpperNumericBound(); i++) {

                // Cancel if the max inventory size was reached
                if (i >= maxInventorySize) { break; }

                // Yes include this slot it is a nice beautiful CLEAN slot
                ret.add(new ISPPlayerExplorer(ISPExplorerStatements.CRAFTING.of(i)));
            }

        // If they are the same (or the upper bound is somehow less which is a massive skill issue) we are done
        } else { ret.add(new ISPPlayerExplorer(ISPExplorerStatements.CRAFTING.of(getNumericSlot()))); }

        return ret;
    }

    @Override
    public @Nullable ISPPlayerLocation whenRealized(@NotNull ISPPlayerElaborator elaborator) {
        return new ISPPlayerLocation(elaborator.getPlayer(), this);
    }

    @Override
    public @Nullable ItemStack readItemStack(@NotNull Player target) {
        if (getNumericSlot() < InventoryMenu.CRAFT_SLOT_START || getNumericSlot() >= InventoryMenu.CRAFT_SLOT_END) { return null; }

        return target.inventoryMenu.getCraftSlots().getItem(getNumericSlot());
    }

    @Override
    public void writeItemStack(@NotNull Player target, @Nullable ItemStack item) {
        if (getNumericSlot() < InventoryMenu.CRAFT_SLOT_START || getNumericSlot() >= InventoryMenu.CRAFT_SLOT_END) { return; }

        target.inventoryMenu.getCraftSlots().setItem(getNumericSlot(), item == null ? ItemStack.EMPTY : item);
    }
}
