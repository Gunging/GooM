package gunging.ootilities.GungingOotilitiesMod.stats.definitions;

import gunging.ootilities.GungingOotilitiesMod.commands.friendly.FriendlyFeedbackProvider;
import gunging.ootilities.GungingOotilitiesMod.ootilityception.OotilityNumbers;
import gunging.ootilities.GungingOotilitiesMod.stats.core.StatDefinition;
import gunging.ootilities.GungingOotilitiesMod.stats.core.StatValue;
import gunging.ootilities.GungingOotilitiesMod.stats.values.BooleanStat;
import gunging.ootilities.GungingOotilitiesMod.stats.values.StringStat;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a metric that expects a boolean
 *
 * @author Gunging
 * @since 1.0.0
 */
public class BooleanDefinition extends StatDefinition<Boolean> {

    /**
     * @param definitionID The unique identifier of this stat
     * @param def          The default value of this stat
     *
     * @author Gunging
     * @since 1.0.0
     */
    public BooleanDefinition(@NotNull String definitionID, @NotNull StatValue<? extends Boolean> def) {
        super(definitionID, def);
    }

    /**
     * @param definitionID The unique identifier of this stat
     * @param def          The default value of this stat
     *
     * @author Gunging
     * @since 1.0.0
     */
    public BooleanDefinition(@NotNull String definitionID, boolean def) { super(definitionID, new BooleanStat(def)); }

    /**
     * @param definitionID The unique identifier of this stat
     *
     * @author Gunging
     * @since 1.0.0
     */
    public BooleanDefinition(@NotNull String definitionID) { super(definitionID, new BooleanStat()); }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @Nullable StatValue<? extends Boolean> operation(@Nullable StatValue<? extends Boolean> current, @Nullable String operation, @Nullable FriendlyFeedbackProvider ffp) {

        // Immediate fail when no operation is provided
        if (operation == null) {
            FriendlyFeedbackProvider.logError(ffp, "Value $fnot$b provided, expected $etrue$b/$efalse$b. ");
            return null; }
        String noCaps = operation.toUpperCase();
        StatValue<? extends Boolean> old = current == null ? getDefault() : current;

        // Perform boolean operations
        boolean isOr = operation.startsWith("||");
        boolean isAnd = operation.startsWith("&&");
        if (isOr || isAnd) { noCaps = noCaps.substring(2); }
        Boolean isTrue = OotilityNumbers.BooleanParse(noCaps);
        if (isTrue == null) {
            FriendlyFeedbackProvider.logError(ffp, "Could $fnot$b parse $etrue$b/$efalse$b value from '$r{0}$b'. ", operation);
            return null; }

        // Compare OR for NEW
        if (isOr) { return new BooleanStat(old.getValue() || isTrue); }

        // Compare AND for NEW
        else if (isAnd) { return new BooleanStat(old.getValue() && isTrue); }

        // Simply SET
        return new BooleanStat(isTrue);
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @NotNull String whenSerialized(@NotNull StatValue<? extends Boolean> current) {
        return current.getValue().toString();
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @Nullable StatValue<? extends Boolean> whenDeserialized(@NotNull String serialized, @Nullable FriendlyFeedbackProvider ffp) {
        Boolean ret = OotilityNumbers.BooleanParse(serialized);
        if (ret == null) {
            FriendlyFeedbackProvider.logError(ffp, "Could not parse $etrue$b/$efalse$b from '$u{0}$b'. ", serialized);
            return null; }
        return new BooleanStat(ret);
    }
}
