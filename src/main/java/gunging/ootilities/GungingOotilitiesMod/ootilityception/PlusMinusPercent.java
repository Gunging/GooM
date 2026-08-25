package gunging.ootilities.GungingOotilitiesMod.ootilityception;

import gunging.ootilities.GungingOotilitiesMod.commands.friendly.FriendlyFeedbackProvider;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

/**
 * A most compact way for a user to perform operations on a variable.
 * <br>Parse a PMP from a string using {@link #getFromString(String)}
 * Once parsed, apply a PMP to any number with {@link #apply(double)}.
 * <br><br>
 * Examples (let the base number be <b>200</b>):
 * <br> +5   = <b>205</b>     <code>'Add'</code>
 * <br> -5  = <b>195</b>     <code>'Subtract'</code>
 * <br> 5    = <b>5</b>       <code>'Set'</code>
 * <br> n5  = <b>-5</b>      <code>'Set'</code>
 * <br> 5%   = <b>10</b>      <code>'Multiply'</code>
 * <br> +5% = <b>210</b>     <code>'Percent Bonus'</code>
 * <br> -5%  = <b>190</b>     <code>'Percent Deduction'</code>
 * <br> n5% = <b>-10</b>     <code>'Flip Multiplication'</code>
 *
 * @author Gunging
 * @since 1.0.0
 */
public class PlusMinusPercent {

    /**
     * The constant to operate with, not to be confused with
     * the <b>base</b>. The base is the value this operation
     * will be performed on, and it is provided only at the
     * moment of applying it
     *
     * @since 1.0.0
     */
    double constant;
    /**
     * @author Gunging
     * @since 1.0.0
     */
    public double getConstant() { return constant; }
    /**
     * @author Gunging
     * @since 1.0.0
     */
    public void setConstant(double constant) { this.constant = constant; }

    /**
     * Whether this will be linearly combined with the <b>base</b>
     * <br>..."linearly combined with" just means "added to" LOL
     *
     * @since 1.0.0
     */
    boolean relative;
    /**
     * @author Gunging
     * @since 1.0.0
     */
    public boolean isRelative() { return relative; }
    /**
     * @author Gunging
     * @since 1.0.0
     */
    public void setRelative(boolean relative) { this.relative = relative; }

    /**
     * Whether this will be scaled with the <b>base</b>
     *
     * @since 1.0.0
     */
    boolean multiplicative;
    /**
     * @author Gunging
     * @since 1.0.0
     */
    public boolean isMultiplicative() { return multiplicative; }
    /**
     * @author Gunging
     * @since 1.0.0
     */
    public void setMultiplicative(boolean multiplicative) { this.multiplicative = multiplicative; }

    /**
     * Build a new PMP without needing to parse a string.
     *
     * @param constant A number that represents this operation. Not to be confused with the <i>base</i>.
     *                 The base is the external number you will apply this operation to. We'll call it
     *                 <b>C</b> for now.
     *
     * @param relative Will <b>C</b> be added/subtracted from the base? (If <code>false</code>, the
     *                 base will be <i>set</i> to <b>C</b>).
     *
     * @param multiplicative If not additive, instead of <i>setting</i> the base
     *                       to <b>C</b>, the base will be multiplied by <b>C</b>.
     *                       <br>
     *                       If additive, instead of adding <b>C</b> to the base,
     *                       the base will be multiplied by <b>1 + C</b> (Which is identical
     *                       to the expression <u><code>base = base + <b>C</b>*base</code></u>)
     *
     * @see #getFromString(String)
     *
     * @author Gunging
     * @since 1.0.0
     */
    public PlusMinusPercent(double constant, boolean relative, boolean multiplicative) {
        this.constant = constant;
        this.relative = relative;
        this.multiplicative = multiplicative;
    }

    /**
     * Perform the operation represented by this PMP onto the <code>base</code>.
     *
     * @param base Base value to apply this operation onto
     *
     * @author Gunging
     * @since 1.0.0
     */
    public double apply(double base) {

        // Stores a copy of the constant that may be freely modified through this method (makes math easier)
        double oV = constant;

        // Is it multiplicative?
        if (multiplicative) {

            // Relative is on base 100%; so +50% means it multiplies by 1.5; while -50% is *0.5
            if (relative) { oV += 1; }

            return base * oV;

            // It's not a multiplicative, its scalar
        } else {

            // If it is relative to the source
            if (relative) {

                // Just shift by the source
                return  base + oV;

                // Otherwise it's a straight-up set
            } else {

                // That's the set
                return oV;
            }
        }
    }

    /**
     * @param pmp String that encodes for a PMP
     *
     * @return This string parsed into a PMP, or <code>null</code> on <b>FAILURE</b>.
     *
     * @author Gunging
     * @since 1.0.0
     */
    @Contract("null -> null")
    @Nullable public static PlusMinusPercent getFromString(@Nullable String pmp) { return getFromString(pmp, null); }

    /**
     * @param pmp String that encodes for a PMP
     * @param ffp Feedback Provider to tell the user what went wrong if their PMP did not parse
     *
     * @return This string parsed into a PMP, or <code>null</code> on <b>FAILURE</b>.
     *
     * @author Gunging
     * @since 1.0.0
     */
    @Contract("null, _ -> null")
    @Nullable public static PlusMinusPercent getFromString(@Nullable String pmp, @Nullable FriendlyFeedbackProvider ffp) {

        // Parsing errors before any parsing errors could have existed...
        if (pmp == null) {
            FriendlyFeedbackProvider.logError(ffp, "No value provided to parse PlusMinusPercent. ");
            return null; }

        // Interpret the user's input
        boolean relativity = false;
        boolean multiplicativity = false;
        double positivity = 1.0;
        double value;
        String unsignedArg = pmp;

        if (pmp.startsWith("-")) {
            relativity = true;
            positivity = -1.0;
            unsignedArg = pmp.substring(1);

        } else if (pmp.startsWith("+")) {
            relativity = true;
            unsignedArg = pmp.substring(1);

        } else if (pmp.startsWith("n")) {
            positivity = -1.0;
            unsignedArg = pmp.substring(1);
        }

        if (pmp.endsWith("%")) {
            multiplicativity = true;
            positivity *= 0.01;
            unsignedArg = unsignedArg.substring(0, unsignedArg.length() - 1);
        }

        // Attempt to parse the constant
        Double variability = OotilityNumbers.DoubleParse(unsignedArg);

        // Success?
        if (variability != null) {

            // Use
            value = variability * positivity;

        // Fail
        } else {
            FriendlyFeedbackProvider.logError(ffp, "Could $fnot parse$b numeric value '$r{0}$b' from '$i{1}$b' (After removing ±, n, and %). ", unsignedArg, pmp);
            return null;
        }

        // Success
        return new PlusMinusPercent(value, relativity, multiplicativity);
    }

    /**
     * @return This PMP, serialized into a string that can be parsed later with {@link #getFromString(String)}
     *
     * @author Gunging
     * @since 1.0.0
     */
    @Override public String toString() {
        StringBuilder str = new StringBuilder();

        // Relative?
        if (isRelative() && !isMultiplicative()) {

            // n or no prefix?
            if (getConstant() < 0) { str.append('-'); } else { str.append('+'); }

        } else {

            // n or no prefix?
            if (getConstant() < 0) { str.append('n'); }
        }

        // Append absolute value
        double val = getConstant();
        if (isMultiplicative()) { val *= 100; }

        // Append thay
        str.append(OotilityNumbers.readableRounding(val, 2));

        // Append percent
        if (isMultiplicative()) { str.append('%'); }

        // Build
        return str.toString();
    }
}
