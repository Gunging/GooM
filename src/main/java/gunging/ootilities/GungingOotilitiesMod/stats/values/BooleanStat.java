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

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    @NotNull public String toString() { return String.valueOf(getValue()); }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public boolean equals(Object obj) {

        // Compare as boolean
        if (obj instanceof Boolean) { return obj.equals(getValue()); }

        // Compare as Stat Value
        if (obj instanceof StatValue) {
            StatValue asStat = (StatValue) obj;
            if (asStat.getValue() instanceof Boolean) {
                return asStat.getValue().equals(getValue());
            }
        }

        // Nothing
        return false;
    }
}
