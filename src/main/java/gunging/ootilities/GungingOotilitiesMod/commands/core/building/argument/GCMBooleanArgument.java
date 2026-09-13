package gunging.ootilities.GungingOotilitiesMod.commands.core.building.argument;

import gunging.ootilities.GungingOotilitiesMod.commands.core.building.GCMStandaloneArgument;
import gunging.ootilities.GungingOotilitiesMod.commands.core.parsing.argument.GCPProvidedBoolean;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

/**
 * An argument that expects a boolean
 *
 * @author Gunging
 * @since 1.0.0
 */
public class GCMBooleanArgument extends GCMStandaloneArgument<Boolean> {

    /**
     * @param argumentName        The name of this argument
     * @param argumentDescription A short description of this argument
     *
     * @author Gunging
     * @since 1.0.0
     */
    public GCMBooleanArgument(@NotNull String argumentName, @NotNull String argumentDescription) {
        super(argumentName, argumentDescription);
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override public @NotNull GCPProvidedBoolean provide(@NotNull String explicit) {
        return new GCPProvidedBoolean(explicit);
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @NotNull ArrayList<String> getUbiquitousSuggestions() {
        ArrayList<String> ret = new ArrayList<>();
        ret.add("true");
        ret.add("false");
        ret.add("1");
        ret.add("0");
        return ret;
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @NotNull GCMBooleanArgument withDefaultValue(@Nullable Boolean def) {
        return (GCMBooleanArgument) super.withDefaultValue(def);
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @NotNull GCMBooleanArgument withGreedy(boolean greed) {
        return (GCMBooleanArgument) super.withGreedy(greed);
    }
}
