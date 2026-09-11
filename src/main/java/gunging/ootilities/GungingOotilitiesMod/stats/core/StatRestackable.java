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
     * @param restacked The lambda to run when this stat stack was reloaded
     *
     * @return This same Stat Stack, it is a builder pattern.
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull StatStackable withWhenReloaded(@Nullable WhenRestacked restacked);
}
