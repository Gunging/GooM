package gunging.ootilities.GungingOotilitiesMod.commands.core.building;

import gunging.ootilities.GungingOotilitiesMod.commands.core.parsing.GCPCommandStack;
import gunging.ootilities.GungingOotilitiesMod.commands.core.parsing.GCPProvidedArgument;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * An expected argument that is guaranteed to not need
 * other arguments of the command to accomplish its purpose
 *
 * @param <Value> The object class of the parsed argument
 *
 * @author Gunging
 * @since 1.0.0
 */
public abstract class GCMStandaloneArgument<Value> extends GCMExpectedArgument<Value> {

    /**
     * @param argumentName        The name of this argument
     * @param argumentDescription A short description of this argument
     *
     * @author Gunging
     * @since 1.0.0
     */
    public GCMStandaloneArgument(@NotNull String argumentName, @NotNull String argumentDescription) {
        super(argumentName, argumentDescription);
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override public @NotNull ArrayList<String> getSuggestions(@NotNull GCPCommandStack stack) {
        return getUbiquitousSuggestions();
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override public @NotNull GCPProvidedArgument<Value> provide(@NotNull GCPCommandStack stack) {
        return provide(getMyExplicit(stack).getExplicit());
    }

    /**
     * @param explicit A single string representing this argument
     *
     * @return A provider that can interpret this value
     *
     * @author Gunging
     * @since 1.0.0
     */
    public abstract @NotNull GCPProvidedArgument<Value> provide(@NotNull String explicit);
}
