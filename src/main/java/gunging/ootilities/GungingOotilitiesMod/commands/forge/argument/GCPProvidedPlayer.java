package gunging.ootilities.GungingOotilitiesMod.commands.forge.argument;

import gunging.ootilities.GungingOotilitiesMod.commands.core.parsing.GCPCommandStack;
import gunging.ootilities.GungingOotilitiesMod.commands.core.parsing.GCPPlaceholderArgument;
import gunging.ootilities.GungingOotilitiesMod.commands.core.parsing.GCPProvidedArgument;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * An instance of a Player argument that was provided
 *
 * @author Gunging
 * @since 1.0.0
 */
public class GCPProvidedPlayer extends GCPPlaceholderArgument<ServerPlayer> {

    /**
     * List of acceptable placeholders that will be substituted by the
     * sender of the command when encountered.
     *
     * @since 1.0.0
     */
    public static ArrayList<String> playerPlaceholders = new ArrayList<>(List.of(new String[] { "@s", "%player%" }));

    /**
     * @param context The context in which this command was called
     * @param explicit The text provided by the user
     *
     * @author Gunging
     * @since 1.0.0
     */
    public GCPProvidedPlayer(@NotNull GCPCommandStack context, @NotNull String explicit) {
        super(explicit);

        // Parse
        CommandSourceStack source = context.getOptions().getCommandSourceStack();
        if (source == null) { setParsingError("$fServer not found. "); return; }

        // Parse player
        ServerPlayer found = source.getServer().getPlayerList().getPlayerByName(explicit);
        if (found == null) {
            for (String papi : playerPlaceholders) {
                if (papi.equals(explicit)) { found = source.getPlayer(); setWasPlaceholder(true);
                } } }
        if (found == null) { setParsingError("$bPlayer '$f" + explicit + "$b' not found. "); return; }

        // Done
        setParsed(found);
    }
}
