package gunging.ootilities.GungingOotilitiesMod.stats.commands;

import gunging.ootilities.GungingOotilitiesMod.commands.core.building.argument.GCMDoubleArgument;
import gunging.ootilities.GungingOotilitiesMod.commands.core.building.GCMGooMCommandNode;
import gunging.ootilities.GungingOotilitiesMod.commands.core.building.argument.GCMStatArgument;
import gunging.ootilities.GungingOotilitiesMod.commands.core.parsing.GCPCommandStack;
import gunging.ootilities.GungingOotilitiesMod.commands.forge.GCCCommandRegistry;
import gunging.ootilities.GungingOotilitiesMod.commands.forge.argument.GCMPlayerArgument;
import gunging.ootilities.GungingOotilitiesMod.commands.forge.argument.GCPProvidedPlayer;
import gunging.ootilities.GungingOotilitiesMod.commands.friendly.FriendlyFeedbackCategory;
import gunging.ootilities.GungingOotilitiesMod.commands.friendly.FriendlyFeedbackProvider;
import gunging.ootilities.GungingOotilitiesMod.ootilityception.OotilityNumbers;
import net.minecraft.network.chat.Component;
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
        buildHelp("Base Stats", "$rChange the base stats of an entity. ", "The base stats of an entity are unique to itself regardless of its equipment or environment. For players, these changes are retained after death or relog. ");
    }

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
    @Override @Nullable public String execute(@NotNull GCPCommandStack stack, @Nullable FriendlyFeedbackProvider ffp) {

        GCPProvidedPlayer player = (GCPProvidedPlayer) playerArg.read(stack);

        if (player.getParsed() != null) { player.getParsed().sendSystemMessage(Component.literal("EEEOEO"));}

        return "";
    }
}
