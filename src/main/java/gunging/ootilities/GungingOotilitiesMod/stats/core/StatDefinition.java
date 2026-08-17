package gunging.ootilities.GungingOotilitiesMod.stats.core;

import gunging.ootilities.GungingOotilitiesMod.ootilityception.OotilityNumbers;
import org.jetbrains.annotations.NotNull;

/**
 * The representation of something that is, without its actual value.
 * <br><br>
 * In [Fire Damage +5], the [Fire Damage] part.
 *
 * @author Gunging
 * @since 1.0.0
 */
public abstract class StatDefinition<Measure> {

    /**
     * @param definitionID The unique identifier of this stat
     * @param def The default value of this stat
     *
     * @author Gunging
     * @since 1.0.0
     */
    public StatDefinition(@NotNull String definitionID, @NotNull StatValue<? extends Measure> def) {
        assert (OotilityNumbers.isInternalStandard(definitionID)) : "Invalid stat definition ID '" + definitionID + "'";
        this.definitionID = definitionID;
        this.defaultValue = def;
    }

    /**
     * The unique identifier of this stat
     *
     * @since 1.0.0
     */
    @NotNull final String definitionID;

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public String getDefinitionID() { return definitionID; }

    /**
     * The default value of this stat
     *
     * @since 1.0.0
     */
    @NotNull final StatValue<? extends Measure> defaultValue;

    /**
     * @return The default value of this value
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public StatValue<? extends Measure> getDefault() { return defaultValue; }

    /**
     * @return If the value here is the same as the default value
     *
     * @author Gunging
     * @since 1.0.0
     */
    public boolean isDefault(@NotNull StatValue<? extends Measure> value) { return getDefault().equals(value.getValue()); }

    /**
     * @param current The current stat value in here
     * @param incoming The stat value that is about to be added to me
     *
     * @return The combination of these two current and incoming
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public StatValue<? extends Measure> merge(@NotNull StatValue<? extends Measure> current, @NotNull StatValue<?> incoming ){
        if (!accepts(incoming)) { return current; }

        // By default, no merge. It just replaces the current with the incoming.
        return (StatValue<? extends Measure>) incoming;
    }

    /**
     * @return if the provided stat data is acceptable for this definition
     *
     * @author Gunging
     * @since 1.0.0
     */
    public boolean accepts(@NotNull StatValue<?> value) {
        return getDefault().getClass().isInstance(value.getValue());
    }
}
