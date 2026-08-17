package gunging.ootilities.GungingOotilitiesMod.mixininterfaces;

import gunging.ootilities.GungingOotilitiesMod.stats.core.StatStack;
import org.jetbrains.annotations.NotNull;

/**
 * Borrowing the power of the Stat Stack by containing
 * your own Stat Stack, how easy you have it these days!
 *
 * @author Gunging
 * @since 1.0.0
 */
public interface WithStatsStack {

    /**
     * @return The stat stack of this object
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull StatStack gungingoom$getStatStack();
}
