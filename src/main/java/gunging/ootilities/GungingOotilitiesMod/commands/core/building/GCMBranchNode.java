package gunging.ootilities.GungingOotilitiesMod.commands.core.building;

import gunging.ootilities.GungingOotilitiesMod.commands.FFPGooM;
import gunging.ootilities.GungingOotilitiesMod.commands.core.parsing.GCPContextOptions;
import gunging.ootilities.GungingOotilitiesMod.commands.friendly.FriendlyFeedbackProvider;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A node in the command tree capable of branching out
 *
 * @author Gunging
 * @since 1.0.0
 */
public class GCMBranchNode extends GCMNode {

    /**
     * @param keyword The argument in the command line that represents this node
     * @author Gunging
     * @since 1.0.0
     */
    public GCMBranchNode(@NotNull String keyword) { super(keyword); }

    /**
     * The branches of this command tree. The root node
     * should never be a command node, having no node tree
     * is terrible design after all.
     *
     * @since 1.0.0
     */
    @NotNull HashMap<String, GCMNode> subnodes = new HashMap<>();

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public HashMap<String, GCMNode> getSubnodes() { return subnodes; }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public void addNode(@NotNull GCMNode node) { subnodes.put(node.getKeyword(), node); node.setParent(this); }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override public void setParent(@NotNull GCMBranchNode parent) {
        super.setParent(parent);
        getHelp().setPalette(parent.getRoot().getHelp().getPalette());
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override public @NotNull ArrayList<String> tabComplete(@NotNull GCPContextOptions source, @NotNull String[] args) {
        if (args.length < 2) { return new ArrayList<>(); }

        /*
         * No point in checking the ZEROTH argument, after all
         * it is guaranteed to be identical to the keyword
         */

        String arg = args[1];
        ArrayList<String> ret = new ArrayList<>();
        ArrayList<String> ret2 = new ArrayList<>();
        for (String subkey : subnodes.keySet()) {

            // High priority to those that start with
            if (subkey.toLowerCase().startsWith(arg)) {
                ret.add(subkey);

            // Lower priority but still suggested when contains
            } else if (subkey.toLowerCase().contains(arg)) {
                ret2.add(subkey);
            }
        }

        // That's it
        ret.addAll(ret2);
        return ret;
    }
}
