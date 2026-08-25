package gunging.ootilities.GungingOotilitiesMod.stats.commands;

import gunging.ootilities.GungingOotilitiesMod.commands.core.building.argument.GCMDoubleArgument;
import gunging.ootilities.GungingOotilitiesMod.commands.core.building.GCMGooMCommandNode;
import gunging.ootilities.GungingOotilitiesMod.commands.core.building.argument.GCMStatArgument;
import gunging.ootilities.GungingOotilitiesMod.commands.core.parsing.GCPCommandStack;
import gunging.ootilities.GungingOotilitiesMod.commands.forge.argument.GCMPlayerArgument;
import gunging.ootilities.GungingOotilitiesMod.commands.friendly.FriendlyFeedbackProvider;
import gunging.ootilities.GungingOotilitiesMod.stats.core.StatDefinition;
import net.minecraft.server.level.ServerPlayer;
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
    @NotNull GCMPlayerArgument playerArg = new GCMPlayerArgument("player", "The player whose stats to modify. ").withDefaultValue(null);

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

        // Build arguments (in order)
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

        // Read the arguments (in order)
        ServerPlayer player = playerArg.supplied(stack, stack.getOptions().getSenderPlayer(), ffp);
        StatDefinition<?> stat = statArg.expected(stack, ffp);
        Double value = valueArg.expected(stack, ffp);

        // Cancel in the case of a failure
        if (stack.isFailure()) { return null; }

        FriendlyFeedbackProvider.logSuccess(ffp, "Player $r{0}$b gained $u{1}$b : $s{2}", player.getScoreboardName(), stat.getDefinitionID(), value.toString());
        return "";
    }
}
