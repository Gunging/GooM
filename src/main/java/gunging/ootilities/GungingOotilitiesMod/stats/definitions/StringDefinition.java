package gunging.ootilities.GungingOotilitiesMod.stats.definitions;

import gunging.ootilities.GungingOotilitiesMod.stats.core.StatDefinition;
import gunging.ootilities.GungingOotilitiesMod.stats.core.StatValue;
import gunging.ootilities.GungingOotilitiesMod.stats.values.StringStat;
import org.jetbrains.annotations.NotNull;

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
}
