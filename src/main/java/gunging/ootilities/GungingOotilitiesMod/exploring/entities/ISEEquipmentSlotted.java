package gunging.ootilities.GungingOotilitiesMod.exploring.entities;

import net.minecraft.world.entity.EquipmentSlot;
import org.jetbrains.annotations.NotNull;

/**
 * Intended use for Item Explorer Statements that are based on Equipment Slots
 *
 * @since 1.0.0
 * @author Gunging
 */
public interface ISEEquipmentSlotted {

    /**
     * @return The vanilla equipment slot that this Entity slot targets
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull EquipmentSlot getEquipmentSlot();
}
