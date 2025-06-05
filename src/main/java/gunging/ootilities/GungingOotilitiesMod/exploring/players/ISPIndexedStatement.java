package gunging.ootilities.GungingOotilitiesMod.exploring.players;

/**
 * Deals with numbered inventory slots in a player's inventory
 *
 * @since 1.0.0
 * @author Gunging
 */
public abstract class ISPIndexedStatement extends ISPPlayerStatement {

    /**
     * The numeric slot index for the inventory GUI [inclusive]
     *
     * @since 1.0.0
     */
    final int numericSlot;

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public int getNumericSlot() { return numericSlot; }

    /**
     * In case of a range, the maximum slot spanned by the
     * range of targeted slots [inclusive].
     *
     * @since 1.0.0
     */
    final int upperNumericBound;

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public int getUpperNumericBound() { return upperNumericBound; }

    /**
     * Creates an Indexed Statement "ANY" that represents
     * all the slots of this indexed inventory.
     *
     * @since 1.0.0
     * @author Gunging
     */
    public ISPIndexedStatement() {
        numericSlot = -1;
        upperNumericBound = -1;
    }

    /**
     * @param slot The slot of this inventory in question
     *
     * @since 1.0.0
     * @author Gunging
     */
    public ISPIndexedStatement(int slot) {
        numericSlot = slot;
        upperNumericBound = -1;
    }

    /**
     * @param minSlot The first inventory slot of the range [inclusive]
     * @param maxSlot The last inventory slot of the range [inclusive]
     *
     * @since 1.0.0
     * @author Gunging
     */
    public ISPIndexedStatement(int minSlot, int maxSlot) {
        numericSlot = minSlot;
        upperNumericBound = maxSlot;
    }

    /**
     * @return A clone of this indexed statement
     */
    @SuppressWarnings("MethodDoesntCallSuperMethod")
    @Override public ISPIndexedStatement clone() { return of(numericSlot, upperNumericBound); }

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
    public abstract ISPIndexedStatement of(int minSlot, int maxSlot);
}
