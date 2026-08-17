package gunging.ootilities.GungingOotilitiesMod.stats.core;

import org.jetbrains.annotations.NotNull;

/**
 * A metric and its measure, a definition with a value.
 * <br><br>
 * In [Fire Damage +5], both the [Fire Damage] and [+5] parts.
 *
 * @author Gunging
 * @since 1.0.0
 */
public class StatInstance<Measure> implements Cloneable {

    /**
     * The meaning and metric of this stat
     *
     * @since 1.0.0
     */
    @NotNull final StatDefinition<Measure> definition;

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public @NotNull StatDefinition<Measure> getDefinition() { return definition; }

    /**
     * The value and extent of this stat
     *
     * @since 1.0.0
     */
    @NotNull StatValue<? extends Measure> value;

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public @NotNull StatValue<? extends Measure> getValue() { return value; }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public void setValue(@NotNull StatValue<? extends Measure> value) { this.value = value; }

    /**
     * @param definition The meaning and metric of this stat
     * @param value The value and extent of this stat
     *
     * @author Gunging
     * @since 1.0.0
     */
    public StatInstance(@NotNull StatDefinition<Measure> definition, @NotNull StatValue<? extends Measure> value) {
        this.definition = definition;
        this.value = value;
    }

    /**
     * @param toMerge A stat instance to be merged with this one
     *
     * @author Gunging
     * @since 1.0.0
     */
    public void merge(@NotNull StatInstance<?> toMerge) {
        merge(toMerge.getValue());
    }

    /**
     * @param toMerge A stat instance to be merged with this one
     *
     * @author Gunging
     * @since 1.0.0
     */
    public void merge(@NotNull StatValue<?> toMerge) {
        if (!getDefinition().accepts(toMerge)) { return; }
        setValue(getDefinition().merge(getValue(), toMerge));
    }

    /**
     * @param definition The meaning and metric of this stat
     *
     * @author Gunging
     * @since 1.0.0
     */
    public StatInstance(@NotNull StatDefinition<Measure> definition) {
        this(definition, definition.getDefault());
    }

    /**
     * @return TRUE if this stat instance has the default value for this definition
     *
     * @author Gunging
     * @since 1.0.0
     */
    public boolean isDefault() { return getDefinition().isDefault(getValue()); }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    @SuppressWarnings("MethodDoesntCallSuperMethod")
    public StatInstance<Measure> clone() {
        return new StatInstance<>(getDefinition(), getValue().clone());
    }
}
