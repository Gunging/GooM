package gunging.ootilities.GungingOotilitiesMod.stats.core;

import gunging.ootilities.GungingOotilitiesMod.commands.friendly.FriendlyFeedbackProvider;
import gunging.ootilities.GungingOotilitiesMod.ootilityception.OotilityNumbers;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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

        // Default stat stuff
        withDisplayFeature(DISPLAY_FEATURE_FORMAT, "#symbol-color##symbol#<#9f9f9f> #name-color##name##name-color#:<#9f9f9f> #value-color-neutral##value#");
        withDisplayFeature(DISPLAY_FEATURE_SYMBOL_COLOR, "<#" + Integer.toHexString((new Random(definitionID.hashCode() - 6)).nextInt()) + ">");
        withDisplayFeature(DISPLAY_FEATURE_SYMBOL, "■");
        withDisplayFeature(DISPLAY_FEATURE_NAME_COLOR, "");
        withDisplayFeature(DISPLAY_FEATURE_NAME, definitionID.replace("_", " "));
        withDisplayFeature(DISPLAY_FEATURE_VALUE_COLOR_NEUTRAL, DISPLAY_COLOR_NEUTRAL);
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
     * @return If this stat should be hidden from edition
     *         interfaces and commands.
     *
     * @author Gunging
     * @since 1.0.0
     */
    public boolean isInternal() { return false; }

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

    //region Default Display
    /**
     * The format by which other display features will display
     *
     * @since 1.0.0
     */
    public static final String DISPLAY_FEATURE_FORMAT = "#format#";
    /**
     * When specifying a display format, the placeholder for the symbol icon
     *
     * @since 1.0.0
     */
    public static final String DISPLAY_FEATURE_SYMBOL = "#symbol#";
    /**
     * When specifying a display format, the placeholder for the color of the symbol
     *
     * @since 1.0.0
     */
    public static final String DISPLAY_FEATURE_SYMBOL_COLOR = "#symbol-color#";
    /**
     * When specifying a display format, the placeholder for the display name of this stat
     *
     * @since 1.0.0
     */
    public static final String DISPLAY_FEATURE_NAME = "#name#";
    /**
     * When specifying a display format, the placeholder for the color of the display name of this stat
     *
     * @since 1.0.0
     */
    public static final String DISPLAY_FEATURE_NAME_COLOR = "#name-color#";
    /**
     * When specifying a display format, the placeholder for the sign of numeric value
     *
     * @since 1.0.0
     */
    public static final String DISPLAY_FEATURE_PLUS_VALUE = "#plus#";
    /**
     * When specifying a display format, the placeholder for the exact value of this stat (numeric)
     *
     * @since 1.0.0
     */
    public static final String DISPLAY_FEATURE_EXACT_VALUE = "#value#";
    /**
     * When specifying a display format, the placeholder for the approximate value of this stat (numeric)
     *
     * @since 1.0.0
     */
    public static final String DISPLAY_FEATURE_APPROXIMATE_VALUE = "#approximate#";
    /**
     * When specifying a display format, the placeholder for a vague estimate of the value of this stat (numeric)
     *
     * @since 1.0.0
     */
    public static final String DISPLAY_FEATURE_COARSE_VALUE = "#coarse#";
    /**
     * When specifying a display format, the placeholder for the qualitative assessment of this stat (not numeric)
     *
     * @since 1.0.0
     */
    public static final String DISPLAY_FEATURE_QUALITATIVE_VALUE = "#qualitative#";
    /**
     * When specifying a display format, the color of the value when neutral
     *
     * @since 1.0.0
     */
    public static final String DISPLAY_FEATURE_VALUE_COLOR_NEUTRAL = "#value-color-neutral#";
    /**
     * When specifying a display format, the color of the value accounting for desirability
     *
     * @since 1.0.0
     */
    public static final String DISPLAY_FEATURE_VALUE_COLOR_DESIRABLE = "#value-color-desirable#";
    /**
     * The default color to be used for the value of this stat
     *
     * @since 1.0.0
     */
    public static String DISPLAY_COLOR_NEUTRAL = "<#fefefe>";
    /**
     * The default color to be used for the value of this stat when desirable
     *
     * @since 1.0.0
     */
    public static String DISPLAY_COLOR_DESIRABLE = "<#9effa3>";
    /**
     * The default color to be used for the value of this stat when undesirable
     *
     * @since 1.0.0
     */
    public static String DISPLAY_COLOR_UNDESIRABLE = "<#ff9e9e>";

    /**
     * Prepares a list of tooltip lines to show the value of this stat.
     *
     * @param current The value to be displayed
     * @param asTotal If this must be shown as the totals rather than a contribution.
     *                Consider "+10 attack damage" vs the total "10 attack damage."
     *
     * @since 1.0.0
     */
    @NotNull public ArrayList<String> whenDisplayed(@NotNull StatValue<? extends Measure> current, boolean asTotal) {

        // Default stats are not written
        if (isDefault(current)) { return new ArrayList<>(); }

        // Otherwise, write out per the format
        String format = getDisplayFeature(DISPLAY_FEATURE_FORMAT);
        if (format.isEmpty()) { return new ArrayList<>(); }

        // Cook and finish
        ArrayList<String> ret = new ArrayList<>();
        ret.add(cookDisplayFeatures(format));
        return ret;
    }

    /**
     * Attempts to replace all the display placeholders this stat knows
     *
     * @param format The format to mass-replace according to the display features
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public String cookDisplayFeatures(@NotNull String format) {
        String ret = format;

        // Replace every display feature
        for (Map.Entry<String, String> pair : displayFeatures.entrySet()) {
            ret = ret.replace(pair.getKey(), pair.getValue());
        }

        return ret;
    }

    /**
     * The format placeholders when displaying this stat
     *
     * @since 1.0.0
     */
    @NotNull HashMap<String, String> displayFeatures = new HashMap<>();
    /**
     * @param feature The name of the feature
     * @param value The default value of the feature
     *
     * @return This same object. Builder pattern.
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public StatDefinition<Measure> withDisplayFeature(@NotNull String feature, @NotNull String value) { displayFeatures.put(feature, value); return this; }
    /**
     * The format placeholders when displaying this stat
     *
     * @since 1.0.0
     */
    @NotNull public HashMap<String, String> getDisplayFeatures() { return displayFeatures; }
    /**
     * The format placeholders when displaying this stat
     *
     * @since 1.0.0
     */
    @NotNull public String getDisplayFeature(@NotNull String feature) {
        String ret = getDisplayFeatures().get(feature);
        if (ret == null) { return ""; }
        return ret;
    }
    //endregion
}
