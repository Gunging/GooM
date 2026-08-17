package gunging.ootilities.GungingOotilitiesMod.stats.values;

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
}
