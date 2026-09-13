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
public class StatStack implements StatStacked, StatStackable, StatRestackable {

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
    @Nullable StatStacked parent;

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
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @NotNull ArrayList<StatStacked> getChildStacks() { return children; }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @Nullable StatStacked getParentStack() {return parent;}

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public void setParentStack(@Nullable StatStacked parent) {this.parent = parent;}

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @NotNull HashMap<String, StatInstance<?>> getStatTotals() {return totals;}

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @NotNull HashMap<String, StatInstance<?>> getCharacteristicTotals() {return characteristic;}

    /**
     * If making changes to this map, you MUST run {@link #doPostInherentChanges()} when
     * you are done. Otherwise, things that are listening to changes of the inherent stats
     * will break.
     *
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @NotNull HashMap<String, StatInstance<?>> getInherentStats() {return inherent;}

    /**
     * Call this when making changes to the Inherent Stats map externally,
     * as this is called automatically when doing it through {@link #setStat(StatInstance)}
     *
     * @author Gunging
     * @since 1.0.0
     */
    public void doPostInherentChanges() {
        if (postInherentChanges != null) {
            postInherentChanges.whenStatStackReloaded(this);
        }
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public boolean hasStatTotalChanges() {return knownChanges;}

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public void registerStatTotalChanges() {knownChanges = true;}

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public void setStat(@NotNull StatInstance<?> stat) {

        // Register this in the inherent stats
        inherent.put(stat.getDefinition().getDefinitionID(), stat);
        dirtyNetwork.put(stat.getDefinition().getDefinitionID(), stat);
        doPostInherentChanges();

        // Register stat changes for me and all of my parents
        if (stat.getDefinition().isCharacteristic()) {
            registerStatTotalChanges();
        } else {
            parentalChainRegisterChanges();
        }
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public <M> void setStat(@NotNull StatDefinition<M> statDefinition, @Nullable StatValue<? extends M> data) {
        if (data == null) {
            inherent.remove(statDefinition.getDefinitionID());
            dirtyNetwork.put(statDefinition.getDefinitionID(),
                    new StatInstance<M>(statDefinition, statDefinition.getDefault()));
            doPostInherentChanges();
            return;
        }
        setStat(new StatInstance<M>(statDefinition, data));
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @NotNull HashMap<String, StatInstance<?>> getRefreshedStatTotals() {
        if (hasStatTotalChanges()) {recalculateStatTotals();}
        return getStatTotals();
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @NotNull HashMap<String, StatInstance<?>> getRefreshedCharacteristicTotals() {
        if (hasStatTotalChanges()) {recalculateStatTotals();}
        return getCharacteristicTotals();
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @NotNull HashMap<String, StatInstance<?>> getDirtyInherent() {return dirtyNetwork;}

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override public void recalculateStatTotals() {
        if (preStatTotals != null) {preStatTotals.whenStatStackReloaded(this);}

        // Reset totals
        totals.clear();
        characteristic.clear();

        // Begin by including the stats of the children
        for (StatStacked child : getChildStacks()) {
            for (StatInstance<?> stat : child.getRefreshedStatTotals().values()) {
                if (stat.getDefinition().isCharacteristic()) {continue;}

                // Find the totals already gathered from other children
                StatInstance<?> inTotals = totals.get(stat.getDefinition().getDefinitionID());

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
            if (!stat.getDefinition().isValid()) {continue;}

            // Find the totals already gathered from children
            StatInstance<?> inTotals = totals.get(stat.getDefinition().getDefinitionID());

            // If this stat is missing, accept it as new
            if (inTotals == null) {
                totals.put(stat.getDefinition().getDefinitionID(), stat);

                // If it already was there, merge it
            } else {inTotals.merge(stat);}

            // Include in characteristic totals as well
            if (stat.getDefinition().isCharacteristic()) {
                characteristic.put(stat.getDefinition().getDefinitionID(), stat);
            }
        }

        // Refreshed
        knownChanges = false;
        if (postStatTotals != null) {postStatTotals.whenStatStackReloaded(this);}
    }

    /**
     * Method to run before the stat stack is reloaded and changes applied.
     *
     * @since 1.0.0
     */
    @Nullable WhenRestacked preStatTotals;
    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override public @NotNull StatStack preStatTotalsReloaded(@Nullable WhenRestacked restacked) {
        this.preStatTotals = restacked;
        return this;
    }

    /**
     * Method to run when the stat stack is reloaded and changes applied.
     *
     * @since 1.0.0
     */
    @Nullable WhenRestacked postStatTotals;
    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override public @NotNull StatStack postStatTotalsReloaded(@Nullable WhenRestacked restacked) {
        this.postStatTotals = restacked;
        return this;
    }

    /**
     * Method to run after changes are made to the base stats of this stat stack
     *
     * @since 1.0.0
     */
    @Nullable WhenRestacked postInherentChanges;
    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override public @NotNull StatStack postInherentStatsChanged(@Nullable WhenRestacked restacked) {
        this.postInherentChanges = restacked;
        return this;
    }
}
