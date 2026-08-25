package gunging.ootilities.GungingOotilitiesMod.stats.definitions;

import gunging.ootilities.GungingOotilitiesMod.commands.friendly.FriendlyFeedbackProvider;
import gunging.ootilities.GungingOotilitiesMod.ootilityception.OotilityNumbers;
import gunging.ootilities.GungingOotilitiesMod.ootilityception.PlusMinusPercent;
import gunging.ootilities.GungingOotilitiesMod.stats.core.StatDefinition;
import gunging.ootilities.GungingOotilitiesMod.stats.core.StatValue;
import gunging.ootilities.GungingOotilitiesMod.stats.values.StringStat;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a metric that expects a line of text
 *
 * @author Gunging
 * @since 1.0.0
 */
public class StringDefinition extends StatDefinition<String> {

    /**
     * @param definitionID The unique identifier of this stat
     * @param def          The default value of this stat
     *
     * @author Gunging
     * @since 1.0.0
     */
    public StringDefinition(@NotNull String definitionID, @NotNull StatValue<? extends String> def) {
        super(definitionID, def);
    }

    /**
     * @param definitionID The unique identifier of this stat
     * @param def          The default value of this stat
     *
     * @author Gunging
     * @since 1.0.0
     */
    public StringDefinition(@NotNull String definitionID, @NotNull String def) { super(definitionID, new StringStat(def)); }

    /**
     * @param definitionID The unique identifier of this stat
     *
     * @author Gunging
     * @since 1.0.0
     */
    public StringDefinition(@NotNull String definitionID) { super(definitionID, new StringStat()); }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @Nullable StatValue<? extends String> operation(@Nullable StatValue<? extends String> current, @Nullable String operation, @Nullable FriendlyFeedbackProvider ffp) {

        // Immediate fail when no operation is provided
        if (operation == null) { FriendlyFeedbackProvider.logError(ffp, "Value $fnot$b provided. "); return null; }

        // Only SET is supported
        return new StringStat(operation);
    }
}
