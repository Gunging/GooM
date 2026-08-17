package gunging.ootilities.GungingOotilitiesMod.commands.core.parsing.argument;

import gunging.ootilities.GungingOotilitiesMod.commands.core.parsing.GCPProvidedArgument;
import gunging.ootilities.GungingOotilitiesMod.ootilityception.OotilityNumbers;
import org.jetbrains.annotations.NotNull;

/**
 * A instance of a double argument that was provided
 *
 * @author Gunging
 * @since 1.0.0
 */
public class GCPProvidedDouble extends GCPProvidedArgument<Double> {

    /**
     * @param explicit The text provided by the user
     *
     * @author Gunging
     * @since 1.0.0
     */
    public GCPProvidedDouble(@NotNull String explicit) {
        super(explicit);

        // Parse this value
        setParsed(OotilityNumbers.DoubleParse(explicit));
    }
}
