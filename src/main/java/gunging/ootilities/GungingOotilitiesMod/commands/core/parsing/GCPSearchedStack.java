package gunging.ootilities.GungingOotilitiesMod.commands.core.parsing;

import gunging.ootilities.GungingOotilitiesMod.commands.core.building.GCMCommandNode;
import gunging.ootilities.GungingOotilitiesMod.commands.core.building.GCMNode;
import gunging.ootilities.GungingOotilitiesMod.commands.core.building.GCMBranchNode;
import gunging.ootilities.GungingOotilitiesMod.commands.core.building.GCMRootNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * An argument stack in the middle of processing, a step
 * where it has done its best to find the command it represents
 * though it may not have yet succeeded.
 *
 * @author Gunging
 * @since 1.0.0
 */
public class GCPSearchedStack extends GCPArgumentStack {

    /**
     * @param explicit The
     * @param root The identified root node
     * @param latestNode The latest identified node
     * @param command The identified command node
     *
     * @author Gunging
     * @since 1.0.0
     */
    public GCPSearchedStack(@NotNull GCPArgumentStack explicit,
                            @Nullable GCMRootNode root,
                            @Nullable GCMNode latestNode,
                            @Nullable GCMCommandNode command,
                            boolean fullNodeSuccess,
                            @Nullable GCPArgumentStack commandArguments) {
        super(explicit);
        this.root = root;
        this.command = command;
        this.latestNode = latestNode;
        this.fullNodeSuccess = fullNodeSuccess;
        this.commandArguments = commandArguments;
    }

    /**
     * Node upon which argument stack is based
     *
     * @since 1.0.0
     */
    @Nullable final GCMRootNode root;

    /**
     * Identified command for this argument stack
     *
     * @since 1.0.0
     */
    @Nullable final GCMCommandNode command;

    /**
     * Deepest node that was identified, usually the command node, but just
     * as important when sending an incomplete or erroneous command.
     *
     * @since 1.0.0
     */
    @Nullable final GCMNode latestNode;

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public @Nullable GCPArgumentStack getCommandArguments() { return commandArguments; }

    /**
     * Removing the node tree, only the arguments passed to
     * the command (only exists if a command was found). These
     * arguments do not include the command node keyword, so
     * the ZEROTH argument is the first actual argument for
     * the command.
     *
     * @since 1.0.0
     */
    @Nullable final GCPArgumentStack commandArguments;

    /**
     * This is TRUE when the latest node the user specified
     * was found correctly. Not to be confused with the Latest
     * Node, this is about not being able to find the node
     * after that one.
     *
     * @since 1.0.0
     */
    boolean fullNodeSuccess;

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public @Nullable GCMNode getLatestNode() {
        return latestNode;
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public @Nullable GCMCommandNode getCommand() {
        return command;
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public @Nullable GCMRootNode getRoot() {
        return root;
    }

    /**
     * If we found the command the user refers to
     *
     * @author Gunging
     * @since 1.0.0
     */
    public boolean foundCommand() { return getCommand() != null; }

    /**
     * If we found the node tree the user refers to
     *
     * @author Gunging
     * @since 1.0.0
     */
    public boolean foundRoot() { return getRoot() != null; }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public boolean isFullNodeSuccess() { return fullNodeSuccess; }
}
