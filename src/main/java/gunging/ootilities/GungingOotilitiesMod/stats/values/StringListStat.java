package gunging.ootilities.GungingOotilitiesMod.stats.values;

import gunging.ootilities.GungingOotilitiesMod.ootilityception.OotilityNumbers;
import gunging.ootilities.GungingOotilitiesMod.stats.core.StatValue;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

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

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    @NotNull public String toString() { return OotilityNumbers.collapseList(getValue(), ";"); }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public boolean equals(Object obj) {

        // Identify the collection we are comparing against
        Collection rawComp = null;

        // Compare as boolean
        if (obj instanceof Collection) {rawComp = (Collection) obj;}

        // Compare as Stat Value
        if (obj instanceof StatValue) {
            StatValue asStat = (StatValue) obj;
            if (asStat.getValue() instanceof Collection) {
                rawComp = (Collection) asStat.getValue();
            }
        }

        // Straight up no match
        if (rawComp == null || rawComp.size() != getValue().size()) {return false;}

        // Empty collections match I suppose
        if (getValue().isEmpty()) {return true;}

        // All contents must be strings, and present in my own array
        for (Object content : rawComp) {
            if (!(content instanceof String)) {return false;}

            // Check that every content is present
            boolean found = false;
            for (String present : getValue()) {
                if (present.equals(content)) {
                    found = true;
                    break;
                }
            }
            if (found) { continue; }
            return false;
        }

        /*
         * Strictly speaking, this may detect a false
         * positive if both lists have the same number
         * of elements, and the same elements, but repeated
         * different times.
         *
         * So:
         *
         * APPLE, APPLE, ORANGE
         * will find itself equal to
         * ORANGE, ORANGE, APPLE
         *
         * Though, not checking for same order is intended. This
         * String List Stat is not an ordered list.
         */

        // Matched, after all
        return true;
    }
}
