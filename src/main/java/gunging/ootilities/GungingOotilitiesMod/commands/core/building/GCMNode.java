package gunging.ootilities.GungingOotilitiesMod.commands.core.building;

import gunging.ootilities.GungingOotilitiesMod.GungingOotilitiesMod;
import gunging.ootilities.GungingOotilitiesMod.commands.FFPGooM;
import gunging.ootilities.GungingOotilitiesMod.commands.core.parsing.GCPContextOptions;
import gunging.ootilities.GungingOotilitiesMod.commands.friendly.FriendlyComponentReceiver;
import gunging.ootilities.GungingOotilitiesMod.commands.friendly.FriendlyFeedbackPalette;
import gunging.ootilities.GungingOotilitiesMod.commands.friendly.FriendlyFeedbackProvider;
import gunging.ootilities.GungingOotilitiesMod.commands.friendly.FriendlyStringReceiver;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Nodes are the command tree, and are usually followed by the
 * arguments that are the payload of the operation.
 * <br>
 * <code>/goom goop testInventory %player% %slot% %item%</code>
 * <br>
 * <code>/(root node) (sub node) (command node) (argument) (argument) (argument)</code>
 *
 * @author Gunging
 * @since 1.0.0
 */
public abstract class GCMNode {

    /**
     * The argument in the command line that represents this node
     *
     * @since 1.0.0
     */
    @NotNull final String keyword;

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public String getKeyword() { return keyword; }

    /**
     * The node upon which this node is based. For
     * the case of root nodes, they are their own
     * parent.
     *
     * @since 1.0.0
     */
    GCMBranchNode parent;

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public GCMBranchNode getParent() { return parent; }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public void setParent(@NotNull GCMBranchNode parent) { this.parent = parent; }

    /**
     * @return The root node at the start of this tree.
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public GCMRootNode getRoot() {
        if (isRoot()) { return (GCMRootNode) parent; }
        return getParent().getRoot();
    }

    /**
     * @return If this node is a root node (defined by its @NotNull parent being itself)
     *
     * @author Gunging
     * @since 1.0.0
     */
    public boolean isRoot() { return this == parent; }

    /**
     * @param keyword The argument in the command line that represents this node
     *
     * @author Gunging
     * @since 1.0.0
     */
    public GCMNode(@NotNull String keyword) { this.keyword = keyword; }

    /**
     * The messages displayed as "help" when this is called with no arguments
     *
     * @since 1.0.0
     */
    @Nullable FriendlyFeedbackProvider help;

    /**
     * @return The chat messages displayed when calling this node with no arguments
     *
     * @author Gunging
     * @since 1.0.0
     */
    public @NotNull FriendlyFeedbackProvider getHelp() {
        if (help == null) { help = newFeedbackProvider(); }
        return help;
    }

    /**
     * @param args The arguments that follow, where the ZEROTH argument is
     *             this node itself. Only command nodes will get more than
     *             2 elements in this array, whereas all other nodes have
     *             two elements where the second argument is the following
     *             node as to tab complete the suggestions for it.
     *
     * @return The inputs to suggest based on the input already written
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public abstract ArrayList<String> tabComplete(@NotNull GCPContextOptions source, @NotNull String[] args);

    /**
     * @param args The arguments that follow, where the ZEROTH argument is
     *             this node itself. Only command nodes will get more than
     *             2 elements in this array, whereas all other nodes have
     *             two elements where the second argument is the following
     *             node as to tab complete the suggestions for it.
     *
     * @return An array containing arguments such that the ZEROTH argument
     *         is the root node's keyword.
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public String[] argsToFullPath(@NotNull String[] args) {

        // By definition, args' ZEROTH argument is myself
        if (isRoot()) { return args; }
        ArrayList<String> ret = new ArrayList<>();

        // Then, we must append every parent node
        int contrivance = 0;
        GCMNode asParent = this;
        do {

            // Insert keyword of the parent at zeroth position
            asParent = asParent.getParent();
            ret.add(0, asParent.getKeyword());

            contrivance++;
        } while (!asParent.isRoot() && contrivance < 500);

        // Error that should never reasonably happen
        if (contrivance > 499) {
            GungingOotilitiesMod.Log("GCMNode.fullPath(String[]) &c ERROR: Contrivance maxed for command arguments for node '" + getKeyword() + "' with root '" + getRoot().getKeyword() + "'");
        }

        // Then append every argument of the input
        ret.addAll(Arrays.asList(args));

        // Convert to array
        return ret.toArray(new String[0]);
    }

    /**
     * @return Builds a new friendly feedback provider with the
     *         correct palette and style to execute this command
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public FriendlyFeedbackProvider newFeedbackProvider() {
        FriendlyFeedbackProvider ret = new FriendlyFeedbackProvider(getPalette());
        ret.activatePrefix(true, null);
        return ret;
    }

    /**
     * @param helper The one who will receive the /help
     *
     * @author Gunging
     * @since 1.0.0
     */
    public void help(@NotNull FriendlyComponentReceiver helper) { getHelp().sendAllTo(helper); }

    /**
     * @param helper The one who will receive the /help
     *
     * @author Gunging
     * @since 1.0.0
     */
    public void helpConsole(@NotNull FriendlyStringReceiver helper) { getHelp().sendAllToConsole(helper); }

    /**
     * The Friendly Feedback Palette for this tree
     *
     * @since 1.0.0
     */
    @NotNull FriendlyFeedbackPalette palette = new FFPGooM();

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public @NotNull FriendlyFeedbackPalette getPalette() { return palette; }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public void setPalette(@NotNull FriendlyFeedbackPalette palette) {
        this.palette = palette;
        getHelp().setPalette(palette);
    }
}
