package gunging.ootilities.GungingOotilitiesMod.commands.core.parsing.argument;

import gunging.ootilities.GungingOotilitiesMod.GungingOotilitiesMod;
import gunging.ootilities.GungingOotilitiesMod.commands.core.parsing.GCPProvidedArgument;
import gunging.ootilities.GungingOotilitiesMod.stats.core.StatDefinition;
import org.jetbrains.annotations.NotNull;

/**
 * A instance of a GooM Stat Definition argument that was provided
 *
 * @author Gunging
 * @since 1.0.0
 */
public class GCPProvidedStat extends GCPProvidedArgument<StatDefinition<?>> {

    /**
     * @param explicit The text provided by the user
     *
     * @author Gunging
     * @since 1.0.0
     */
    public GCPProvidedStat(@NotNull String explicit) {
        super(explicit);

        // Parse it by checking the registered stats of course
        setParsed(GungingOotilitiesMod.getInstance().getStats().getStatDefinition(explicit));
        if (getParsed() == null) { setParsingError("$bStat '$f" + explicit + "$b' not found. ");  }
    }
}
