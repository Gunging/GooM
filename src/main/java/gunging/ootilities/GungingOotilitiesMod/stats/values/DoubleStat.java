package gunging.ootilities.GungingOotilitiesMod.stats.values;

import gunging.ootilities.GungingOotilitiesMod.ootilityception.OotilityNumbers;
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
        if (obj instanceof Number) { return OotilityNumbers.approximatelyPercent(getValue(), (double) (Number) obj, 0.02D); }

        // Compare as Stat Value
        if (obj instanceof StatValue) {
            StatValue asStat = (StatValue) obj;
            if (asStat.getValue() instanceof Number) {
                return OotilityNumbers.approximatelyPercent(getValue(), (double) (Number) asStat.getValue(), 0.02D);
            }
        }

        // Nothing
        return false;
    }
}
