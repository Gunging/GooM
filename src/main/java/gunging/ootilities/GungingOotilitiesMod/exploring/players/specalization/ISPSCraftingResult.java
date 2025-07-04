package gunging.ootilities.GungingOotilitiesMod.exploring.players.specalization;

import gunging.ootilities.GungingOotilitiesMod.exploring.ItemStackExplorer;
import gunging.ootilities.GungingOotilitiesMod.exploring.players.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

/**
 * Targets the result slot of the crafting grid.
 *
 * @author Gunging
 * @since 1.0.0
 */
public class ISPSCraftingResult extends ISPPlayerStatement {

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @NotNull String getOptions() {
        return "";
    }

    /**
     * @param statementName The internal name of this explorer statement
     *
     * @author Gunging
     * @since 1.0.0
     */
    public ISPSCraftingResult(@NotNull ResourceLocation statementName) {
        super(statementName);
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @Nullable ISPSCraftingResult withOptions(@NotNull String options) {
        return ISPExplorerStatements.CRAFTING_RESULT;
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @NotNull ArrayList<ItemStackExplorer<ISPPlayerElaborator, Player>> whenElaborated(@NotNull ISPPlayerElaborator elaborator) {
        ArrayList<ItemStackExplorer<ISPPlayerElaborator, Player>> ret = new ArrayList<>();

        ret.add(new ISPPlayerExplorer(ISPExplorerStatements.CRAFTING_RESULT));

        return ret;
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @Nullable ISPPlayerLocation whenRealized(@NotNull ISPPlayerElaborator elaborator) {
        return new ISPPlayerLocation(elaborator.getPlayer(), this);
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @Nullable ItemStack readItemStack(@NotNull Player target) {
        return target.inventoryMenu.getCraftSlots().getItem(InventoryMenu.RESULT_SLOT);
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public void writeItemStack(@NotNull Player target, @Nullable ItemStack item) {
        target.inventoryMenu.getCraftSlots().setItem(InventoryMenu.RESULT_SLOT, item == null ? ItemStack.EMPTY : item);
    }
}
