package gunging.ootilities.GungingOotilitiesMod.stats.values;

import gunging.ootilities.GungingOotilitiesMod.stats.core.StatValue;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * A stat that represents a list of strings
 *
 * @author Gunging
 * @since 1.0.0
 */
public class StringListStat extends StatValue<List<String>> {

    /**
     * @param value The value and extent to wrap herein
     *
     * @author Gunging
     * @since 1.0.0
     */
    public StringListStat(@NotNull List<String> value) {
        super(value);
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public StringListStat() { super(new ArrayList<>()); }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override public StringListStat clone() { return new StringListStat(new ArrayList<>(getValue())); }
}
