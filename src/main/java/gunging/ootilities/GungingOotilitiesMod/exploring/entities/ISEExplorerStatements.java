package gunging.ootilities.GungingOotilitiesMod.exploring.entities;

import gunging.ootilities.GungingOotilitiesMod.exploring.entities.specialization.*;
import net.minecraft.world.entity.EquipmentSlot;
import org.jetbrains.annotations.NotNull;

/**
 * Special slot names for use with {@link ISEEntityExplorer} elaboration.
 *
 * @since 1.0.0
 * @author Gunging
 */
public class ISEExplorerStatements {

    /**
     * @param slot The Equipment slot you search for
     *
     * @return The appropriate item stack slot specialization for this slot.
     *
     * @since 1.0.0
     * @author Gunging
     */
    @NotNull
    public static ISEEntityStatement getByEquipmentSlot(@NotNull EquipmentSlot slot) {
        switch (slot) {
            case FEET:
                return BOOTS;

            case HEAD:
                return HELMET;

            case LEGS:
                return LEGGINGS;

            case CHEST:
                return CHESTPLATE;

            case OFFHAND:
                return OFFHAND;

            case MAINHAND:
            default:
                return MAINHAND;
        }
    }

    /**
     * Represents the four armor slots
     *
     * @since 1.0.0
     */
    public static ISESArmor ARMOR = new ISESArmor();
    /**
     * Represents both the hand slots
     *
     * @since 1.0.0
     */
    public static ISESHands HANDS = new ISESHands();

    /**
     * The slot currently held by the entity mainhand.
     *
     * @since 1.0.0
     */
    public static ISESMainhand MAINHAND = new ISESMainhand();

    /**
     * The slot currently held by the entity offhand.
     *
     * @since 1.0.0
     */
    public static ISESOffhand OFFHAND = new ISESOffhand();

    /**
     * The slot currently held by the entity helmet.
     *
     * @since 1.0.0
     */
    public static ISESHelmet HELMET = new ISESHelmet();

    /**
     * The slot currently held by the entity chest.
     *
     * @since 1.0.0
     */
    public static ISESChestplate CHESTPLATE = new ISESChestplate();

    /**
     * The slot currently held by the entity chest.
     *
     * @since 1.0.0
     */
    public static ISESLeggings LEGGINGS = new ISESLeggings();

    /**
     * The slot currently held by the entity chest.
     *
     * @since 1.0.0
     */
    public static ISESBoots BOOTS = new ISESBoots();

    /**
     * Represents all the slots of this entity (four armor and two hands)
     *
     * @since 1.0.0
     */
    public static ISESAll ALL = new ISESAll();
}
