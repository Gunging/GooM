package gunging.ootilities.GungingOotilitiesMod.stats.definitions;

import gunging.ootilities.GungingOotilitiesMod.commands.friendly.FriendlyFeedbackProvider;
import gunging.ootilities.GungingOotilitiesMod.ootilityception.OotilityNumbers;
import gunging.ootilities.GungingOotilitiesMod.ootilityception.PlusMinusPercent;
import gunging.ootilities.GungingOotilitiesMod.stats.core.StatDefinition;
import gunging.ootilities.GungingOotilitiesMod.stats.core.StatValue;
import gunging.ootilities.GungingOotilitiesMod.stats.values.IntegerStat;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

/**
 * Represents a metric that expects an integer number
 *
 * @author Gunging
 * @since 1.0.0
 */
public class IntegerDefinition extends StatDefinition<Integer> {

    /**
     * @param definitionID The unique identifier of this stat
     * @param def          The default value of this stat
     *
     * @author Gunging
     * @since 1.0.0
     */
    public IntegerDefinition(@NotNull String definitionID, @NotNull StatValue<? extends Integer> def) {
        super(definitionID, def);
        withDisplayFeature(StatDefinition.DISPLAY_FEATURE_FORMAT, "#symbol-color##symbol#<#9f9f9f> #name-color##name#:<#9f9f9f> #value-color-neutral##plus##value#");
    }

    /**
     * @param definitionID The unique identifier of this stat
     * @param def          The default value of this stat
     *
     * @author Gunging
     * @since 1.0.0
     */
    public IntegerDefinition(@NotNull String definitionID, int def) { this(definitionID, new IntegerStat(def)); }

    /**
     * @param definitionID The unique identifier of this stat
     *
     * @author Gunging
     * @since 1.0.0
     */
    public IntegerDefinition(@NotNull String definitionID) { this(definitionID, new IntegerStat()); }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @NotNull StatValue<? extends Integer> merge(@NotNull StatValue<? extends Integer> current, @NotNull StatValue<?> incoming) {
        if (!accepts(incoming)) { return current; }

        // Double addition, then round
        Number inc = (Number) incoming.getValue();
        return new IntegerStat(OotilityNumbers.round(current.getValue() + (double) inc));
    }

    /**
     * Accepts any number
     *
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public boolean accepts(@NotNull StatValue<?> value) {
        return value.getValue() instanceof Number;
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @Nullable StatValue<? extends Integer> operation(@Nullable StatValue<? extends Integer> current, @Nullable String operation, @Nullable FriendlyFeedbackProvider ffp) {

        // Immediate fail when no operation is provided
        if (operation == null) {
            FriendlyFeedbackProvider.logError(ffp, "Value $fnot$b provided, expected an integer number. ");
            return null; }

        // Perform PMP Operation, it MUST parse
        PlusMinusPercent pmp = PlusMinusPercent.getFromString(operation, ffp);
        if (pmp == null) { return null; }

        // Apply to the old
        StatValue<? extends Integer> old = current == null ? getDefault() : current;
        return new IntegerStat(OotilityNumbers.round(pmp.apply(old.getValue())));
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @NotNull String whenSerialized(@NotNull StatValue<? extends Integer> current) {
        return current.getValue().toString();
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @Nullable StatValue<? extends Integer> whenDeserialized(@NotNull String serialized, @Nullable FriendlyFeedbackProvider ffp) {
        Integer ret = OotilityNumbers.IntegerParse(serialized);
        if (ret == null) {
            FriendlyFeedbackProvider.logError(ffp, "Expected an integer number instead of '$u{0}$b'. ", serialized);
            return null; }
        return new IntegerStat(ret);
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @NotNull ArrayList<String> whenDisplayed(@NotNull StatValue<? extends Integer> current) {

        // If the super determined not to display this, then no more replacing is needed
        ArrayList<String> ret = new ArrayList<>();
        if (isDefault(current)) { return ret; }
        int value = current.getValue();

        // Cook format
        String singleLine = getDisplayFeature(DISPLAY_FEATURE_FORMAT)
                .replace(StatDefinition.DISPLAY_FEATURE_PLUS_VALUE, value >= 0 ? "+" : "-")
                .replace(StatDefinition.DISPLAY_FEATURE_EXACT_VALUE, OotilityNumbers.readableRounding(value, 0));

        // Cook further
        ret.add(cookDisplayFeatures(singleLine));
        return ret;
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @NotNull IntegerDefinition withDisplayFeature(@NotNull String feature, @NotNull String value) {
        return (IntegerDefinition) super.withDisplayFeature(feature, value);
    }
}
