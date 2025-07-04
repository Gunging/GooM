package gunging.ootilities.GungingOotilitiesMod.exploring.players;

import gunging.ootilities.GungingOotilitiesMod.exploring.ItemStackExplorer;
import gunging.ootilities.GungingOotilitiesMod.ootilityception.IntegerNumberRange;
import gunging.ootilities.GungingOotilitiesMod.ootilityception.OotilityNumbers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

/**
 * Deals with numbered inventory slots in a player's inventory
 *
 * @since 1.0.0
 * @author Gunging
 */
public abstract class ISPIndexedStatement extends ISPPlayerStatement {

    /**
     * @return The minimum slot index covered by this Indexed Statement (usually zero)
     *
     * @since 1.0.0
     */
    public abstract int getMinimumRange(@NotNull Player target);

    /**
     * @return The maximum slot index covered by this Indexed Statement
     *
     * @since 1.0.0
     */
    public abstract int getMaximumRange(@NotNull Player target);

    /**
     * The numeric slot range or index for the inventory GUI
     *
     * @since 1.0.0
     */
    @NotNull final IntegerNumberRange numericSlot;

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public IntegerNumberRange getNumericSlot() { return numericSlot; }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @NotNull ArrayList<ItemStackExplorer<ISPPlayerElaborator, Player>> whenElaborated(@NotNull ISPPlayerElaborator elaborator) {
        ArrayList<ItemStackExplorer<ISPPlayerElaborator, Player>> ret = new ArrayList<>();

        // If simple... we are kind of done but whatever
        if (getNumericSlot().isSimple()) {
            ret.add(new ISPPlayerExplorer(of(OotilityNumbers.round(getNumericSlot().getMinimumInclusive()))));

            // If there is range, elaborate
        } else {

            // Darn modded minecraft! I cant even assume the inventory size (plugin developer moment)
            int minInventorySize = getMinimumRange(elaborator.getElaborator());
            int maxInventorySize = getMaximumRange(elaborator.getElaborator());

            int min = getNumericSlot().getMinimumInclusive() == null ? minInventorySize : OotilityNumbers.round(getNumericSlot().getMinimumInclusive());
            int max = getNumericSlot().getMaximumInclusive() == null ? maxInventorySize : OotilityNumbers.round(getNumericSlot().getMaximumInclusive());

            // I mean, just add every one of those slots inclusive of the upper and lower bound
            for (int i = min; i <= max; i++) {

                // Cancel if the max inventory size was reached
                if (i >= maxInventorySize) {break;}

                // Yes include this slot it is a nice beautiful CLEAN slot
                ret.add(new ISPPlayerExplorer(of(i)));
            }
        }

        return ret;
    }

    /**
     * Creates an Indexed Statement "ANY" that represents
     * all the slots of this indexed inventory.
     *
     * @since 1.0.0
     * @author Gunging
     */
    public ISPIndexedStatement(@NotNull ResourceLocation name) {
        super(name);
        numericSlot = new IntegerNumberRange(null, null);
    }

    /**
     * @param slot The slot of this inventory in question
     *
     * @since 1.0.0
     * @author Gunging
     */
    public ISPIndexedStatement(@NotNull ResourceLocation name, int slot) {
        super(name);
        numericSlot = new IntegerNumberRange(slot, slot);
    }

    /**
     * @param qnr The slot range of this inventory in question
     *
     * @since 1.0.0
     * @author Gunging
     */
    public ISPIndexedStatement(@NotNull ResourceLocation name, @NotNull IntegerNumberRange qnr) {
        super(name);
        numericSlot = qnr;
    }

    /**
     * @param minSlot The first inventory slot of the range [inclusive]
     * @param maxSlot The last inventory slot of the range [inclusive]
     *
     * @since 1.0.0
     * @author Gunging
     */
    public ISPIndexedStatement(@NotNull ResourceLocation name, int minSlot, int maxSlot) {
        super(name);
        numericSlot = new IntegerNumberRange(minSlot, maxSlot);
    }

    /**
     * @return A clone of this indexed statement
     */
    @SuppressWarnings("MethodDoesntCallSuperMethod")
    @Override public ISPIndexedStatement clone() { return of(numericSlot); }

    /**
     * Creates an Indexed Statement "ANY" that represents
     * all the slots of this indexed inventory.
     *
     * @return Essentially a clone, but with different index
     *
     * @since 1.0.0
     * @author Gunging
     */
    public ISPIndexedStatement any() { return of(-1, -1); }

    /**
     * @param slot The slot of this inventory in question
     *
     * @return Essentially a clone, but with different index
     *
     * @since 1.0.0
     * @author Gunging
     */
    public ISPIndexedStatement of(int slot) { return of(slot, -1); }

    /**
     * @param minSlot The first inventory slot of the range [inclusive]
     * @param maxSlot The last inventory slot of the range [inclusive]
     *
     * @return Essentially a clone, but with different minimum and maximum indices
     *
     * @since 1.0.0
     * @author Gunging
     */
    public ISPIndexedStatement of(int minSlot, int maxSlot) { return of(new IntegerNumberRange(minSlot, maxSlot)); }

    /**
     * @param qnr The slot range of this inventory in question
     *
     * @return Essentially a clone, but with different minimum and maximum indices
     *
     * @since 1.0.0
     * @author Gunging
     */
    public abstract ISPIndexedStatement of(@NotNull IntegerNumberRange qnr);

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @NotNull String getOptions() { return numericSlot.toString(); }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @Nullable ISPIndexedStatement withOptions(@NotNull String options) {
        IntegerNumberRange rebuilt = IntegerNumberRange.getFromString(options);
        if (rebuilt == null) { return null; }
        return this.of(rebuilt);
    }
}
