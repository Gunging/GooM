package gunging.ootilities.GungingOotilitiesMod.exploring.players.specalization;

import gunging.ootilities.GungingOotilitiesMod.exploring.ItemStackExplorer;
import gunging.ootilities.GungingOotilitiesMod.exploring.players.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

/**
 * All the slots of the enderchest
 *
 * @author Gunging
 * @since 1.0.0
 */
public class ISPSAllEnderchest extends ISPPlayerStatement {


    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override  public @NotNull String getOptions() { return ""; }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override public @NotNull ISPSAllEnderchest withOptions(@NotNull String options) { return ISPExplorerStatements.ALL_ENDERCHEST; }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public ISPSAllEnderchest(@NotNull ResourceLocation name) { super(name); }

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

        // Max slot is size-1, since zeroth slot counts
        int ecMax = elaborator.getPlayer().getEnderChestInventory().getContainerSize() - 1;

        // Elaborate that range
        return new ArrayList<>((new ISPPlayerExplorer(ISPExplorerStatements.ENDERCHEST.of(0, ecMax)).elaborate(elaborator)));
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
