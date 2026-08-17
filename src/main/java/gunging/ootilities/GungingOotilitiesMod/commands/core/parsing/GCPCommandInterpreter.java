package gunging.ootilities.GungingOotilitiesMod.commands.core.parsing;

import gunging.ootilities.GungingOotilitiesMod.commands.core.building.GCMCommandNode;
import gunging.ootilities.GungingOotilitiesMod.commands.core.building.GCMNode;
import gunging.ootilities.GungingOotilitiesMod.commands.core.building.GCMBranchNode;
import gunging.ootilities.GungingOotilitiesMod.commands.core.building.GCMRootNode;
import gunging.ootilities.GungingOotilitiesMod.ootilityception.OotilityNumbers;
import org.jetbrains.annotations.NotNull;

/**
 * A class that process a bunch of strings into
 * a cohesive argument stack associated with
 * a command.
 *
 * @author Gunging
 * @since 1.0.0
 */
public class GCPCommandInterpreter {

    /**
     * If context is encoded in this string, it will be
     * parsed and removed from the arguments. Note that
     * it is expected that placeholders parse before this.
     *
     * @param command A command in the form of a single string.
     *
     * @return The same command but in the form of an Argument Stack
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public GCPArgumentStack transform(@NotNull String command) {
        if (!command.contains(" ")) { return new GCPArgumentStack(command); }
        return transform(command.split(" "));
    }

    /**
     * If context is encoded in this array, it will be
     * parsed and removed from the arguments. Placeholders
     * are expected to already have parsed.
     *
     * @param command A command in the form of a string array. The ZEROTH
     *                element is expected to be the keyword for the root
     *                command node.
     *
     * @return The same command but in the form of an Argument Stack
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public GCPArgumentStack transform(@NotNull String... command) {

        /*
         * If context will be included in a command string, it must be in the first
         * "argument" after the root node. For example:
         *
         * /goom [context] goop command yay args
         */
        if (command.length > 2) {
            if (command[1].startsWith("[")) {
                GCPContextOptions opts = new GCPContextOptions(OotilityNumbers.unwrapFromBrackets(command[1], "[", "]"));

                // Crop context and transform
                String[] noContext = new String[command.length - 1];
                noContext[0] = command[0];
                System.arraycopy(command, 2, noContext, 1, noContext.length - 1);
                return new GCPArgumentStack(noContext).withOptions(opts);
            }
        }

        // Transform normally
        return new GCPArgumentStack(command);
    }

    /**
     * Based on all arguments, looks through the node structure looking
     * for the latest node this encodes for. Especial attention given
     * if it is a Command Node.
     *
     * @param root The node tree to search. It is guaranteed that its keyword
     *             is the ZEROTH element in the Argument Stack.
     *
     * @param unparsed A raw Argument Stack that has not been interpreted
     *
     * @return A more produced argument stack, one with search results included
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public GCPSearchedStack search(@NotNull GCMRootNode root, @NotNull GCPArgumentStack unparsed) {

        /*
         * Assuming that the context has been removed, the
         * subnode keywords should be matching the arguments
         * exactly.
         */
        int contrivance = 1;
        boolean succeeding = true;
        GCMBranchNode node = root;
        GCMCommandNode fruit = null;
        while (succeeding && contrivance < unparsed.arguments.length) {

            // We are looking for this subnode right now
            GCPProvidedArgument<?> subNodeArg = unparsed.arguments[contrivance];
            GCMNode subnode = node.getSubnodes().get(subNodeArg.explicit);

            // This subnode does not exist. Failure
            if (subnode == null) {
                succeeding = false;

            // This subnode was found
            } else {

                // Subnode found, dive deeper into the tree
                if (subnode instanceof GCMBranchNode) {
                    node = (GCMBranchNode) subnode;
                    contrivance ++;

                // Branch end reached, completion
                } else if (subnode instanceof GCMCommandNode) {
                    fruit = (GCMCommandNode) subnode;
                    break;

                // Should never happen.
                } else { succeeding = false; }
            }
        }

        // That is the result of parsing the command structure of this stack
        return (GCPSearchedStack) new GCPSearchedStack(
                unparsed,
                root,
                fruit != null ? fruit : node,
                fruit,
                succeeding,
                fruit == null ? null : unparsed.after(contrivance)).withOptions(unparsed.options);
    }

    /**
     * The crux of this system, this method will find the command
     * among the subnode tree as well as check its expected arguments
     *
     * @param unparsed A raw Argument Stack that has not been interpreted
     *
     * @return The same argument stack, but now properly processed
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public GCPArgumentStack interpret(@NotNull GCMRootNode root, @NotNull GCPArgumentStack unparsed) {

        // Do our best to interpret the desired command from this stack
        GCPSearchedStack searched = search(root, unparsed);
        if (!searched.foundCommand()) {

            // If a command was not found, this is the end of interpreting
            return searched; }

        /*
         * The command was found, therefore we now process its arguments.
         * At this point, the nodes as much as the arguments are simply
         * a bunch of "explicit" strings provided by the user.
         *
         * The job here is to assign each explicit argument to each GCM
         * Expected Argument of the command, without parsing it yet in
         * case that this is just a Tab Complete operation, and we don't
         * need to parse it all.
         */
        return new GCPCommandStack(searched);
    }
}
