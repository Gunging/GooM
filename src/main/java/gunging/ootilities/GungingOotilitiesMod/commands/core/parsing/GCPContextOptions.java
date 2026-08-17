package gunging.ootilities.GungingOotilitiesMod.commands.core.parsing;

import net.minecraft.commands.CommandSourceStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A list of context regarding the execution of a command
 *
 * @author Gunging
 * @since 1.0.0
 */
public class GCPContextOptions implements Cloneable {

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public @Nullable CommandSourceStack getCommandSourceStack() {
        return commandSourceStack;
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public void setCommandSourceStack(@Nullable CommandSourceStack commandSourceStack) {
        this.commandSourceStack = commandSourceStack;
    }

    /**
     * All the information we could ever desire as far as Forge is concerned
     *
     * @since 1.0.0
     */
    @Nullable CommandSourceStack commandSourceStack;

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public GCPContextOptions() { this(""); }

    /**
     * @param textual The text value to parse these from
     *
     * @author Gunging
     * @since 1.0.0
     */
    public GCPContextOptions(@NotNull String textual) {

    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    protected GCPContextOptions clone() {
        GCPContextOptions ret = new GCPContextOptions("");
        ret.setCommandSourceStack(getCommandSourceStack());
        return ret;
    }

    /**
     * @param context Combines these two options, merging
     *                me onto them where my options take
     *                higher priority.
     *
     * @author Gunging
     * @since 1.0.0
     */
    public void withContext(@NotNull GCPContextOptions context) {

        // No source? Adopt the source of the context
        if (getCommandSourceStack() == null) { setCommandSourceStack(context.getCommandSourceStack()); }
    }
}
