package gunging.ootilities.GungingOotilitiesMod.stats.core;


import org.jetbrains.annotations.NotNull;

/**
 * Runs a method after the stat stack recalculates,
 * which usually only happens after all changes
 * are made, and it is time to apply them.
 * <br><br>
 * This should be the result of that operation.
 *
 * @author Gunging
 * @since 1.0.0
 */
@FunctionalInterface
public interface WhenRestacked {

    /**
     * @author Gunging
     * @since 1.0.0
     */
    void whenStatStackReloaded(@NotNull StatStack stack);
}
