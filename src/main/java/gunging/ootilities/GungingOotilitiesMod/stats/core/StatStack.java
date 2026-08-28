package gunging.ootilities.GungingOotilitiesMod.stats.core;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A stat stack is the culmination of a series of stat instances.
 * <br><br>
 * Consider an RPG item [Flame Axe] with +5 damage and +5 fire damage.
 * These are two stat instances, and the axe itself is the stat stack.
 *
 * @author Gunging
 * @since 1.0.0
 */
public class StatStack implements StatStacked, StatStackable {

    /**
     * The children this Stat Stack inherits from
     *
     * @since 1.0.0
     */
    @NotNull ArrayList<StatStacked> children = new ArrayList<>();

    /**
     * The parent that contains this Stat Stack
     *
     * @since 1.0.0
     */
    @Nullable StatStack parent;

    /**
     * The stats belonging to this stat stack specifically
     *
     * @since 1.0.0
     */
    @NotNull HashMap<String, StatInstance<?>> inherent = new HashMap<>();

    /**
     * The cached totals of the stats of this and its children
     *
     * @since 1.0.0
     */
    @NotNull HashMap<String, StatInstance<?>> totals = new HashMap<>();

    /**
     * The cached characteristic stats of this provider.
     * Characteristic stats are Inherent stats that do
     * not get synced with the parent, staying in this
     * Stat Stack only.
     *
     * @since 1.0.0
     */
    @NotNull HashMap<String, StatInstance<?>> characteristic = new HashMap<>();

    /**
     * Registers stat changes to inherent stats
     * that must be sent over the network.
     *
     * @since 1.0.0
     */
    @NotNull HashMap<String, StatInstance<?>> dirtyNetwork = new HashMap<>();

    /**
     * If this or its children are known to have changes
     *
     * @since 1.0.0
     */
    boolean knownChanges;

    /**
     * @since 1.0.0
     * @author Gunging
     */
    @Override public @NotNull ArrayList<StatStacked> getChildStacks() { return children; }

    /**
     * @since 1.0.0
     * @author Gunging
     */
    @Override public @Nullable StatStacked getParentStack() { return parent; }

    /**
     * @since 1.0.0
     * @author Gunging
     */
    @Override public @NotNull HashMap<String, StatInstance<?>> getStatTotals() { return totals; }

    /**
     * @since 1.0.0
     * @author Gunging
     */
    @Override public @NotNull HashMap<String, StatInstance<?>> getCharacteristicTotals() { return characteristic; }

    /**
     * @since 1.0.0
     * @author Gunging
     */
    @Override
    public @NotNull HashMap<String, StatInstance<?>> getInherentStats() { return inherent; }

    /**
     * @since 1.0.0
     * @author Gunging
     */
    @Override public boolean hasStatTotalChanges() { return knownChanges; }

    /**
     * @since 1.0.0
     * @author Gunging
     */
    @Override public void registerStatTotalChanges() { knownChanges = true; }

    /**
     * @since 1.0.0
     * @author Gunging
     */
    @Override
    public void setStat(@NotNull StatInstance<?> stat) {

        // Register this in the inherent stats
        inherent.put(stat.getDefinition().getDefinitionID(), stat);
        dirtyNetwork.put(stat.getDefinition().getDefinitionID(), stat);

        // Register stat changes for me and all of my parents
        if (stat.getDefinition().isCharacteristic()) {
            registerStatTotalChanges();
        } else {
            parentalChainRegisterChanges(); }
    }

    /**
     * @since 1.0.0
     * @author Gunging
     */
    @Override public <M> void setStat(@NotNull StatDefinition<M> statDefinition, @Nullable StatValue<? extends M> data) {
        if (data == null) {
            inherent.remove(statDefinition.getDefinitionID());
            dirtyNetwork.put(statDefinition.getDefinitionID(),
                    new StatInstance<M>(statDefinition, statDefinition.getDefault()));
            return; }
        setStat(new StatInstance<M>(statDefinition, data));
    }

    /**
     * @since 1.0.0
     * @author Gunging
     */
    @Override public @NotNull HashMap<String, StatInstance<?>> getRefreshedStatTotals() {
        if (hasStatTotalChanges()) { recalculateStatTotals(); }
        return getStatTotals();
    }

    /**
     * @since 1.0.0
     * @author Gunging
     */
    @Override public @NotNull HashMap<String, StatInstance<?>> getRefreshedCharacteristicTotals() {
        if (hasStatTotalChanges()) { recalculateStatTotals(); }
        return getCharacteristicTotals();
    }

    /**
     * @since 1.0.0
     * @author Gunging
     */
    @Override public @NotNull HashMap<String, StatInstance<?>> getDirtyInherent() { return dirtyNetwork; }

    /**
     * @since 1.0.0
     * @author Gunging
     */
    @Override public void recalculateStatTotals() {

        // Reset totals
        totals.clear();
        characteristic.clear();

        // Begin by including the stats of the children
        for (StatStacked child : getChildStacks()) {
            for (StatInstance<?> stat : child.getRefreshedStatTotals().values()) {
                if (stat.getDefinition().isCharacteristic()) { continue; }

                // Find the totals already gathered from other children
                StatInstance<?> inTotals = totals.get(stat.getDefinition());

                // If this stat is missing, accept it as new
                if (inTotals == null) {
                    totals.put(stat.getDefinition().getDefinitionID(), stat);

                // If it already was there, merge it
                } else {
                    inTotals.merge(stat);
                }
            }
        }

        // Then merge inherent
        for (StatInstance<?> stat : getInherentStats().values()) {
            if (!stat.getDefinition().isValid()) { continue; }

            // Find the totals already gathered from children
            StatInstance<?> inTotals = totals.get(stat.getDefinition());

            // If this stat is missing, accept it as new
            if (inTotals == null) {
                totals.put(stat.getDefinition().getDefinitionID(), stat);

            // If it already was there, merge it
            } else { inTotals.merge(stat); }

            // Include in characteristic totals as well
            if (stat.getDefinition().isCharacteristic()) {
                characteristic.put(stat.getDefinition().getDefinitionID(), stat); }
        }

        // Refreshed
        knownChanges = false;
    }
}
