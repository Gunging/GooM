package gunging.ootilities.GungingOotilitiesMod.stats.core;

import org.jetbrains.annotations.NotNull;

/**
 * The value of something, regardless of what it is
 * <br><br>
 * In [Fire Damage +5], the [+5] part.
 *
 * @author Gunging
 * @since 1.0.0
 */
public abstract class StatValue<Measure> implements Cloneable {

    /**
     * @param value The value and extent to wrap herein
     *
     * @author Gunging
     * @since 1.0.0
     */
    public StatValue(@NotNull Measure value) { this.value = value; }

    /**
     * The value and extent encoded herein
     *
     * @since 1.0.0
     */
    @NotNull Measure value;

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public Measure getValue() { return value; }

    /**
     * @return A duplicate of this value. Deep clone.
     *
     * @author Gunging
     * @since 1.0.0
     */
    @Override abstract public StatValue<Measure> clone();
}
