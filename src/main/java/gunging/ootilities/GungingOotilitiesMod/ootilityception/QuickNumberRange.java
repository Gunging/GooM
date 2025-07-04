package gunging.ootilities.GungingOotilitiesMod.ootilityception;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * You see how vanilla minecraft handles number ranges?
 * <p>
 * I'm talking about the command selectors, <code>@e[distance=..10]</code> for example.
 * <p>
 * Well, this system is genius :
 * Allows negative and decimal numbers, as well as infinity as one of the bounds of detection.
 *
 * @author Gunging
 * @since 1.0.0
 */
public abstract class QuickNumberRange<E> implements Cloneable {

    /**
     * If defined, this indicates the lower range the user specified.
     *
     * @since 1.0.0
     */
    @Nullable E minimumInclusive;

    /**
     * If defined, this indicates the higher range the user specified.
     *
     * @since 1.0.0
     */
    @Nullable E maximumInclusive;

    /**
     * If defined, this indicates the lower range the user specified.
     * @see #hasMin()
     *
     * @author Gunging
     * @since 1.0.0
     */
    @Nullable public E getMinimumInclusive() { return minimumInclusive; }

    /**
     * If defined, this indicates the higher range the user specified.
     * @see #hasMax()
     *
     * @author Gunging
     * @since 1.0.0
     */
    @Nullable public E getMaximumInclusive() { return maximumInclusive; }

    /**
     * Indicates if the user specified the lower range {@link #getMinimumInclusive()}.
     *
     * @author Gunging
     * @since 1.0.0
     */
    public boolean hasMin() { return minimumInclusive != null; }

    /**
     * Indicates if the user specified the higher range {@link #getMaximumInclusive()}.
     *
     * @author Gunging
     * @since 1.0.0
     */
    public boolean hasMax() { return maximumInclusive != null; }

    /**
     * This '<i>Quick Number Range</i>' is actually just a number,
     * because the user specified the same as the min and max.
     *
     * @author Gunging
     * @since 1.0.0
     */
    public boolean isSimple() {

        // If either bound is missing, this is not simple.
        if (!hasMax() || !hasMin()) { return false; }

        // They match-yo?
        return Objects.equals(getMaximumInclusive(), getMinimumInclusive());
    }

    /**
     * This '<i>Quick Number Range</i>' is actually just a number,
     * because the user specified the same as the min and max.
     *
     * @author Gunging
     * @since 1.0.0
     */
    public boolean isFiniteRanged() {

        // If either bound is missing, this is not simple.
        if (!hasMax() || !hasMin()) { return false; }

        // They must not match
        return !Objects.equals(getMaximumInclusive(), getMinimumInclusive());
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
    public QuickNumberRange(@Nullable E min, @Nullable E max) {
        minimumInclusive = min;
        maximumInclusive = max;
        restituteRange();
    }

    /**
     * Makes sure that the upper bound is greater than the minimum bound
     *
     * @author Gunging
     * @since 1.0.0
     */
    public void restituteRange() {

        if (getMinimumInclusive() == null || getMaximumInclusive() == null) { return; }

        // The ranges must be defined and flipped
        if (isGreaterThan(getMinimumInclusive(), getMaximumInclusive())) {

            // Flip them
            E max = minimumInclusive;
            minimumInclusive = maximumInclusive;
            maximumInclusive = max;
        }
    }

    /**
     * When both bounds are null, then we accept any non-null value.
     *
     * @return If this Quick Number Range accepts any (non-null) value to succeed.
     *
     * @author Gunging
     * @since 1.0.0
     */
    public boolean isAny() { return getMinimumInclusive() == null && getMaximumInclusive() == null; }

    /**
     * @param basis The number we want to know if it is greater than
     * @param target The number to compare against
     *
     * @return If basis is greater than target
     *
     * @author Gunging
     * @since 1.0.0
     */
    public abstract boolean isGreaterThan(@NotNull E basis, @NotNull E target);

    /**
     * @param basis The number we want to know if it is greater than
     * @param target The number to compare against
     *
     * @return If basis is greater than target
     *
     * @author Gunging
     * @since 1.0.0
     */
    public abstract boolean isLessThan(@NotNull E basis, @NotNull E target);

    /**
     * @param test Number in question.
     * @return If a number is within the boundaries.
     *
     * @author Gunging
     * @since 1.0.0
     */
    @Contract("null -> false")
    public boolean inRange(@Nullable E test) {
        //CHK// MythicCraftingManager.log("&8QNR &eA&7 Testing number &b" + test + "&7 to fit in &3" + this.toString());
        if (test == null) { return false; }

        // If it has minimum
        if (hasMin()) {

            // Fail if below minimum
            if (isLessThan(test, getMinimumInclusive())) {
                //CHK// MythicCraftingManager.log("&8QNR &eA&7 Test less than the minimum &b" + getMinimumInclusive() + ", &cFail");
                return false; }
        }

        // If it has maximum
        if (hasMax()) {

            // Fail if above maximum
            if (isGreaterThan(test, getMaximumInclusive())) {
                //CHK// MythicCraftingManager.log("&8QNR &eA&7 Test more than the minimum &b" + getMaximumInclusive() + ", &cFail");
                return false; }
        }

        // Success
        //CHK// MythicCraftingManager.log("&8QNR &eA&7 Test within range &aSuccess");
        return true;
    }

    /**
     * @return A String that encodes for this Quick Number Range
     *
     * @see #toStringColored()
     *
     * @author Gunging
     * @since 1.0.0
     */
    @Override public String toString() {

        // Simple?
        if (isSimple()) { return OotilityNumbers.removeDecimalZeros(String.valueOf(getMinimumInclusive())); }

        // Get
        String minn = ""; String maxn = "";
        if (hasMin()) { minn = OotilityNumbers.removeDecimalZeros(String.valueOf(getMinimumInclusive())); }
        if (hasMax()) { maxn = OotilityNumbers.removeDecimalZeros(String.valueOf(getMaximumInclusive())); }

        // Build
        return minn + ".." + maxn;
    }

    /**
     * It's basically a <code>toString()</code> method but looks nicer.
     * Use if you're going to display the value in the console IDK.
     *
     * @see #toString()
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull
    public String toStringColored() {

        // Simple?
        if (isSimple()) { return OotilityNumbers.removeDecimalZeros(String.valueOf(getMinimumInclusive())); }

        // Get
        String minn = "-∞"; String maxn = "∞";
        if (hasMin()) { minn = OotilityNumbers.removeDecimalZeros(String.valueOf(getMinimumInclusive())); }
        if (hasMax()) { maxn = OotilityNumbers.removeDecimalZeros(String.valueOf(getMaximumInclusive())); }

        // Build
        return minn + " to " + maxn;
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override public abstract QuickNumberRange<E> clone();
}
