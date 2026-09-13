package gunging.ootilities.GungingOotilitiesMod.stats.registry;

import gunging.ootilities.GungingOotilitiesMod.stats.core.StatDefinition;
import gunging.ootilities.GungingOotilitiesMod.stats.definitions.BooleanDefinition;
import gunging.ootilities.GungingOotilitiesMod.stats.definitions.DoubleDefinition;
import gunging.ootilities.GungingOotilitiesMod.stats.definitions.StringListDefinition;
import org.jetbrains.annotations.NotNull;

/**
 * A series of example stats that do nothing, really
 *
 * @since 1.0.0
 * @author Gunging
 */
public class GOOMStats {

    /**
     * Basically plot armor
     *
     * @since 1.0.0
     */
    public static DoubleDefinition AURA = new DoubleDefinition("GOOM_AURA")
            .withDisplayFeature(StatDefinition.DISPLAY_FEATURE_NAME,"Aura")
            .withDisplayFeature(StatDefinition.DISPLAY_FEATURE_FORMAT, "#symbol-color##symbol#<#9f9f9f> #name-color##name##name-color#:<#9f9f9f> #value-color-neutral##value##units#")
            .withSignificanceBasis(10000)
            .withUnits(0.001, "K");

    /**
     * Basically plot armor
     *
     * @since 1.0.0
     */
    public static DoubleDefinition NIRVANA_COMPLETION = new DoubleDefinition("GOOM_NIRVANA")
            .withDisplayFeature(StatDefinition.DISPLAY_FEATURE_NAME,"Nirvana Completion")
            .withDisplayFeature(StatDefinition.DISPLAY_FEATURE_FORMAT, "#symbol-color##symbol#<#9f9f9f> #name-color##name##name-color#:<#9f9f9f> #value-color-neutral##approximate##units#")
            .withSignificanceBasis(1)
            .withUnits(100, "%");

    /**
     * Blessing of Mangoes
     *
     * @since 1.0.0
     */
    public static BooleanDefinition BLESSING_OF_MANGOES = new BooleanDefinition("GOOM_BLESSING")
            .withDisplayFeature(StatDefinition.DISPLAY_FEATURE_NAME,"Blessing of Mangoes");

    /**
     * Blessing of Mangoes
     *
     * @since 1.0.0
     */
    public static BooleanDefinition AIR_PROTECTION_DISABLE = new BooleanDefinition("GOOM_PROTECT_AIR", true)
            .withDisplayFeature(StatDefinition.DISPLAY_FEATURE_NAME,"Air Not Protected");

    /**
     * Basically plot armor
     *
     * @since 1.0.0
     */
    public static DoubleDefinition CURSED_ENERGY = new DoubleDefinition("GOOM_CURSED_ENERGY")
            .withDisplayFeature(StatDefinition.DISPLAY_FEATURE_NAME,"Cursed Energy")
            .withDisplayFeature(StatDefinition.DISPLAY_FEATURE_FORMAT, "#symbol-color##symbol#<#9f9f9f> #value-color-desirable##qualitative#<#9f9f9f> #name-color##name##name-color#")
            .withSignificanceBasis(10);

    /**
     * Basically plot armor
     *
     * @since 1.0.0
     */
    public static StringListDefinition VAPE_DEALERS = new StringListDefinition("GOOM_VAPES")
            .withDisplayFeature(StatDefinition.DISPLAY_FEATURE_NAME,"Vape Dealers");

    /**
     * The tag that saves data in entity and item NBT
     *
     * @since 1.0.0
     */
    @NotNull public static final String GOOM_STATS_NBT_TAG = "GooMStats";
}
