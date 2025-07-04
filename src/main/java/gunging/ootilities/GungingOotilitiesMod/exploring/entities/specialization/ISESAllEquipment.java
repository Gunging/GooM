package gunging.ootilities.GungingOotilitiesMod.exploring.entities.specialization;

import gunging.ootilities.GungingOotilitiesMod.exploring.ItemStackExplorer;
import gunging.ootilities.GungingOotilitiesMod.exploring.entities.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

/**
 * Absolutely all the slots of the inventory (except crafting grid)
 *
 * @author Gunging
 * @since 1.0.0
 */
public class ISESAllEquipment extends ISEEntityStatement {

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override  public @NotNull String getOptions() { return ""; }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override public @NotNull ISESAllEquipment withOptions(@NotNull String options) { return ISEExplorerStatements.ALL_EQUIPMENT; }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public ISESAllEquipment(@NotNull ResourceLocation name) { super(name); }

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
    public @NotNull ArrayList<ItemStackExplorer<ISEEntityElaborator, Entity>> whenElaborated(@NotNull ISEEntityElaborator elaborator) {
        ArrayList<ItemStackExplorer<ISEEntityElaborator, Entity>> ret = new ArrayList<>();

        ret.addAll(new ISEEntityExplorer(ISEExplorerStatements.ARMOR).elaborate(elaborator));
        ret.addAll(new ISEEntityExplorer(ISEExplorerStatements.HANDS).elaborate(elaborator));

        return ret;
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @Nullable ISEEntityLocation whenRealized(@NotNull ISEEntityElaborator elaborator) {
        throw new UnsupportedOperationException("A Slot Specialization that targets multiple slots cannot be realized. Please elaborate it first. ");
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @Nullable ItemStack readItemStack(@NotNull Entity target) {
        throw new UnsupportedOperationException("A Slot Specialization that targets multiple slots cannot be read. Please elaborate it first. ");
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public void writeItemStack(@NotNull Entity target, @Nullable ItemStack item) {
        throw new UnsupportedOperationException("A Slot Specialization that targets multiple slots cannot be written. Please elaborate it first. ");
    }
}
