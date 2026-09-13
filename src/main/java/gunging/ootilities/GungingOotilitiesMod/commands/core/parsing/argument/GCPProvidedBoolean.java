package gunging.ootilities.GungingOotilitiesMod.commands.core.parsing.argument;

import gunging.ootilities.GungingOotilitiesMod.commands.core.parsing.GCPProvidedArgument;
import gunging.ootilities.GungingOotilitiesMod.ootilityception.OotilityNumbers;
import org.jetbrains.annotations.NotNull;

/**
 * An instance of a boolean argument that was provided
 *
 * @author Gunging
 * @since 1.0.0
 */
public class GCPProvidedBoolean extends GCPProvidedArgument<Boolean> {

    /**
     * @param explicit The text provided by the user
     *
     * @author Gunging
     * @since 1.0.0
     */
    public GCPProvidedBoolean(@NotNull String explicit) {
        super(explicit);

        // Parse this value
        setParsed(OotilityNumbers.BooleanParse(explicit));
        if (getParsed() == null) { setParsingError("$bExpected $etrue$b/$efalse$b instead of '$f" + explicit + "$b'. ");  }
    }
}
