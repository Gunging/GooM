package gunging.ootilities.GungingOotilitiesMod.exploring.entities;

import net.minecraft.world.entity.EquipmentSlot;
import org.jetbrains.annotations.NotNull;

public interface ISEEquipmentSlotted {

    /**
     * @return The vanilla equipment slot that this Entity slot targets
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull EquipmentSlot getEquipmentSlot();
}
