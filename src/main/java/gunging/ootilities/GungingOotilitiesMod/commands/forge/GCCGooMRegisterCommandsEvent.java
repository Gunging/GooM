package gunging.ootilities.GungingOotilitiesMod.commands.forge;

import gunging.ootilities.GungingOotilitiesMod.commands.core.building.GCMBranchNode;
import gunging.ootilities.GungingOotilitiesMod.commands.core.building.GCMRootNode;
import net.minecraftforge.eventbus.api.Event;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

/**
 * An event that collects root nodes to register as Forge Commands
 *
 * @author Gunging
 * @since 1.0.0
 */
public class GCCGooMRegisterCommandsEvent extends Event {

    /**
     * The collection of branching command trees to register
     *
     * @since 1.0.0
     */
    @NotNull final HashMap<String, GCMRootNode> commandTrees = new HashMap<>();

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull HashMap<String, GCMRootNode> getCommandTrees() { return commandTrees; }

    /**
     * @param node Root Node of the built command tree to include
     *
     * @author Gunging
     * @since 1.0.0
     */
    public void addRoot(@NotNull GCMRootNode node) { commandTrees.put(node.getKeyword(), node); }
}
