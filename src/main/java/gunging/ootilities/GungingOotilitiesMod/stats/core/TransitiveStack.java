package gunging.ootilities.GungingOotilitiesMod.stats.core;

import gunging.ootilities.GungingOotilitiesMod.mixininterfaces.WithTransitiveStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

/**
 * A Stats Stack that actually is a collection of other
 * Stat Stacks, can be highly dynamic.
 *
 * @author Gunging
 * @since 1.0.0
 */
public class TransitiveStack extends StatStack {

    /**
     * The object owning this Transitive Stack
     *
     * @since 1.0.0
     */
    @NotNull final WithTransitiveStack transitiveParent;

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public @NotNull WithTransitiveStack getTransitiveParent() {
        return transitiveParent;
    }

    /**
     * @param parent The object owning this Transitive Stack
     *
     * @author Gunging
     * @since 1.0.0
     */
    public TransitiveStack(@NotNull WithTransitiveStack parent) {
        this.transitiveParent = parent;
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @NotNull ArrayList<StatStacked> getChildStacks() {
        return getTransitiveParent().gungingoom$getChildStacks();
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @Nullable StatStacked getParentStack() {
        return getTransitiveParent().gungingoom$getParentStack();
    }
}
