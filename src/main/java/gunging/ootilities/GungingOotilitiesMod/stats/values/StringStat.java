package gunging.ootilities.GungingOotilitiesMod.stats.values;

import gunging.ootilities.GungingOotilitiesMod.ootilityception.OotilityNumbers;
import gunging.ootilities.GungingOotilitiesMod.stats.core.StatValue;
import org.jetbrains.annotations.NotNull;

/**
 * A stat that represents a string
 *
 * @author Gunging
 * @since 1.0.0
 */
public class StringStat extends StatValue<String> {

    /**
     * @param value The value and extent to wrap herein
     *
     * @author Gunging
     * @since 1.0.0
     */
    public StringStat(@NotNull String value) {
        super(value);
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public StringStat() { super(""); }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override public StringStat clone() { return new StringStat(getValue()); }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    @NotNull public String toString() { return getValue(); }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public boolean equals(Object obj) {

        // Compare as boolean
        if (obj instanceof String) { return obj.equals(getValue()); }

        // Compare as Stat Value
        if (obj instanceof StatValue) {
            StatValue asStat = (StatValue) obj;
            if (asStat.getValue() instanceof String) {
                return asStat.getValue().equals(getValue());
            }
        }

        // Nothing
        return false;
    }
}
