package gunging.ootilities.GungingOotilitiesMod.stats.commands;

import gunging.ootilities.GungingOotilitiesMod.commands.core.building.GCMGooMCommandNode;
import gunging.ootilities.GungingOotilitiesMod.commands.core.building.argument.GCMPlayerSlotArgument;
import gunging.ootilities.GungingOotilitiesMod.commands.core.building.argument.GCMStatArgument;
import gunging.ootilities.GungingOotilitiesMod.commands.core.building.argument.GCMStatValueArgument;
import gunging.ootilities.GungingOotilitiesMod.commands.core.parsing.GCPCommandStack;
import gunging.ootilities.GungingOotilitiesMod.commands.forge.argument.GCMPlayerArgument;
import gunging.ootilities.GungingOotilitiesMod.commands.friendly.FriendlyFeedbackProvider;
import gunging.ootilities.GungingOotilitiesMod.exploring.ExplorerManager;
import gunging.ootilities.GungingOotilitiesMod.exploring.ItemStackLocation;
import gunging.ootilities.GungingOotilitiesMod.exploring.players.ISPExplorerStatements;
import gunging.ootilities.GungingOotilitiesMod.exploring.players.ISPPlayerStatement;
import gunging.ootilities.GungingOotilitiesMod.mixininterfaces.WithStatsStack;
import gunging.ootilities.GungingOotilitiesMod.ootilityception.OotilityNumbers;
import gunging.ootilities.GungingOotilitiesMod.stats.core.StatDefinition;
import gunging.ootilities.GungingOotilitiesMod.stats.core.StatInstance;
import gunging.ootilities.GungingOotilitiesMod.stats.core.StatStack;
import gunging.ootilities.GungingOotilitiesMod.stats.core.StatValue;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A command that sets the inherent stats of an item stack
 *
 * @author Gunging
 * @since 1.0.0
 */
public class ItemStatCommand extends GCMGooMCommandNode {

    /**
     * An argument for this commend
     *
     * @since 1.0.0
     */
    @NotNull GCMPlayerArgument playerArg = new GCMPlayerArgument("player", "The player holding the item to modify. ").withDefaultValue(null);

    /**
     * An argument for this commend
     *
     * @since 1.0.0
     */
    @NotNull GCMPlayerSlotArgument slotArg = new GCMPlayerSlotArgument("slot", "The location of the item in the player's inventory. ").withDefaultValue(ISPExplorerStatements.MAINHAND);

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
    @NotNull GCMStatValueArgument valueArg = new GCMStatValueArgument("value", "The value to set this stat to. ").withGreedy(true);

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override public @NotNull String getCommandSubdivision() { return "Item Stats"; }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public ItemStatCommand() {
        super("item", "Set Item Stat", "Sets the stats of an item. ");

        // Build arguments (in order)
        addArgument(playerArg);
        addArgument(slotArg);
        addArgument(statArg);
        addArgument(valueArg);

        // Build /help
        buildHelp("$rChange the stats of an item. ", "This command does not simply 'set' the base stats of an item, rather it adds a modifier to the base so that the resulting number is that which you specified. For vanilla items, the base value is nonexistent so this does just 'set' it tho. ");
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override @Nullable
    public String execute(@NotNull GCPCommandStack stack, @Nullable FriendlyFeedbackProvider ffp) {

        // Read the arguments (in order)
        ServerPlayer player = playerArg.supplied(stack, stack.getOptions().getSenderPlayer(), ffp);
        ISPPlayerStatement slot = slotArg.expected(stack, ffp);
        StatDefinition stat = statArg.expected(stack, ffp);
        String value = valueArg.expected(stack, ffp);

        // Cancel in the case of a failure
        if (stack.isFailure()) { return null; }

        // Find the items
        HashMap<ItemStackLocation, ItemStack> items = ExplorerManager.realize(slot, player);
        if (items.isEmpty()) {
            FriendlyFeedbackProvider.logSuccess(ffp, "No items found in '$r{0}$b' of '$f{1}$b'. ", slot.toString(), player.getScoreboardName());
            return null;
        }

        // Modify every item
        ArrayList<String> successes = new ArrayList<>();
        for (Map.Entry<ItemStackLocation, ItemStack> pair : items.entrySet()) {
            ItemStackLocation location = pair.getKey();
            ItemStack item = pair.getValue();

            // Perform operation
            StatStack itemStats = ((WithStatsStack) (Object) item).gungingoom$getStatStack();
            StatInstance priorInherent = itemStats.getInherentStats().get(stat.getDefinitionID());
            StatValue original = priorInherent == null ? stat.getDefault() : priorInherent.getValue();
            StatValue result = stat.operation(original, value, ffp);

            // No changes?
            if (result.equals(original)) {
                FriendlyFeedbackProvider.logSuccess(ffp, "Stat $u{1}$b of $r{0}$b did not change from $f{2}$b. ", item.getDisplayName().getString(), stat.getDefinitionID(), result.toString());

            // Success
            } else {
                successes.add(location.getStatement().toString());
                itemStats.setStat(stat, result);
                itemStats.recalculateStatTotals();
                FriendlyFeedbackProvider.logSuccess(ffp, "Stat $u{1}$b of $r{0}$b set to $s{2}$b. ", item.getDisplayName().getString(), stat.getDefinitionID(), result.toString());

            }
        }

        // Return the list of slots that succeeded
        return successes.isEmpty() ? null : OotilityNumbers.collapseList(successes, ";");
    }
}
