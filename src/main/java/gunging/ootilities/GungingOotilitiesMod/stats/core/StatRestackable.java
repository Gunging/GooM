package gunging.ootilities.GungingOotilitiesMod.stats.core;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Something that runs mini events when its stats change.
 *
 * @author Gunging
 * @since 1.0.0
 */
public interface StatRestackable {

    /**
     * @param restacked The lambda to run right before the stat totals are recalculated
     *
     * @return This same Stat Stack, it is a builder pattern.
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull StatStackable preStatTotalsReloaded(@Nullable WhenRestacked restacked);

    /**
     * @param restacked The lambda to run right after the stat totals are recalculated
     *
     * @return This same Stat Stack, it is a builder pattern.
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull StatStackable postStatTotalsReloaded(@Nullable WhenRestacked restacked);

    /**
     * @param restacked The lambda to run right after changes are made to the inherent stats list
     *
     * @return This same Stat Stack, it is a builder pattern.
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull StatStackable postInherentStatsChanged(@Nullable WhenRestacked restacked);
}
