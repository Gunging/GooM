package gunging.ootilities.GungingOotilitiesMod.commands.core.building;

import gunging.ootilities.GungingOotilitiesMod.commands.friendly.FriendlyFeedbackPalette;
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
        this.parent = this;
    }

    /**
     * @param keyword The argument in the command line that represents this node
     *
     * @author Gunging
     * @since 1.0.0
     */
    public GCMRootNode(@NotNull String keyword, @NotNull FriendlyFeedbackPalette palette) {
        super(keyword);
        this.parent = this;
        this.palette = palette;
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public void setParent(@NotNull GCMBranchNode parent) {  }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public void addNode(@NotNull GCMNode node) {
        super.addNode(node);
        node.setPalette(getPalette());
    }
}
