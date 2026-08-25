package gunging.ootilities.GungingOotilitiesMod.commands.core.parsing.argument;

import gunging.ootilities.GungingOotilitiesMod.commands.core.parsing.GCPProvidedArgument;
import org.jetbrains.annotations.NotNull;

/**
 * A string provided, with literally no parsing LOL this system is overkill for this
 *
 * @author Gunging
 * @since 1.0.0
 */
public class GCPProvidedString extends GCPProvidedArgument<String> {

    /**
     * @param explicit The text provided by the user
     *
     * @author Gunging
     * @since 1.0.0
     */
    public GCPProvidedString(@NotNull String explicit) {
        super(explicit);

        // Parse this value
        setParsed(explicit);
    }
}
