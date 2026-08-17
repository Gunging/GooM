package gunging.ootilities.GungingOotilitiesMod.commands.core.building.argument;

import gunging.ootilities.GungingOotilitiesMod.commands.core.building.GCMStandaloneArgument;
import gunging.ootilities.GungingOotilitiesMod.commands.core.parsing.argument.GCPProvidedStat;
import gunging.ootilities.GungingOotilitiesMod.stats.core.StatDefinition;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * An argument that expects a GooM Stat Definition
 *
 * @author Gunging
 * @since 1.0.0
 */
public class GCMStatArgument extends GCMStandaloneArgument<StatDefinition<?>> {

    /**
     * @param argumentName        The name of this argument
     * @param argumentDescription A short description of this argument
     *
     * @author Gunging
     * @since 1.0.0
     */
    public GCMStatArgument(@NotNull String argumentName, @NotNull String argumentDescription) {
        super(argumentName, argumentDescription);
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override public @NotNull GCPProvidedStat provide(@NotNull String explicit) {
        return new GCPProvidedStat(explicit);
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @NotNull ArrayList<String> getUbiquitousSuggestions() {
        ArrayList<String> ret = new ArrayList<>();
        ret.add("FAKE_MAX_HEALTH");
        ret.add("FAKE_ATTACK_DAMAGE");
        ret.add("FAKE_MAX_MANA");
        ret.add("FAKE_ATTACK_SPEED");
        return ret;
    }
}
