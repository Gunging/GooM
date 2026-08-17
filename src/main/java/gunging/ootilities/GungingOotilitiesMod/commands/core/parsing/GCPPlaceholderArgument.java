package gunging.ootilities.GungingOotilitiesMod.commands.core.parsing;

import org.jetbrains.annotations.NotNull;

/**
 * An argument that accepts placeholders one way or another
 *
 * @author Gunging
 * @since 1.0.0
 */
public abstract class GCPPlaceholderArgument<Value> extends GCPProvidedArgument<Value> {

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public boolean isWasPlaceholder() {
        return wasPlaceholder;
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public void setWasPlaceholder(boolean wasPlaceholder) {
        this.wasPlaceholder = wasPlaceholder;
    }

    /**
     * If the input provided was a placeholder, and not an actual player name
     *
     * @since 1.0.0
     */
    boolean wasPlaceholder;

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override public boolean parsingErrorIsSoft() { return wasPlaceholder; }

    /**
     * @param explicit The text provided by the user
     *
     * @author Gunging
     * @since 1.0.0
     */
    public GCPPlaceholderArgument(@NotNull String explicit) {
        super(explicit);
    }
}
