package gunging.ootilities.GungingOotilitiesMod.exploring.players.specalization;

import gunging.ootilities.GungingOotilitiesMod.exploring.ItemStackExplorer;
import gunging.ootilities.GungingOotilitiesMod.exploring.players.*;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

/**
 * All the normal inventory storage slots except for those of
 * the hotbar. Basically (9-35) or something like that.
 *
 * @author Gunging
 * @since 1.0.0
 */
public class ISPSStash extends ISPPlayerStatement {

    @Override public boolean isFundamental() { return false; }

    @Override
    public @NotNull ArrayList<ItemStackExplorer<ISPPlayerElaborator, Player>> whenElaborated(@NotNull ISPPlayerElaborator elaborator) {

        // Add the first 9 slots - the hotbar
        return new ArrayList<>((new ISPPlayerExplorer(ISPExplorerStatements.STANDARD.of(9, Inventory.INVENTORY_SIZE)).elaborate(elaborator)));
    }

    @Override
    public @Nullable ISPPlayerLocation whenRealized(@NotNull ISPPlayerElaborator elaborator) {
        throw new UnsupportedOperationException("A Slot Specialization that targets multiple slots cannot be realized. Please elaborate it first. ");
    }

    @Override
    public @Nullable ItemStack readItemStack(@NotNull Player target) {
        throw new UnsupportedOperationException("A Slot Specialization that targets multiple slots cannot be read. Please elaborate it first. ");
    }

    @Override
    public void writeItemStack(@NotNull Player target, @Nullable ItemStack item) {
        throw new UnsupportedOperationException("A Slot Specialization that targets multiple slots cannot be written. Please elaborate it first. ");
    }
}
