package gunging.ootilities.GungingOotilitiesMod.exploring.players.specalization;

import gunging.ootilities.GungingOotilitiesMod.exploring.ItemStackExplorer;
import gunging.ootilities.GungingOotilitiesMod.exploring.players.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

/**
 * It is actually not a special slot, just
 * a simple numeric (or number range).
 *
 * @since 1.0.0
 * @author Gunging
 */
public class ISPSStandard extends ISPIndexedStatement {

    /**
     * Creates an "ANY" statement for all slots of the player inventory
     *
     * @since 1.0.0
     * @author Gunging
     */
    public ISPSStandard() { super(); }

    /**
     * @param slot The slot of the player inventory in question
     *
     * @since 1.0.0
     * @author Gunging
     */
    public ISPSStandard(int slot) { super(slot); }

    /**
     * @param minSlot The first inventory slot of the range [inclusive]
     * @param maxSlot The last inventory slot of the range [inclusive]
     *
     * @since 1.0.0
     * @author Gunging
     */
    public ISPSStandard(int minSlot, int maxSlot) { super(minSlot, maxSlot); }


    @Override public ISPIndexedStatement of(int minSlot, int maxSlot) {return new ISPSStandard(minSlot, maxSlot); }

    @Override
    public @NotNull ArrayList<ItemStackExplorer<ISPPlayerElaborator, Player>> whenElaborated(@NotNull ISPPlayerElaborator elaborator) {
        ArrayList<ItemStackExplorer<ISPPlayerElaborator, Player>> ret = new ArrayList<>();

        // If there is an upper bound, elaborate
        if (getNumericSlot() < getUpperNumericBound()) {

            // Darn modded minecraft! I cant even assume the inventory size (plugin developer moment)
            int maxInventorySize = elaborator.getPlayer().getInventory().items.size();

            // I mean, just add every one of those slots inclusive of the upper and lower bound
            for (int i = getNumericSlot(); i <= getUpperNumericBound(); i++) {

                // Cancel if the max inventory size was reached
                if (i >= maxInventorySize) { break; }

                // Yes include this slot it is a nice beautiful CLEAN slot
                ret.add(new ISPPlayerExplorer(ISPExplorerStatements.STANDARD.of(i)));
            }

        // If they are the same (or the upper bound is somehow less which is a massive skill issue) we are done
        } else { ret.add(new ISPPlayerExplorer(ISPExplorerStatements.STANDARD.of(getNumericSlot()))); }

        return ret;
    }

    @Override
    public @Nullable ISPPlayerLocation whenRealized(@NotNull ISPPlayerElaborator elaborator) {
        return new ISPPlayerLocation(elaborator.getPlayer(), this);
    }

    @Override
    public @Nullable ItemStack readItemStack(@NotNull Player target) {
        if (getNumericSlot() < 0 || getNumericSlot() >= target.getInventory().items.size()) { return null; }

        return target.getInventory().items.get(getNumericSlot());
    }

    @Override
    public void writeItemStack(@NotNull Player target, @Nullable ItemStack item) {
        if (getNumericSlot() < 0 || getNumericSlot() >= target.getInventory().items.size()) { return; }

        target.getInventory().items.set(getNumericSlot(), item == null ? ItemStack.EMPTY : item);
    }
}
