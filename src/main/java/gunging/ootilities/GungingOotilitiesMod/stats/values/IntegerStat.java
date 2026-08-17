package gunging.ootilities.GungingOotilitiesMod.stats.values;

import gunging.ootilities.GungingOotilitiesMod.stats.core.StatValue;
import org.jetbrains.annotations.NotNull;

/**
 * A stat that represents an integer number
 *
 * @author Gunging
 * @since 1.0.0
 */
public class IntegerStat extends StatValue<Integer> {

    /**
     * @param value The value and extent to wrap herein
     *
     * @author Gunging
     * @since 1.0.0
     */
    public IntegerStat(@NotNull Integer value) { super(value); }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public IntegerStat() { super(0); }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override public IntegerStat clone() { return new IntegerStat(getValue()); }
}
