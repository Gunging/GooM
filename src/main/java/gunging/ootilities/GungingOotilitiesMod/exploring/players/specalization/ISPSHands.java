package gunging.ootilities.GungingOotilitiesMod.exploring.players.specalization;

import gunging.ootilities.GungingOotilitiesMod.exploring.ItemStackExplorer;
import gunging.ootilities.GungingOotilitiesMod.exploring.players.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class ISPSHands extends ISPPlayerStatement {

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override  public @NotNull String getOptions() { return ""; }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override public @NotNull ISPSHands withOptions(@NotNull String options) { return ISPExplorerStatements.HANDS; }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public ISPSHands(@NotNull ResourceLocation name) { super(name); }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override public boolean isFundamental() { return false; }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @NotNull ArrayList<ItemStackExplorer<ISPPlayerElaborator, Player>> whenElaborated(@NotNull ISPPlayerElaborator elaborator) {
        ArrayList<ItemStackExplorer<ISPPlayerElaborator, Player>> ret = new ArrayList<>();

        ret.add(new ISPPlayerExplorer(ISPExplorerStatements.MAINHAND));
        ret.add(new ISPPlayerExplorer(ISPExplorerStatements.OFFHAND));

        return ret;
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @Nullable ISPPlayerLocation whenRealized(@NotNull ISPPlayerElaborator elaborator) {
        throw new UnsupportedOperationException("A Slot Specialization that targets multiple slots cannot be realized. Please elaborate it first. ");
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @Nullable ItemStack readItemStack(@NotNull Player target) {
        throw new UnsupportedOperationException("A Slot Specialization that targets multiple slots cannot be read. Please elaborate it first. ");
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public void writeItemStack(@NotNull Player target, @Nullable ItemStack item) {
        throw new UnsupportedOperationException("A Slot Specialization that targets multiple slots cannot be written. Please elaborate it first. ");
    }
}
