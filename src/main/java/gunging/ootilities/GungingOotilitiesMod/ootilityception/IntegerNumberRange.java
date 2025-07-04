package gunging.ootilities.GungingOotilitiesMod.ootilityception;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The same as a Quick Number Range but works with integers.
 *
 * @author Gunging
 * @since 1.0.0
 */
public class IntegerNumberRange extends QuickNumberRange<Integer> {

    /**
     * Supported Formats:
     * <p><code>[m]..[M]</code>  (Vanilla range, the way used in command selectors)
     * </p><code>[n]</code> (Just a number; basically <code>=</code> operator)
     * <p><code>..[M]</code> (anything up to this; basically less-than-or-equals operator)
     * </p><code>[m]..</code> (this and on; basically greater-than-or-equals operator)
     * <p></p>
     * Obviously, number <code>[m]</code> is the minimum, and <code>[M]</code> the maximum;
     * They may not even be specified.
     *
     * @return <code>null</code>> if incorrect format.
     *
     * @author Gunging
     * @since 1.0.0
     */
    @Contract("null -> null") @Nullable public static IntegerNumberRange getFromString(@Nullable String qnr) {

        // Parsing errors before any parsing errors could have existed...
        if (qnr == null) { return null; }

        // The special case, when neither bound is specified (bruh but ok)
        if (qnr.equals("..")) { return new IntegerNumberRange(null, null); }
        if (qnr.equals("*")) { return new IntegerNumberRange(null, null); }

        // Number itself? So it's basically an EXACTLY this value, I'll allow it.
        Integer asItself = OotilityNumbers.IntegerParse(qnr);
        if (asItself != null) { return new IntegerNumberRange(asItself, asItself); }

        // Split
        if (qnr.contains("..")) {

            // Split
            String[] blit = qnr.split("\\.\\.");

            // Must be length two
            if (blit.length == 2 || blit.length == 1) {

                // Failure?
                boolean failure = false;

                /*
                 *   Identify minor and major bounds
                 */
                String m = "", M = "";

                // Are both specified?
                if (blit.length == 2) {

                    // In order
                    m = blit[0];
                    M = blit[1];

                    // Only one is specified
                } else {

                    // Unrestricted Bounds
                    if(qnr.startsWith("..")) {

                        // Its second
                        M = blit[0];

                    } else {

                        // Its first
                        m = blit[0];
                    }
                }

                /*
                 *  Parse both of them
                 */
                Integer min = OotilityNumbers.IntegerParse(m);
                Integer max = OotilityNumbers.IntegerParse(M);

                // Try to parse both
                if (!m.isEmpty() && min == null) {

                    // Number Format Error
                    failure = true; }

                // Try to parse both
                if (!M.isEmpty() && max == null) {

                    // Number Format Error
                    failure = true; }

                // Success?
                if (!failure) {

                    // Return
                    return new IntegerNumberRange(min, max);
                }
            }
        }

        // Something went wrong
        return null;
    }

    /**
     * An easy way of testing if the number falls between two numbers.
     *
     * @param min The (optional) lower bound
     * @param max The (optional) upper bound
     *
     * @author Gunging
     * @since 1.0.0
     */
    public IntegerNumberRange(@Nullable Integer min, @Nullable Integer max) { super(min, max); }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public boolean isGreaterThan(@NotNull Integer basis, @NotNull Integer target) {
        return basis > target;
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public boolean isLessThan(@NotNull Integer basis, @NotNull Integer target) {
        return basis < target;
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public IntegerNumberRange clone() { return new IntegerNumberRange(getMinimumInclusive(), getMaximumInclusive()); }
}
