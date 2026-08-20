package gunging.ootilities.GungingOotilitiesMod.stats.commands;

import gunging.ootilities.GungingOotilitiesMod.commands.core.building.argument.GCMDoubleArgument;
import gunging.ootilities.GungingOotilitiesMod.commands.core.building.GCMGooMCommandNode;
import gunging.ootilities.GungingOotilitiesMod.commands.core.building.argument.GCMStatArgument;
import gunging.ootilities.GungingOotilitiesMod.commands.core.parsing.GCPCommandStack;
import gunging.ootilities.GungingOotilitiesMod.commands.core.parsing.argument.GCPProvidedDouble;
import gunging.ootilities.GungingOotilitiesMod.commands.core.parsing.argument.GCPProvidedStat;
import gunging.ootilities.GungingOotilitiesMod.commands.forge.argument.GCMPlayerArgument;
import gunging.ootilities.GungingOotilitiesMod.commands.forge.argument.GCPProvidedPlayer;
import gunging.ootilities.GungingOotilitiesMod.commands.friendly.FriendlyFeedbackProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A command that sets an inherent stat in the specified
 * player, it persists across death and world closing.
 *
 * @author Gunging
 * @since 1.0.0
 */
public class InherentStatCommand extends GCMGooMCommandNode {

    /**
     * An argument for this commend
     *
     * @since 1.0.0
     */
    @NotNull GCMPlayerArgument playerArg = (GCMPlayerArgument) new GCMPlayerArgument("player", "The player whose stats to modify. ").withDefaultValue(null);

    /**
     * An argument for this commend
     *
     * @since 1.0.0
     */
    @NotNull GCMStatArgument statArg = new GCMStatArgument("stat", "The stat to modify. ");

    /**
     * An argument for this commend
     *
     * @since 1.0.0
     */
    @NotNull GCMDoubleArgument valueArg = new GCMDoubleArgument("value", "The value to set this stat to. ");

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override public @NotNull String getCommandSubdivision() { return "Base Stats"; }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public InherentStatCommand() {
        super("base", "Set Base Stat", "Sets the base value of this stat for a specific entity. ");

        // Build arguments
        addArgument(playerArg);
        addArgument(statArg);
        addArgument(valueArg);

        // Build /help
        buildHelp("$rChange the base stats of an entity. ", "The base stats of an entity are unique to itself regardless of its equipment or environment. For players, these changes are retained after death or relog. ");
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override @Nullable public String execute(@NotNull GCPCommandStack stack, @Nullable FriendlyFeedbackProvider ffp) {
        FriendlyFeedbackProvider.logInfo(ffp, "INHERENT Start");

        GCPProvidedPlayer player = (GCPProvidedPlayer) playerArg.read(stack);
        if (player.getParsingError() != null) { FriendlyFeedbackProvider.logError(ffp, "INHERENT $fPlayer {0}", player.getParsingError()); }
        else if (player.isDefaulted() && stack.getOptions().getCommandSourceStack() != null) { player.setParsed(stack.getOptions().getCommandSourceStack().getPlayer()); }
        if (player.getParsed() != null) { FriendlyFeedbackProvider.logInfo(ffp, "INHERENT Player $s{0}$b! ", player.getParsed().getScoreboardName()); }

        GCPProvidedStat stat = (GCPProvidedStat) statArg.read(stack);
        if (stat.getParsingError() != null) { FriendlyFeedbackProvider.logError(ffp, "INHERENT $fStat {0}", stat.getParsingError()); }
        if (stat.getParsed() != null) { FriendlyFeedbackProvider.logInfo(ffp, "INHERENT Stat $s{0}$b! ", stat.getParsed().getDefinitionID()); }

        GCPProvidedDouble value = (GCPProvidedDouble) valueArg.read(stack);
        if (value.getParsingError() != null) { FriendlyFeedbackProvider.logError(ffp, "INHERENT $fValue {0}", value.getParsingError()); }
        if (value.getParsed() != null) { FriendlyFeedbackProvider.logInfo(ffp, "INHERENT Value $s{0}$b! ", String.valueOf(value.getParsed())); }

        FriendlyFeedbackProvider.logInfo(ffp, "INHERENT End");
        return "";
    }
}
