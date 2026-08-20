package gunging.ootilities.GungingOotilitiesMod.commands.core.building.argument;

import gunging.ootilities.GungingOotilitiesMod.commands.core.building.GCMExpectedArgument;
import gunging.ootilities.GungingOotilitiesMod.commands.core.building.GCMStandaloneArgument;
import gunging.ootilities.GungingOotilitiesMod.commands.core.parsing.argument.GCPProvidedDouble;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

/**
 * An argument that expects a double-precision number
 *
 * @author Gunging
 * @since 1.0.0
 */
public class GCMDoubleArgument extends GCMStandaloneArgument<Double> {

    /**
     * @param argumentName        The name of this argument
     * @param argumentDescription A short description of this argument
     *
     * @author Gunging
     * @since 1.0.0
     */
    public GCMDoubleArgument(@NotNull String argumentName, @NotNull String argumentDescription) {
        super(argumentName, argumentDescription);
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override public @NotNull GCPProvidedDouble provide(@NotNull String explicit) {
        return new GCPProvidedDouble(explicit);
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @NotNull ArrayList<String> getUbiquitousSuggestions() {
        ArrayList<String> ret = new ArrayList<>();
        ret.add("5");
        ret.add("15");
        ret.add("3.1416");
        ret.add("-2.5");
        ret.add("6.7");
        ret.add("-126.7");
        return ret;
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @NotNull GCMDoubleArgument withDefaultValue(@Nullable Double def) {
        return (GCMDoubleArgument) super.withDefaultValue(def);
    }
}
