package gunging.ootilities.GungingOotilitiesMod.stats.values;

import gunging.ootilities.GungingOotilitiesMod.stats.core.StatValue;
import org.jetbrains.annotations.NotNull;

/**
 * A stat that represents a double-precision number
 *
 * @author Gunging
 * @since 1.0.0
 */
public class DoubleStat extends StatValue<Double> {

    /**
     * @param value The value and extent to wrap herein
     *
     * @author Gunging
     * @since 1.0.0
     */
    public DoubleStat(@NotNull Double value) { super(value); }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public DoubleStat() { super(0D); }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override public DoubleStat clone() { return new DoubleStat(getValue()); }
}
