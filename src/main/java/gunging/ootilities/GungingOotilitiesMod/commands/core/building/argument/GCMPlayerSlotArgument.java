package gunging.ootilities.GungingOotilitiesMod.commands.core.building.argument;

import gunging.ootilities.GungingOotilitiesMod.GungingOotilitiesMod;
import gunging.ootilities.GungingOotilitiesMod.commands.core.building.GCMStandaloneArgument;
import gunging.ootilities.GungingOotilitiesMod.commands.core.parsing.argument.GCPProvidedPlayerSlot;
import gunging.ootilities.GungingOotilitiesMod.exploring.players.ISPExplorerStatements;
import gunging.ootilities.GungingOotilitiesMod.exploring.players.ISPPlayerStatement;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

/**
 * An argument that expects a slot in a player's inventory
 *
 * @author Gunging
 * @since 1.0.0
 */
public class GCMPlayerSlotArgument extends GCMStandaloneArgument<ISPPlayerStatement> {

    /**
     * @param argumentName        The name of this argument
     * @param argumentDescription A short description of this argument
     *
     * @author Gunging
     * @since 1.0.0
     */
    public GCMPlayerSlotArgument(@NotNull String argumentName, @NotNull String argumentDescription) {
        super(argumentName, argumentDescription);
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override public @NotNull GCPProvidedPlayerSlot provide(@NotNull String explicit) {
        return new GCPProvidedPlayerSlot(explicit);
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @NotNull ArrayList<String> getUbiquitousSuggestions() {

        // Build basic examples
        ArrayList<ISPPlayerStatement> suggestions = new ArrayList<>();
        suggestions.add(ISPExplorerStatements.MAINHAND);
        suggestions.add(ISPExplorerStatements.OFFHAND);
        suggestions.add(ISPExplorerStatements.HEAD);
        suggestions.add(ISPExplorerStatements.CHEST);
        suggestions.add(ISPExplorerStatements.LEGS);
        suggestions.add(ISPExplorerStatements.FEET);
        suggestions.add(ISPExplorerStatements.ARMOR);
        suggestions.add(ISPExplorerStatements.CURSOR);
        suggestions.add(ISPExplorerStatements.MAIN);
        suggestions.add(ISPExplorerStatements.STASH);
        suggestions.add(ISPExplorerStatements.HANDS);
        suggestions.add(ISPExplorerStatements.HOTBAR);
        suggestions.add(ISPExplorerStatements.ALL);

        // Include basic examples
        ArrayList<String> ret = new ArrayList<>();
        ret.add("5");
        ret.add("0-4");
        for (ISPPlayerStatement slot : suggestions) { ret.add(slot.getStatementName().getPath()); }

        // Include all registered
        for (ResourceLocation regSlot : GungingOotilitiesMod.getInstance().getExplorer().getRegisteredStatements().keySet()) {
            ret.add(regSlot.toString());
        }

        // Done
        return ret;
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @NotNull GCMPlayerSlotArgument withDefaultValue(@Nullable ISPPlayerStatement def) {
        return (GCMPlayerSlotArgument) super.withDefaultValue(def);
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @NotNull GCMPlayerSlotArgument withGreedy(boolean greed) {
        return (GCMPlayerSlotArgument) super.withGreedy(greed);
    }
}
