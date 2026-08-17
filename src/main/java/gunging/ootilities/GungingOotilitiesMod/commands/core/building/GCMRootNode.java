package gunging.ootilities.GungingOotilitiesMod.commands.core.building;

import org.jetbrains.annotations.NotNull;

/**
 * A node with no parent, the source of a command tree.
 *
 * @author Gunging
 * @since 1.0.0
 */
public class GCMRootNode extends GCMBranchNode {

    /**
     * @param keyword The argument in the command line that represents this node
     *
     * @author Gunging
     * @since 1.0.0
     */
    public GCMRootNode(@NotNull String keyword) {
        super(keyword);
        setParent(this);
    }
}
