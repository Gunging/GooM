package gunging.ootilities.GungingOotilitiesMod.stats.definitions;

import gunging.ootilities.GungingOotilitiesMod.commands.friendly.FriendlyFeedbackProvider;
import gunging.ootilities.GungingOotilitiesMod.ootilityception.OotilityNumbers;
import gunging.ootilities.GungingOotilitiesMod.ootilityception.PlusMinusPercent;
import gunging.ootilities.GungingOotilitiesMod.stats.core.StatDefinition;
import gunging.ootilities.GungingOotilitiesMod.stats.core.StatValue;
import gunging.ootilities.GungingOotilitiesMod.stats.values.DoubleStat;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

/**
 * Represents a metric that expects a double-precision number
 *
 * @author Gunging
 * @since 1.0.0
 */
public class DoubleDefinition extends StatDefinition<Double> {

    /**
     * @param definitionID The unique identifier of this stat
     * @param def          The default value of this stat
     *
     * @author Gunging
     * @since 1.0.0
     */
    public DoubleDefinition(@NotNull String definitionID, @NotNull StatValue<? extends Double> def) {
        super(definitionID, def);
        withDisplayFeature(StatDefinition.DISPLAY_FEATURE_FORMAT, "#symbol-color##symbol#<#9f9f9f> #name-color##name#:<#9f9f9f> #value-color-neutral##plus##value##units#");
        withDisplayFeature(DISPLAY_FEATURE_UNITS, "");
    }

    /**
     * @param definitionID The unique identifier of this stat
     * @param def          The default value of this stat
     *
     * @author Gunging
     * @since 1.0.0
     */
    public DoubleDefinition(@NotNull String definitionID, double def) { this(definitionID, new DoubleStat(def)); }


    /**
     * @param definitionID The unique identifier of this stat
     *
     * @author Gunging
     * @since 1.0.0
     */
    public DoubleDefinition(@NotNull String definitionID) { this(definitionID, new DoubleStat()); }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @NotNull StatValue<? extends Double> merge(@NotNull StatValue<? extends Double> current, @NotNull StatValue<?> incoming) {
        if (!accepts(incoming)) { return current; }

        // Double addition
        Number inc = (Number) incoming.getValue();
        return new DoubleStat(current.getValue() + (double) inc);
    }

    /**
     * Accepts any number
     *
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public boolean accepts(@NotNull StatValue<?> value) { return value.getValue() instanceof Number; }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @Nullable StatValue<? extends Double> operation(@Nullable StatValue<? extends Double> current, @Nullable String operation, @Nullable FriendlyFeedbackProvider ffp) {

        // Immediate fail when no operation is provided
        if (operation == null) {
            FriendlyFeedbackProvider.logError(ffp, "Value $fnot$b provided, expected a number. ");
            return null; }

        // Perform PMP Operation, it MUST parse
        PlusMinusPercent pmp = PlusMinusPercent.getFromString(operation, ffp);
        if (pmp == null) { return null; }

        // Apply to the old
        StatValue<? extends Double> old = current == null ? getDefault() : current;
        return new DoubleStat(pmp.apply(old.getValue()));
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @NotNull String whenSerialized(@NotNull StatValue<? extends Double> current) {
        return current.getValue().toString();
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @Nullable StatValue<? extends Double> whenDeserialized(@NotNull String serialized, @Nullable FriendlyFeedbackProvider ffp) {
        Double ret = OotilityNumbers.DoubleParse(serialized);
        if (ret == null) {
            FriendlyFeedbackProvider.logError(ffp, "Expected a number instead of '$u{0}$b'. ", serialized);
            return null; }
        return new DoubleStat(ret);
    }

    /**
     * The number around which this stat is significant.
     * <br><br>
     * For example, some stats such as max health could
     * reasonably matter in the basis of "100" such that
     * increments of +1 or +2 are okay, but +0.1 and +0.2
     * are negligibly.
     * <br>
     * However, consider critical strike chance with a basis
     * of "1" (100%), in this case 0.1 does matter.
     * <br>
     * Finally consider something like weight in grams, you'd
     * care when carrying a basis of 10,000g but not so much
     * when you only have 30g on you.
     * <br><br>
     * Ultimately this could depend on context for advanced
     * uses, but it is decent enough to start with a static one.
     *
     * @since 1.0.0
     */
    double significanceBasis = 1;
    /**
     * If this stat is desirable, more is better.
     * <br><br>
     * When specifying a significance basis, a positive
     * basis means this is a desirable stat and a negative
     * basis means this is an undesirable stat.
     *
     * @since 1.0.0
     */
    boolean desirable = true;
    /**
     * @param basis The number around which this stat is significant.
     * @return This same object. Builder pattern.
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public DoubleDefinition withSignificanceBasis(double basis) {

        // Actually there ARE some constrains on this
        desirable = basis >= 0;
        if (basis < 0) { basis = -basis; }
        if (basis < (1E-9)) { basis = 1E-9; }

        this.significanceBasis = basis;
        return this;
    }
    /**
     * The number around which this stat is significant.
     *
     * @since 1.0.0
     */
    public double getSignificanceBasis() { return significanceBasis; }
    /**
     * @since 1.0.0
     */
    public boolean isDesirable() { return desirable; }

