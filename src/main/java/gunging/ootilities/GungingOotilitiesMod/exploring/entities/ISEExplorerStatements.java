package gunging.ootilities.GungingOotilitiesMod.exploring.entities;

import gunging.ootilities.GungingOotilitiesMod.GungingOotilitiesMod;
import gunging.ootilities.GungingOotilitiesMod.exploring.entities.specialization.*;
import net.minecraft.resources.ResourceLocation;
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
     * @return The appropriate item stack slot statement for this equipment slot.
     *
     * @since 1.0.0
     * @author Gunging
     */
    @NotNull
    public static ISEEntityStatement getByEquipmentSlot(@NotNull EquipmentSlot slot) {
        switch (slot) {
            case FEET:
                return FEET;

            case HEAD:
                return HEAD;

            case LEGS:
                return LEGS;

            case CHEST:
                return CHEST;

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
    public static ISESArmor ARMOR = new ISESArmor(ResourceLocation.fromNamespaceAndPath(GungingOotilitiesMod.MODID, "armor"));
    /**
     * Represents both the hand slots
     *
     * @since 1.0.0
     */
    public static ISESHands HANDS = new ISESHands(ResourceLocation.fromNamespaceAndPath(GungingOotilitiesMod.MODID, "hands"));

    /**
     * The slot currently held by the entity mainhand.
     *
     * @since 1.0.0
     */
    public static ISESMainhand MAINHAND = new ISESMainhand(ResourceLocation.fromNamespaceAndPath(GungingOotilitiesMod.MODID, "mainhand"));

    /**
     * The slot currently held by the entity offhand.
     *
     * @since 1.0.0
     */
    public static ISESOffhand OFFHAND = new ISESOffhand(ResourceLocation.fromNamespaceAndPath(GungingOotilitiesMod.MODID, "offhand"));

    /**
     * The slot currently held by the entity helmet.
     *
     * @since 1.0.0
     */
    public static ISESHead HEAD = new ISESHead(ResourceLocation.fromNamespaceAndPath(GungingOotilitiesMod.MODID, "head"));

    /**
     * The slot currently held by the entity chest.
     *
     * @since 1.0.0
     */
    public static ISESChest CHEST = new ISESChest(ResourceLocation.fromNamespaceAndPath(GungingOotilitiesMod.MODID, "chest"));

    /**
     * The slot currently held by the entity chest.
     *
     * @since 1.0.0
     */
    public static ISESLegs LEGS = new ISESLegs(ResourceLocation.fromNamespaceAndPath(GungingOotilitiesMod.MODID, "legs"));

    /**
     * The slot currently held by the entity chest.
     *
     * @since 1.0.0
     */
    public static ISESFeet FEET = new ISESFeet(ResourceLocation.fromNamespaceAndPath(GungingOotilitiesMod.MODID, "feet"));

    /**
     * Represents all the slots of this entity (four armor and two hands)
     *
     * @since 1.0.0
     */
    public static ISESAllEquipment ALL_EQUIPMENT = new ISESAllEquipment(ResourceLocation.fromNamespaceAndPath(GungingOotilitiesMod.MODID, "all_equipment"));
}
