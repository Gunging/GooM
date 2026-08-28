package gunging.ootilities.GungingOotilitiesMod.stats.definitions;

import org.jetbrains.annotations.NotNull;

/**
 * A stat definition that is cooked and does nothing really.
 * It is a frail attempt at trying to save information that
 * would otherwise be deleted.
 *
 * @author Gunging
 * @since 1.0.0
 */
public class MissingDefinition extends StringDefinition {

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public MissingDefinition(@NotNull String definitionID) {
        super(definitionID);
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override  public boolean isValid() { return false; }
}
