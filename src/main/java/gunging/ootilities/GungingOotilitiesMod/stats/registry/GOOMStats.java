package gunging.ootilities.GungingOotilitiesMod.stats.registry;

import gunging.ootilities.GungingOotilitiesMod.stats.core.StatDefinition;
import gunging.ootilities.GungingOotilitiesMod.stats.definitions.BooleanDefinition;
import gunging.ootilities.GungingOotilitiesMod.stats.definitions.DoubleDefinition;
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
            .withSignificanceBasis(100);

    /**
     * Blessing of Mangoes
     *
     * @since 1.0.0
     */
    public static BooleanDefinition BLESSING_OF_MANGOES = new BooleanDefinition("GOOM_BLESSING")
            .withDisplayFeature(StatDefinition.DISPLAY_FEATURE_NAME,"Blessing of Mangoes");

    /**
     * The tag that saves data in entity and item NBT
     *
     * @since 1.0.0
     */
    @NotNull public static final String GOOM_STATS_NBT_TAG = "GooMStats";
}
