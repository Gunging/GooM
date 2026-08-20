package gunging.ootilities.GungingOotilitiesMod.commands.forge.argument;

import gunging.ootilities.GungingOotilitiesMod.commands.core.building.GCMContextArgument;
import gunging.ootilities.GungingOotilitiesMod.commands.core.building.GCMExpectedArgument;
import gunging.ootilities.GungingOotilitiesMod.commands.core.parsing.GCPCommandStack;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * An argument that expects a player name
 *
 * @author Gunging
 * @since 1.0.0
 */
public class GCMPlayerArgument extends GCMContextArgument<ServerPlayer> {

    /**
     * @param argumentName        The name of this argument
     * @param argumentDescription A short description of this argument
     *
     * @author Gunging
     * @since 1.0.0
     */
    public GCMPlayerArgument(@NotNull String argumentName, @NotNull String argumentDescription) {
        super(argumentName, argumentDescription);
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override public @NotNull GCPProvidedPlayer provide(@NotNull GCPCommandStack context, @NotNull String explicit) {
        return new GCPProvidedPlayer(context, explicit);
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @NotNull ArrayList<String> getUbiquitousSuggestions() {
        ArrayList<String> ret = new ArrayList<>();
        ret.add("%player%");
        ret.add("gunging");
        ret.add("cocopad");
        ret.add("atuosto");
        ret.add("YvaltasKitty");
        return ret;
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @NotNull ArrayList<String> getSuggestions(@NotNull GCPCommandStack stack) {

        // Suggest player list
        CommandSourceStack source = stack.getOptions().getCommandSourceStack();
        if (source == null) { return getUbiquitousSuggestions(); }
        ArrayList<String> ret = new ArrayList<>(List.of(source.getServer().getPlayerList().getPlayerNamesArray()));

        // Suggest wildcards
        ret.add("%player%");
        ret.add("@s");

        // Done
        return ret;
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @NotNull GCMPlayerArgument withDefaultValue(@Nullable ServerPlayer def) {
        return (GCMPlayerArgument) super.withDefaultValue(def);
    }
}
