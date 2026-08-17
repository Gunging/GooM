package gunging.ootilities.GungingOotilitiesMod.commands.core.building;

import gunging.ootilities.GungingOotilitiesMod.commands.core.parsing.GCPCommandStack;
import gunging.ootilities.GungingOotilitiesMod.commands.core.parsing.GCPProvidedArgument;
import org.jetbrains.annotations.NotNull;

/**
 * An expected argument that requires context to parse
 *
 * @param <Value> The object class of the parsed argument
 *
 * @author Gunging
 * @since 1.0.0
 */
public abstract class GCMContextArgument<Value> extends GCMExpectedArgument<Value>  {

    /**
     * @param argumentName        The name of this argument
     * @param argumentDescription A short description of this argument
     *
     * @author Gunging
     * @since 1.0.0
     */
    public GCMContextArgument(@NotNull String argumentName, @NotNull String argumentDescription) {
        super(argumentName, argumentDescription);
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @NotNull GCPProvidedArgument<Value> provide(@NotNull GCPCommandStack stack) {
        return provide(stack, getMyExplicit(stack).getExplicit());
    }

    /**
     * @param explicit A single string representing this argument
     *
     * @return A provider that can interpret this value
     *
     * @author Gunging
     * @since 1.0.0
     */
    public abstract @NotNull GCPProvidedArgument<Value> provide(@NotNull GCPCommandStack context, @NotNull String explicit);
}
