package gunging.ootilities.GungingOotilitiesMod.stats.registry;

import gunging.ootilities.GungingOotilitiesMod.stats.definitions.BooleanDefinition;
import gunging.ootilities.GungingOotilitiesMod.stats.definitions.DoubleDefinition;

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
    public static DoubleDefinition AURA = new DoubleDefinition("GOOM_AURA");

    /**
     * Blessing of Mangoes
     *
     * @since 1.0.0
     */
    public static BooleanDefinition BLESSING_OF_MANGOES = new BooleanDefinition("GOOM_BLESSING");
}
