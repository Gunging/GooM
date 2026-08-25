package gunging.ootilities.GungingOotilitiesMod.commands.core.building.argument;

import gunging.ootilities.GungingOotilitiesMod.commands.core.building.GCMExpectedArgument;
import gunging.ootilities.GungingOotilitiesMod.commands.core.building.GCMStandaloneArgument;
import gunging.ootilities.GungingOotilitiesMod.commands.core.parsing.argument.GCPProvidedString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

/**
 * An argument that expects a line of text, any text, very foundational
 *
 * @author Gunging
 * @since 1.0.0
 */
public class GCMStringArgument extends GCMStandaloneArgument<String> {

    /**
     * @param argumentName        The name of this argument
     * @param argumentDescription A short description of this argument
     *
     * @author Gunging
     * @since 1.0.0
     */
    public GCMStringArgument(@NotNull String argumentName, @NotNull String argumentDescription) {
        super(argumentName, argumentDescription);
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override public @NotNull GCPProvidedString provide(@NotNull String explicit) {
        return new GCPProvidedString(explicit);
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @NotNull ArrayList<String> getUbiquitousSuggestions() {
        ArrayList<String> ret = new ArrayList<>();
        ret.add("Mangoes");
        ret.add("ANYTHING");
        ret.add("Text");
        if (isGreedy()) {
            ret.add("A phrase or line of text");
            ret.add("SAMPLE TEXT :O"); }
        return ret;
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @NotNull GCMStringArgument withDefaultValue(@Nullable String def) {
        return (GCMStringArgument) super.withDefaultValue(def);
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @NotNull GCMStringArgument withGreedy(boolean greed) {
        return (GCMStringArgument) super.withGreedy(greed);
    }
}