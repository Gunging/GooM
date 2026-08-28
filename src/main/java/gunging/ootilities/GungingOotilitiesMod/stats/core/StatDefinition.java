package gunging.ootilities.GungingOotilitiesMod.stats.core;

import gunging.ootilities.GungingOotilitiesMod.commands.friendly.FriendlyFeedbackProvider;
import gunging.ootilities.GungingOotilitiesMod.ootilityception.OotilityNumbers;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
    @NotNull public StatValue<? extends Measure> merge(@NotNull StatValue<? extends Measure> current, @NotNull StatValue<?> incoming) {
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
    public boolean accepts(@NotNull StatValue<?> value) { return getDefault().getClass().isInstance(value.getValue()); }

    /**
     * @param current The current value of this stat. Note that it may just not exist yet.
     * @param operation An operation specified by the user
     * @param ffp Feedback provider in regard to the operation being parsed
     *
     * @return The result of applying this operation. Returns <code>null</code> on <b>FAILURE</b>
     *
     * @author Gunging
     * @since 1.0.0
     */
    @Nullable public abstract StatValue<? extends Measure> operation(@Nullable StatValue<? extends Measure> current, @Nullable String operation, @Nullable FriendlyFeedbackProvider ffp);

    /**
     * @return If this definition only exists in the server.
     *         The alternative syncs the value from server to
     *         client when changes are recalculated.
     *
     * @author Gunging
     * @since 1.0.0
     */
    public boolean isServerSided() { return true; }

    /**
     * @return If this stat definition is good to be used.
     *
     * @author Gunging
     * @since 1.0.0
     */
    public boolean isValid() { return true; }

    /**
     * @return If this stat must not be collected by parents
     *
     * @author Gunging
     * @since 1.0.0
     */
    public boolean isCharacteristic() { return false; }

    /**
     * @param current The stat value to serialize
     * @return The representation of this as a string
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public abstract String whenSerialized(@NotNull StatValue<? extends Measure> current);

    /**
     * @param serialized The serialized value for this stat
     * @return The value, rebuilt from this string
     *
     * @author Gunging
     * @since 1.0.0
     */
    @Nullable public abstract StatValue<? extends Measure> whenDeserialized(@NotNull String serialized, @Nullable FriendlyFeedbackProvider ffp);
}
