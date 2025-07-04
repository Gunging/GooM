package gunging.ootilities.GungingOotilitiesMod.exploring.entities.specialization;

import gunging.ootilities.GungingOotilitiesMod.exploring.ItemStackExplorer;
import gunging.ootilities.GungingOotilitiesMod.exploring.entities.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class ISESHead extends ISEEquipmentStatement {

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @NotNull String getOptions() { return ""; }

    /**
     * @param statementName The internal name of this explorer statement
     *
     * @author Gunging
     * @since 1.0.0
     */
    public ISESHead(@NotNull ResourceLocation statementName) { super(statementName); }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override public @Nullable ISESHead withOptions(@NotNull String options) {return ISEExplorerStatements.HEAD; }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override public @NotNull EquipmentSlot getEquipmentSlot() { return EquipmentSlot.HEAD; }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @NotNull ArrayList<ItemStackExplorer<ISEEntityElaborator, Entity>> whenElaborated(@NotNull ISEEntityElaborator elaborator) {
        ArrayList<ItemStackExplorer<ISEEntityElaborator, Entity>> ret = new ArrayList<>();

        ret.add(new ISEEntityExplorer(ISEExplorerStatements.HEAD));

        return ret;
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @Nullable ISEEntityLocation whenRealized(@NotNull ISEEntityElaborator elaborator) {
        return new ISEEntityLocation(elaborator.getEntity(), this);
    }
}
