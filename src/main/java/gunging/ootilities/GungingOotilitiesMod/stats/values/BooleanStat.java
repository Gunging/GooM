package gunging.ootilities.GungingOotilitiesMod.stats.values;

import gunging.ootilities.GungingOotilitiesMod.stats.core.StatValue;
import org.jetbrains.annotations.NotNull;

/**
 * A stat that represents a boolean
 *
 * @author Gunging
 * @since 1.0.0
 */
public class BooleanStat extends StatValue<Boolean> {

    /**
     * @param value The value and extent to wrap herein
     *
     * @author Gunging
     * @since 1.0.0
     */
    public BooleanStat(@NotNull Boolean value) {
        super(value);
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public BooleanStat() { super(false); }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override public BooleanStat clone() { return new BooleanStat(getValue()); }
}
