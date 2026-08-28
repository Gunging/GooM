package gunging.ootilities.GungingOotilitiesMod.stats.core;

import gunging.ootilities.GungingOotilitiesMod.GungingOotilitiesMod;
import gunging.ootilities.GungingOotilitiesMod.commands.friendly.FriendlyFeedbackProvider;
import gunging.ootilities.GungingOotilitiesMod.ootilityception.OotilityNumbers;
import gunging.ootilities.GungingOotilitiesMod.stats.definitions.MissingDefinition;
import gunging.ootilities.GungingOotilitiesMod.stats.values.StringStat;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
    public void merge(@NotNull StatInstance<?> toMerge) { merge(toMerge.getValue()); }

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
    public StatInstance<Measure> clone() { return new StatInstance<>(getDefinition(), getValue().clone()); }

    /**
     * @return The representation of the value as a string
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public String serializeValue() { return getDefinition().whenSerialized(getValue()); }

    /**
     * @return The representation of this stat instance as a string
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public String serializeFull() {
        return serializeFull(this);
    }

    /**
     * @param iterator String builder where to append this stat instance.
     *                 <b>No separators will be appended.</b>
     *
     * @author Gunging
     * @since 1.0.0
     */
    public void serializeFull(@NotNull StringBuilder iterator) {
        serializeFull(this, iterator);
    }

    /**
     * @return The representation of this stat instance as a string
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public static String serializeFull(@NotNull StatInstance<?> inst) {
        return inst.getDefinition().getDefinitionID() + "=" + inst.serializeValue();
    }

    /**
     * @param iterator String builder where to append this stat instance.
     *                 <b>No separators will be appended.</b>
     *
     * @author Gunging
     * @since 1.0.0
     */
    public static void serializeFull(@NotNull StatInstance<?> inst, @NotNull StringBuilder iterator) {
        iterator.append(inst.getDefinition().getDefinitionID()).append('=').append(inst.serializeValue());
    }

    /**
     * Takes in a string in the format "STAT_ID=VALUE" and looks for
     * it in the stat registry and parses its value. It will return
     * null when the format is not correct, or when the value could
     * not be parsed by the stat definition. However, if the stat
     * definition is not registered, this will return a non-null
     * INVALID STAT DEFINITION object to avoid information loss.
     *
     * @param serialized The string to read, expectedly the result of {@link #serializeFull()}
     * @return A stat instance read from this string.
     *
     * @author Gunging
     * @since 1.0.0
     */
    @Nullable public static StatInstance<?> deserializeFull(@NotNull String serialized, @Nullable FriendlyFeedbackProvider ffp) {

        // Extract definition
        String definition = OotilityNumbers.extractUntil(serialized, "=", false);
        if (definition == null) {
            FriendlyFeedbackProvider.logError(ffp, "Could not parse $eSTAT=VALUE$b format from '$u{0}$b'. ", serialized);
            return null; }
        if (!OotilityNumbers.isInternalStandard(definition)) {
            FriendlyFeedbackProvider.logError(ffp, "Invalid stat name '$f{1}$b' from '$u{0}$b'. ", serialized);
            return null; }

        // Extract value
        String value = OotilityNumbers.extractAfter(serialized, "=", true);
        if (value.isEmpty()) {
            FriendlyFeedbackProvider.logError(ffp, "No value provided for $eSTAT=VALUE$b format in '$u{0}$b'. ", serialized);
            return null; }

        // Seek definition
        StatDefinition stat = GungingOotilitiesMod.getInstance().getStats().getStatDefinition(definition);
        if (stat == null) {

            /*
             * INVALID STAT DEFINITION
             */
            FriendlyFeedbackProvider.logError(ffp, "Unknown stat '$r{0}$b' in '$u{1}$b'. ", definition, serialized);
            return new StatInstance<>(new MissingDefinition(definition), new StringStat(value));
        }

        // Parse value
        StatValue parsed = stat.whenDeserialized(value, ffp);
        if (parsed == null) { return null; }
        return new StatInstance<>(stat, parsed);
    }
}
