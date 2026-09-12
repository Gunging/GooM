package gunging.ootilities.GungingOotilitiesMod.mixininterfaces;

import gunging.ootilities.GungingOotilitiesMod.stats.core.StatStacked;
import gunging.ootilities.GungingOotilitiesMod.stats.core.TransitiveStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

/**
 * Represents a class that contains other stat stacks,
 * such as the player inventory.
 *
 * @author Gunging
 * @since 1.0.0
 */
public interface WithTransitiveStack {

    /**
     * @return The parent stack of this transitive stack
     *
     * @author Gunging
     * @since 1.0.0
     */
    @Nullable StatStacked gungingoom$getParentStack();

    /**
     * @return The totals of stats of this object
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull TransitiveStack gungingoom$getContainedStatStacks();

    /**
     * @return The list of child stat stacks
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull ArrayList<StatStacked> gungingoom$getChildStacks();
}
