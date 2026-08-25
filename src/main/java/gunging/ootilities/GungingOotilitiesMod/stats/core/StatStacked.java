package gunging.ootilities.GungingOotilitiesMod.stats.core;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Something with the capacity to hold stat instances.
 *
 * @author Gunging
 * @since 1.0.0
 */
public interface StatStacked {

    /**
     * The children this Stat Stack inherits from
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull ArrayList<StatStacked> getChildStacks();

    /**
     * @return If this has any children stacks
     *
     * @author Gunging
     * @since 1.0.0
     */
    default boolean hasChildren() { return !getChildStacks().isEmpty(); }

    /**
     * The parent that contains these stats
     *
     * @author Gunging
     * @since 1.0.0
     */
    @Nullable StatStacked getParentStack();

    /**
     * @return If this has a parent
     *
     * @author Gunging
     * @since 1.0.0
     */
    default boolean hasParent() { return getParentStack() != null; }

    /**
     * The root of this stack tree, the parent-most parent
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull default StatStacked getRootStack() {
        StatStacked parent = getParentStack();
        if (parent == null) { return this; }
        return parent.getRootStack();
    }

    /**
     * The stats contained herein
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull HashMap<String, StatInstance<?>> getStatTotals();

    /**
     * Consider recalculating totals if there are changes,
     * then return the latest stat totals for real.
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull HashMap<String, StatInstance<?>> getRefreshedStatTotals();

    /**
     * Call this when modifying the inherent stats of this,
     * or when modifying the stats in any of its children
     *
     * @author Gunging
     * @since 1.0.0
     */
    void registerStatTotalChanges();

    /**
     * Forces this to recalculate totals.
     *
     * @author Gunging
     * @since 1.0.0
     */
    void recalculateStatTotals();

    /**
     * True when the inherent stats of this have
     * changed, or any of its children have changed.
     *
     * @author Gunging
     * @since 1.0.0
     */
    boolean hasStatTotalChanges();

    /**
     * Register changes for me and all of my
     * parents up to the root parent themselves
     *
     * @author Gunging
     * @since 1.0.0
     */
    @SuppressWarnings("DataFlowIssue")
    default void parentalChainRegisterChanges() {

        // Register changes for me
        registerStatTotalChanges();

        // Send the changes up the parent chain
        if (hasParent()) { getParentStack().parentalChainRegisterChanges(); }
    }

    /**
     * @param newChild A new Stat Stacked instance about to be added as a child of this
     *
     * @return TRUE if cleared, FALSE if this is actually a parent of this which
     *         would result in an infinite recursive chain whenever parent or
     *         children mobility is ensued. Meaning, if this returns FALSE, you
     *         can ABSOLUTELY NOT EVER make this newChild a child of this.
     */
    @SuppressWarnings("DataFlowIssue")
    default boolean canBecomeChildren(@NotNull StatStacked newChild) {

        // This is the same? Then pls no infinite change
        if (newChild == this) { return false; }

        // Also applies for my parent, the new child must never be my parent
        if (hasParent()) { return getParentStack().canBecomeChildren(newChild); }

        // Okay then they are fine
        return true;
    }

    /**
     * The stat contained here for this stat definition
     *
     * @param statID ID of the stat definition
     *
     * @author Gunging
     * @since 1.0.0
     */
    @Contract("null->null")
    @Nullable default StatInstance<?> getStat(@Nullable String statID) { return getStatTotals().get(statID); }
    /**
     * The stat contained here for this stat definition
     *
     * @param statDef Stat definition
     *
     * @author Gunging
     * @since 1.0.0
     */
    @Contract("null->null")
    @Nullable default StatInstance<?> getStat(@Nullable StatDefinition<?> statDef) {
        if (statDef == null) { return null; }
        return getStat(statDef.getDefinitionID());
    }

    /**
     * The value of the stat here for this stat definition
     *
     * @param statDef Stat definition
     *
     * @author Gunging
     * @since 1.0.0
     */
    @Contract("null->null")
    @Nullable default StatValue<?> getValue(@Nullable StatDefinition<?> statDef) {
        if (statDef == null) { return null; }
        StatInstance<?> found = getStat(statDef.getDefinitionID());
        if (found == null) { return null; }
        return found.getValue();
    }
    /**
     * The value of the stat here for this stat definition
     * <br>If missing, the stat instance's default value will be returned
     *
     * @param statDef Stat definition
     *
     * @author Gunging
     * @since 1.0.0
     */
    @Contract("null->null")
    @Nullable default StatValue<?> getValueOrDefault(@Nullable StatDefinition<?> statDef) {
        if (statDef == null) { return null; }
        StatInstance<?> found = getStat(statDef.getDefinitionID());
        if (found == null) { return statDef.getDefault(); }
        return found.getValue();
    }
    /**
     * The value of the stat here for this stat definition
     * <br>If missing, the provided "default" value will return
     *
     * @param statDef Stat definition
     * @param def The value to return if this stat is not in this stat stack
     *
     * @author Gunging
     * @since 1.0.0
     */
    @Contract("!null,!null->!null;null,_->null")
    @Nullable default StatValue<?> getValueOrDefault(@Nullable StatDefinition<?> statDef, @Nullable StatValue<?> def) {
        if (statDef == null) { return null; }
        StatInstance<?> found = getStat(statDef.getDefinitionID());
        if (found == null) { return def; }
        return found.getValue();
    }
}
