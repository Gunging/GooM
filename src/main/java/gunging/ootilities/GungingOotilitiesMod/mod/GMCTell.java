package gunging.ootilities.GungingOotilitiesMod.mod;

import gunging.ootilities.GungingOotilitiesMod.commands.FFPGooM;
import gunging.ootilities.GungingOotilitiesMod.commands.core.building.GCMGooMCommandNode;
import gunging.ootilities.GungingOotilitiesMod.commands.core.building.argument.GCMStringArgument;
import gunging.ootilities.GungingOotilitiesMod.commands.core.parsing.GCPCommandStack;
import gunging.ootilities.GungingOotilitiesMod.commands.forge.argument.GCMPlayerArgument;
import gunging.ootilities.GungingOotilitiesMod.commands.friendly.FriendlyFeedbackCategory;
import gunging.ootilities.GungingOotilitiesMod.commands.friendly.FriendlyFeedbackProvider;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A foundational command that displays a line of text to somebody
 *
 * @author Gunging
 * @since 1.0.0
 */
public class GMCTell extends GCMGooMCommandNode {

    /**
     * An argument for this commend
     *
     * @since 1.0.0
     */
    @NotNull GCMPlayerArgument playerArg = new GCMPlayerArgument("player", "The player to whom display this message. ");

    /**
     * An argument for this commend
     *
     * @since 1.0.0
     */
    @NotNull GCMStringArgument messageArg = new GCMStringArgument("msg", "The line of text that will be displayed. ").withGreedy(true);

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override public @NotNull String getCommandSubdivision() { return "Tell"; }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public GMCTell() {
        super("tell", "Tell", "Displays a chat message to somebody. ");

        // Build arguments (in order)
        addArgument(playerArg);
        addArgument(messageArg);

        // Build /help
        buildHelp("$rDisplay a chat message to someone. ", "This command will simply and merely print out text, parsing color codes and other placeholders of course~ ");
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override @Nullable
    public String execute(@NotNull GCPCommandStack stack, @Nullable FriendlyFeedbackProvider ffp) {

        // Read the arguments (in order)
        ServerPlayer player = playerArg.supplied(stack, stack.getOptions().getSenderPlayer(), ffp);
        String message = messageArg.expected(stack, ffp);

        // Cancel in the case of a failure
        if (stack.isFailure()) {return null;}

        // Send message, parsed
        FriendlyFeedbackProvider parser = new FriendlyFeedbackProvider(new FFPGooM());
        if (ffp != null) { parser.setPalette(ffp.getPalette()); }
        parser.log(FriendlyFeedbackCategory.INFORMATION, message);
        parser.sendAllTo(player::sendSystemMessage);

        // Return result
        FriendlyFeedbackProvider.logSuccess(ffp, "Told $e{0}$b:$r {1}", player.getScoreboardName(), message);
        return "";
    }
}