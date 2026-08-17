package gunging.ootilities.GungingOotilitiesMod.commands.forge;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import gunging.ootilities.GungingOotilitiesMod.GungingOotilitiesMod;
import gunging.ootilities.GungingOotilitiesMod.commands.core.building.*;
import gunging.ootilities.GungingOotilitiesMod.commands.core.parsing.GCPContextOptions;
import gunging.ootilities.GungingOotilitiesMod.commands.friendly.FriendlyFeedbackCategory;
import gunging.ootilities.GungingOotilitiesMod.ootilityception.OotilityNumbers;
import gunging.ootilities.GungingOotilitiesMod.stats.commands.StatsCommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Listens for the command registry events to register commands
 * registered to GooM. This is also where GCM GooMCommands get
 * built into Forge Commands.
 *
 * @author Gunging
 * @since 1.0.0
 */
@Mod.EventBusSubscriber(modid = GungingOotilitiesMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class GCCCommandRegistry {

    //region Registry
    /**
     * The collection of command trees registered
     *
     * @since 1.0.0
     */
    @NotNull static final HashMap<String, GCMRootNode> roots = new HashMap<>();

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public static HashMap<String, GCMRootNode> getRoots() { return roots; }

    /**
     * The root node for GooM
     *
     * @since 1.0.0
     */
    @NotNull static final GCMRootNode goomRoot = new GCMRootNode("goom");

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public static GCMRootNode getGooM() { return goomRoot; }

    /**
     * The standard width of paragraphs for help messages
     *
     * @since 1.0.0
     */
    public static int HELP_PARAGRAPH_WIDTH = 32767;
    //endregion

    //region Transcription
    /**
     * Collects all GooM command trees and registers them to the game
     *
     * @param event Event ran when commands are registered to minecraft
     *
     * @author Gunging
     * @since 1.0.0
     */
    @SubscribeEvent
    public static void OnForgeCommandsEvent(@NotNull RegisterCommandsEvent event) {

        // Collect GooM Commands
        GCCGooMRegisterCommandsEvent goom = new GCCGooMRegisterCommandsEvent();
        MinecraftForge.EVENT_BUS.post(goom);

        // Register them
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        for (GCMRootNode root : goom.getCommandTrees().values()) {

            // Register root node
            LiteralArgumentBuilder<CommandSourceStack> asForge = LiteralArgumentBuilder.literal(root.getKeyword());

            /*
             * Calling in help mode, it simply returns line by line the help messages to the command sender
             */
            asForge.executes(css -> {
                root.getHelp().sendAllTo((bakedMessage -> css.getSource().sendSystemMessage(bakedMessage)));
                return 1;
            });

            // Register child nodes
            for (GCMNode node : root.getSubnodes().values()) { asForge.then(forgeCommandNode(node, dispatcher)); }

            // Dispatch
            dispatcher.register(asForge);
            roots.put(root.getKeyword(), root);
        }
    }

    /**
     * Registers the commands included with GooM
     *
     * @param event Event ran when commands are registered to GooM
     *
     * @author Gunging
     * @since 1.0.0
     */
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void RegisterGooMCommands(@NotNull GCCGooMRegisterCommandsEvent event) {

        // Include GooM Root
        event.addRoot(goomRoot);

        // Build GooM branches
        goomRoot.addNode(new StatsCommandNode());

        // Build /help
        goomRoot.getHelp().activatePrefix(true, null);
        goomRoot.getHelp().log(FriendlyFeedbackCategory.INFORMATION, "$rA commands-based utilities library for finesse. ");
        goomRoot.getHelp().activatePrefix(false, null);
        for (String helpLine : OotilityNumbers.chop("GooM provides reliable utilities for map-making. It may be a bit technical, but the promise is low maintenance and backward compatibility — you only have to get it working once and forget. ", GCCCommandRegistry.HELP_PARAGRAPH_WIDTH, "$b")) {
            goomRoot.getHelp().log(FriendlyFeedbackCategory.INFORMATION, helpLine);
        }
    }

    /**
     * @param node The GooM node to process into a forge node
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public static ArgumentBuilder<CommandSourceStack, ?> forgeCommandNode(@NotNull GCMNode node, CommandDispatcher<CommandSourceStack> dispatcher) {

        // Build core around keyword
        ArgumentBuilder<CommandSourceStack, ?> ret = Commands.literal(node.getKeyword());

        // Command nodes rely on interpreting the Forge Command into a GooM command then running
        if (node instanceof GCMCommandNode) {

            // Transcribe arguments
            ArrayList<ArgumentBuilder<CommandSourceStack, ?>> argumentNodes = new ArrayList<>();
            for (GCMExpectedArgument<?> goomArgument : ((GCMCommandNode) node).getArgumentsByIndex()) {

                // Add arguments as quotable phrases
                ArgumentBuilder<CommandSourceStack, ?> argumentNode = Commands.argument(goomArgument.getArgumentKeyword(), StringArgumentType.string())

                        // Tab Completion Delegation
                        .suggests((css, builder) -> {

                            // Transcribe command from Minecraft to GooM
                            ArrayList<String> argBuilt = new ArrayList<>();
                            argBuilt.add(node.getKeyword());
                            for (GCMExpectedArgument<?> provided : ((GCMCommandNode) node).getArgumentsByIndex()) {
                                try {
                                    String in = StringArgumentType.getString(css, provided.getArgumentKeyword());
                                    argBuilt.add(in);
                                } catch (IllegalArgumentException ignored) { break; }
                            }

                            // Include empty arguments
                            if (css.getInput().endsWith(" ")) { argBuilt.add(""); }

                            // Provide options
                            GCPContextOptions options = new GCPContextOptions();
                            options.setCommandSourceStack(css.getSource());

                            // Suggest appropriately
                            ArrayList<String> tabCompletion = node.tabComplete(options, argBuilt.toArray(new String[0]));
                            return SharedSuggestionProvider.suggest(tabCompletion, builder);

                        // Command Execution Delegation
                        }).executes(css -> {

                            //todo Make sure to run /help mode when no args are provided

                            return 0;
                        });

                // Append to previous
                argumentNodes.add(argumentNode);
            }

            // Then in reverse
            for (int i = argumentNodes.size() - 1; i >= 0; i--) {
                ArgumentBuilder<CommandSourceStack, ?> thisNode = argumentNodes.get(i);
                ArgumentBuilder<CommandSourceStack, ?> parentNode = i > 0 ? argumentNodes.get(i - 1) : ret;
                parentNode.then(thisNode);
            }

            // Allow chaining
            //ret.then(Commands.literal("oS=").redirect(dispatcher.getRoot()));

        // If not a command node, register /help mode
        }else {
            ret.executes(css -> {
                node.getHelp().sendAllTo((bakedMessage -> css.getSource().sendSystemMessage(bakedMessage)));
                return 1;
            }); }

        // If it has children, register the children
        if (node instanceof GCMBranchNode) {
            for (GCMNode subnode : ((GCMBranchNode) node).getSubnodes().values()) {
                ret.then(forgeCommandNode(subnode, dispatcher));
            } }

        // Done
        return ret;
    }
    //endregion
}