    /**
     * The multiplier when displaying this value with units
     *
     * @since 1.0.0
     */
    double unitsMultiplier = 1;
    /**
     * The units to give meaning to the multiplier
     *
     * @since 1.0.0
     */
    @NotNull String units = "";
    /**
     * @param multiplier The multiplication inverse of the symbol numeric meaning. Example "0.001"
     * @param symbol The symbol that carries meaning of units. Example "k"
     *
     * @return This same object. Builder pattern.
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public DoubleDefinition withUnits(double multiplier, @NotNull String symbol) {

        // Actually there ARE some constrains on this
        this.unitsMultiplier = multiplier;
        this.units = symbol;
        withDisplayFeature(DISPLAY_FEATURE_UNITS, symbol);
        return this;
    }
    /**
     * The number around which this stat is significant.
     *
     * @since 1.0.0
     */
    public double getUnitsMultiplier() { return unitsMultiplier; }
    /**
     * @since 1.0.0
     */
    @NotNull public String getUnits() { return units; }

    /**
     * The format by which other display features will display
     *
     * @since 1.0.0
     */
    public static final String DISPLAY_FEATURE_UNITS = "#units#";

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @NotNull ArrayList<String> whenDisplayed(@NotNull StatValue<? extends Double> current, boolean asTotal) {

        // If the super determined not to display this, then no more replacing is needed
        ArrayList<String> ret = new ArrayList<>();
        if (isDefault(current)) { return ret; }
        double value = current.getValue();

        // Cook coarseness by deciding the minimum increment of basis
        double basisQuantum = getSignificanceBasis() * 0.01D;
        double basisQuanta = value / basisQuantum;
        double basisAbsolute = basisQuanta < 0 ? -basisQuanta : basisQuanta;

        // If there is such a negligible value that no one would care, ignore it.
        if (basisAbsolute < 0.19D) { return ret; }
        double basisLog = Math.log10(getSignificanceBasis() * unitsMultiplier);
        int logDecimals = OotilityNumbers.round(basisLog - 3D);
        if (logDecimals > 0) { logDecimals = 0; } else { logDecimals = -logDecimals; }

        double exactQuanta = OotilityNumbers.round(basisQuanta, 1);
        double approximateQuanta = OotilityNumbers.round(basisQuanta * 0.1D, 0) * 10D;

        exactQuanta *= basisQuantum * unitsMultiplier;
        approximateQuanta *= basisQuantum * unitsMultiplier;

        String qualitativeness = asTotal ?  value > 0 ? "Increased" : "Decreased" : value > 0 ? "Increases" : "Decreases";
        double qualitativeAbsolute = basisAbsolute * 0.01D;
        if (qualitativeAbsolute > 1000000) { qualitativeness = "Incomprehensibly " + qualitativeness; }
        else if (qualitativeAbsolute > 10000) { qualitativeness = "Godly " + qualitativeness; }
        else if (qualitativeAbsolute > 1000) { qualitativeness = "Insanely " + qualitativeness; }
        else if (qualitativeAbsolute > 100) { qualitativeness = "Extremely " + qualitativeness; }
        else if (qualitativeAbsolute > 10) { qualitativeness = "Greatly " + qualitativeness; }
        else if (qualitativeAbsolute > 2) { qualitativeness = "Largely " + qualitativeness; }
        if (qualitativeAbsolute < 0.02) { qualitativeness = "Minimally " + qualitativeness; }
        else if (qualitativeAbsolute < 0.1) { qualitativeness = "Slightly " + qualitativeness; }

        String YES = StatDefinition.DISPLAY_COLOR_DESIRABLE;
        String NO = StatDefinition.DISPLAY_COLOR_UNDESIRABLE;
        String singleLine = getDisplayFeature(DISPLAY_FEATURE_FORMAT)
                .replace(StatDefinition.DISPLAY_FEATURE_VALUE_COLOR_DESIRABLE, isDesirable() ? value >= 0 ? YES : NO : value >= 0 ? NO : YES)
                .replace(StatDefinition.DISPLAY_FEATURE_PLUS_VALUE, asTotal ? "" : value >= 0 ? "+" : "")
                .replace(StatDefinition.DISPLAY_FEATURE_EXACT_VALUE, OotilityNumbers.readableRounding(exactQuanta, logDecimals))
                .replace(StatDefinition.DISPLAY_FEATURE_APPROXIMATE_VALUE, OotilityNumbers.readableRounding(approximateQuanta, logDecimals - 2))
                .replace(StatDefinition.DISPLAY_FEATURE_COARSE_VALUE, OotilityNumbers.readableRounding(approximateQuanta, logDecimals - 3))
                .replace(StatDefinition.DISPLAY_FEATURE_QUALITATIVE_VALUE, qualitativeness);

        // Okay cook it
        ret.add(cookDisplayFeatures(singleLine));
        return ret;
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @NotNull DoubleDefinition withDisplayFeature(@NotNull String feature, @NotNull String value) {
        return (DoubleDefinition) super.withDisplayFeature(feature, value);
    }
}
