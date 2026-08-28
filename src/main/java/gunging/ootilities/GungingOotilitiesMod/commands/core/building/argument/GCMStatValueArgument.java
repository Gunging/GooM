package gunging.ootilities.GungingOotilitiesMod.commands.core.building.argument;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

/**
 * A string argument but with better suggestions
 *
 * @author Gunging
 * @since 1.0.0
 */
public class GCMStatValueArgument extends GCMStringArgument {

    /**
     * @param argumentName        The name of this argument
     * @param argumentDescription A short description of this argument
     *
     * @author Gunging
     * @since 1.0.0
     */
    public GCMStatValueArgument(@NotNull String argumentName, @NotNull String argumentDescription) {
        super(argumentName, argumentDescription);
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @NotNull ArrayList<String> getUbiquitousSuggestions() {
        ArrayList<String> ret = new ArrayList<>();
        ret.add("+10");
        ret.add("n20");
        ret.add("+50%");
        ret.add("-30");
        ret.add("Mangoes");
        ret.add("true");
        ret.add("false");
        if (isGreedy()) { ret.add("A phrase or line of text"); }
        return ret;
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @NotNull GCMStatValueArgument withDefaultValue(@Nullable String def) {
        return (GCMStatValueArgument) super.withDefaultValue(def);
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @NotNull GCMStatValueArgument withGreedy(boolean greed) {
        return (GCMStatValueArgument) super.withGreedy(greed);
    }
}
