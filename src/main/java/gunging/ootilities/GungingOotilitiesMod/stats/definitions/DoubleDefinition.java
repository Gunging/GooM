package gunging.ootilities.GungingOotilitiesMod.stats.definitions;

import gunging.ootilities.GungingOotilitiesMod.stats.core.StatDefinition;
import gunging.ootilities.GungingOotilitiesMod.stats.core.StatValue;
import gunging.ootilities.GungingOotilitiesMod.stats.values.DoubleStat;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a metric that expects a double-precision number
 *
 * @author Gunging
 * @since 1.0.0
 */
public class DoubleDefinition extends StatDefinition<Double> {

    /**
     * @param definitionID The unique identifier of this stat
     * @param def          The default value of this stat
     *
     * @author Gunging
     * @since 1.0.0
     */
    public DoubleDefinition(@NotNull String definitionID, @NotNull StatValue<? extends Double> def) {
        super(definitionID, def);
    }

    /**
     * @param definitionID The unique identifier of this stat
     * @param def          The default value of this stat
     *
     * @author Gunging
     * @since 1.0.0
     */
    public DoubleDefinition(@NotNull String definitionID, double def) { super(definitionID, new DoubleStat(def)); }


    /**
     * @param definitionID The unique identifier of this stat
     *
     * @author Gunging
     * @since 1.0.0
     */
    public DoubleDefinition(@NotNull String definitionID) { super(definitionID, new DoubleStat()); }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @NotNull StatValue<? extends Double> merge(@NotNull StatValue<? extends Double> current, @NotNull StatValue<?> incoming) {
        if (!accepts(incoming)) { return current; }

        // Double addition
        Number inc = (Number) incoming.getValue();
        return new DoubleStat(current.getValue() + (double) inc);
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
}
