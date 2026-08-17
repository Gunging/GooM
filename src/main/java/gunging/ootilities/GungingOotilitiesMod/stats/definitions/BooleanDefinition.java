package gunging.ootilities.GungingOotilitiesMod.stats.definitions;

import gunging.ootilities.GungingOotilitiesMod.stats.core.StatDefinition;
import gunging.ootilities.GungingOotilitiesMod.stats.core.StatValue;
import gunging.ootilities.GungingOotilitiesMod.stats.values.BooleanStat;
import org.jetbrains.annotations.NotNull;

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
}
