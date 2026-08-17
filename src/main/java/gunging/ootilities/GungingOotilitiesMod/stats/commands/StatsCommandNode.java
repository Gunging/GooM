package gunging.ootilities.GungingOotilitiesMod.stats.commands;

import gunging.ootilities.GungingOotilitiesMod.commands.core.building.GCMBranchNode;
import gunging.ootilities.GungingOotilitiesMod.commands.forge.GCCCommandRegistry;
import gunging.ootilities.GungingOotilitiesMod.commands.friendly.FriendlyFeedbackCategory;
import gunging.ootilities.GungingOotilitiesMod.ootilityception.OotilityNumbers;

/**
 * The branching root of stat-related commands
 *
 * @author Gunging
 * @since 1.0.0
 */
public class StatsCommandNode extends GCMBranchNode {

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public StatsCommandNode() {
        super("stats");

        // Build tree
        addNode(new InherentStatCommand());

        // Build /help
        getHelp().activatePrefix(true, "Stats");
        getHelp().log(FriendlyFeedbackCategory.INFORMATION, "$rPerform operations with stats");
        getHelp().activatePrefix(false, "Stats");
        for (String helpLine : OotilityNumbers.chop("The GooM stats system is an RPG engine, similar to vanilla attributes but peak.", GCCCommandRegistry.HELP_PARAGRAPH_WIDTH, "$b")) {
            getHelp().log(FriendlyFeedbackCategory.INFORMATION, helpLine);
        }
    }
}
