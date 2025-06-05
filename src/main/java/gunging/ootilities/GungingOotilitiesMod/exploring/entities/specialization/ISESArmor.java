package gunging.ootilities.GungingOotilitiesMod.exploring.entities.specialization;

import gunging.ootilities.GungingOotilitiesMod.exploring.ItemStackExplorer;
import gunging.ootilities.GungingOotilitiesMod.exploring.entities.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class ISESArmor extends ISEEquipmentStatement {

    @Override public boolean isFundamental() { return false; }

    @Override
    public @NotNull ArrayList<ItemStackExplorer<ISEEntityElaborator, Entity>> whenElaborated(@NotNull ISEEntityElaborator elaborator) {
        ArrayList<ItemStackExplorer<ISEEntityElaborator, Entity>> ret = new ArrayList<>();

        ret.add(new ISEEntityExplorer(ISEExplorerStatements.HELMET));
        ret.add(new ISEEntityExplorer(ISEExplorerStatements.CHESTPLATE));
        ret.add(new ISEEntityExplorer(ISEExplorerStatements.LEGGINGS));
        ret.add(new ISEEntityExplorer(ISEExplorerStatements.BOOTS));

        return ret;
    }

    @Override
    public @Nullable ISEEntityLocation whenRealized(@NotNull ISEEntityElaborator elaborator) {
        throw new UnsupportedOperationException("A Slot Specialization that targets multiple slots cannot be realized. Please elaborate it first. ");
    }

    @Override
    public @Nullable ItemStack readItemStack(@NotNull Entity target) {
        throw new UnsupportedOperationException("A Slot Specialization that targets multiple slots cannot be read. Please elaborate it first. ");
    }

    @Override
    public void writeItemStack(@NotNull Entity target, @Nullable ItemStack item) {
        throw new UnsupportedOperationException("A Slot Specialization that targets multiple slots cannot be written. Please elaborate it first. ");
    }

    @Override
    public @NotNull EquipmentSlot getEquipmentSlot() {
        throw new UnsupportedOperationException("This Slot Specialization targets multiple slots. Please elaborate it first. ");
    }
}
