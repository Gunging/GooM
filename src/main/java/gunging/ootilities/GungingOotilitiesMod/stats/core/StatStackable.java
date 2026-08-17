package gunging.ootilities.GungingOotilitiesMod.stats.core;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

/**
 * Something with the capacity to provide stat instances.
 *
 * @author Gunging
 * @since 1.0.0
 */
public interface StatStackable {

    /**
     * The stats provided by this stat stack
     * specifically and not any of its children.
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull HashMap<String, StatInstance<?>> getInherentStats();

    /**
     * Sets the specified inherent stat, marks this
     * and its parent (up to the root parent) as
     * having received changes.
     *
     * @param stat Stat instance to set/insert
     *
     * @author Gunging
     * @since 1.0.0
     */
    void setStat(@NotNull StatInstance<?> stat);

    /**
     * Sets the specified inherent stat, marks this
     * and its parent (up to the root parent) as
     * having received changes.
     *
     * @param statDefinition Stat definition to set
     * @param data Value for this stat
     *
     * @author Gunging
     * @since 1.0.0
     */
    <M> void setStat(@NotNull StatDefinition<M> statDefinition, @Nullable StatValue<? extends M> data);
}
