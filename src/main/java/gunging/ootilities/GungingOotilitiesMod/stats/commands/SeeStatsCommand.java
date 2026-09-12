package gunging.ootilities.GungingOotilitiesMod.stats.commands;

import gunging.ootilities.GungingOotilitiesMod.commands.core.building.GCMGooMCommandNode;
import gunging.ootilities.GungingOotilitiesMod.commands.core.building.argument.GCMPlayerSlotArgument;
import gunging.ootilities.GungingOotilitiesMod.commands.core.parsing.GCPCommandStack;
import gunging.ootilities.GungingOotilitiesMod.commands.forge.argument.GCMPlayerArgument;
import gunging.ootilities.GungingOotilitiesMod.commands.friendly.FriendlyFeedbackProvider;
import gunging.ootilities.GungingOotilitiesMod.exploring.ExplorerManager;
import gunging.ootilities.GungingOotilitiesMod.exploring.ItemStackLocation;
import gunging.ootilities.GungingOotilitiesMod.exploring.players.ISPPlayerStatement;
import gunging.ootilities.GungingOotilitiesMod.mixininterfaces.WithStatsStack;
import gunging.ootilities.GungingOotilitiesMod.ootilityception.OotilityNumbers;
import gunging.ootilities.GungingOotilitiesMod.stats.core.*;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Prints into the chat the list of stats held in this player or item
 *
 * @author Gunging
 * @since 1.0.0
 */
public class SeeStatsCommand extends GCMGooMCommandNode {

    /**
     * An argument for this commend
     *
     * @since 1.0.0
     */
    @NotNull GCMPlayerArgument playerArg = new GCMPlayerArgument("player", "The player to check, or who holds the item. ").withDefaultValue(null);

    /**
     * An argument for this commend
     *
     * @since 1.0.0
     */
    @NotNull GCMPlayerSlotArgument slotArg = new GCMPlayerSlotArgument("slot", "The location of the item in the player's inventory. ").withDefaultValue(null);

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override public @NotNull String getCommandSubdivision() { return "Item Stats"; }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public SeeStatsCommand() {
        super("see", "See Stats", "Prints the stats of a player or their item. ");

        // Build arguments (in order)
        addArgument(playerArg);
        addArgument(slotArg);

        // Build /help
        buildHelp("$rPrint the stats of player or item. ", "Show in the chat the totals of every GooM stat of this player or item. It also shows how much is the base of this stat for that player or item, and the difference is presumably modifiers or equipment. ");
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override @Nullable
    public String execute(@NotNull GCPCommandStack stack, @Nullable FriendlyFeedbackProvider ffp) {

        // Read the arguments (in order)
        ServerPlayer player = playerArg.supplied(stack, stack.getOptions().getSenderPlayer(), ffp);
        ISPPlayerStatement slot = slotArg.defaulted(stack, ffp);

        // Cancel in the case of a failure
        if (stack.isFailure()) { return null; }

        // When checking a player
        if (slot == null) {
            FriendlyFeedbackProvider.logInfo(ffp, "Stats of $r{0}$b: ", player.getScoreboardName());
            printStatStack(((WithStatsStack) player).gungingoom$getStatStack(), ffp);
            return "";
        }

        // Find the items
        HashMap<ItemStackLocation, ItemStack> items = ExplorerManager.realize(slot, player);
        if (items.isEmpty()) {
            FriendlyFeedbackProvider.logSuccess(ffp, "No items found in '$f{0}$b' of '$r{1}$b'. ", slot.getStatementName().toString(), player.getScoreboardName());
            return null;
        }

        // Modify every item
        ArrayList<String> successes = new ArrayList<>();
        FriendlyFeedbackProvider.logInfo(ffp, "Stats of $r{0}$b's selected items: ", player.getScoreboardName());
        for (Map.Entry<ItemStackLocation, ItemStack> pair : items.entrySet()) {
            ItemStackLocation location = pair.getKey();
            ItemStack item = pair.getValue();

            // Record Success
            successes.add(location.getStatement().getStatementName().toString());
            FriendlyFeedbackProvider.logInfo(ffp, "Item $r{0}$b: ", item.getDisplayName().getString());

            // Perform operation
            StatStack itemStats = ((WithStatsStack) (Object) item).gungingoom$getStatStack();
            printStatStack(itemStats, ffp);
        }

        // Return the list of slots that succeeded
        return successes.isEmpty() ? null : OotilityNumbers.collapseList(successes, ";");
    }

    /**
     * Prints the contents of this stat stack onto this FFP
     * <br>
     * Will use the {@link gunging.ootilities.GungingOotilitiesMod.commands.friendly.FriendlyFeedbackCategory#INFORMATION} category
     *
     * @param stats The stats to print
     * @param ffp Friendly Feedback Provider to include this to
     *
     * @author Gunging
     * @since 1.0.0
     */
    public static void printStatStack(@NotNull StatStacked stats, @Nullable FriendlyFeedbackProvider ffp) {
        if (ffp == null) { return; }

        // Append every stat
        for (StatInstance<?> stat : stats.getRefreshedStatTotals().values()) {

            StatInstance<?> inherent = null;
            if (stats instanceof StatStackable) {
                StatStackable asSource = (StatStackable) stats;
                inherent = asSource.getInherentStats().get(stat.getDefinition().getDefinitionID()); }

            // No inherent? Normal append
            if (inherent == null) {
                FriendlyFeedbackProvider.logInfo(ffp, "  {0}$b:$r {1}", stat.getDefinition().getDisplayName(), stat.getValue().toString());

            // With inherent? Append base
            } else {
                FriendlyFeedbackProvider.logInfo(ffp, "  {0}$b:$r {1} $e(Base: $i{2}$3)", stat.getDefinition().getDisplayName(), stat.getValue().toString(), inherent.getValue().toString());
            }
        }
    }

}