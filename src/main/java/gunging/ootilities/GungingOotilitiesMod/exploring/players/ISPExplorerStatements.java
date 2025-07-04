package gunging.ootilities.GungingOotilitiesMod.exploring.players;

import gunging.ootilities.GungingOotilitiesMod.GungingOotilitiesMod;
import gunging.ootilities.GungingOotilitiesMod.exploring.players.specalization.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import org.jetbrains.annotations.NotNull;

/**
 * Special slot names for use with {@link ISPPlayerExplorer} elaboration.
 *
 * @since 1.0.0
 * @author Gunging
 */
public class ISPExplorerStatements {

    /**
     * @param slot The Equipment slot you search for
     *
     * @return The appropriate item stack slot statement for this equipment slot.
     *
     * @since 1.0.0
     * @author Gunging
     */
    @NotNull
    public static ISPPlayerStatement getByEquipmentSlot(@NotNull EquipmentSlot slot) {
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
     * It is actually not a special slot, just
     * a simple numeric (or number range).
     *
     * @since 1.0.0
     */
    public static ISPSStandard STANDARD = new ISPSStandard(ResourceLocation.fromNamespaceAndPath(GungingOotilitiesMod.MODID, "standard"));

    /**
     * The slot currently held by the player.
     *
     * @since 1.0.0
     */
    public static ISPSMainhand MAINHAND = new ISPSMainhand(ResourceLocation.fromNamespaceAndPath(GungingOotilitiesMod.MODID, "player_mainhand"));

    /**
     * The offhand slot of the player.
     *
     * @since 1.0.0
     */
    public static ISPSOffhand OFFHAND = new ISPSOffhand(ResourceLocation.fromNamespaceAndPath(GungingOotilitiesMod.MODID, "player_offhand"));

    /**
     * The slot for armor helmet
     *
     * @since 1.0.0
     */
    public static ISPSHead HEAD = new ISPSHead(ResourceLocation.fromNamespaceAndPath(GungingOotilitiesMod.MODID, "player_head"));

    /**
     * The slot for armor chestplate
     *
     * @since 1.0.0
     */
    public static ISPSChestplate CHEST = new ISPSChestplate(ResourceLocation.fromNamespaceAndPath(GungingOotilitiesMod.MODID, "player_chest"));

    /**
     * The slot for armor leggings
     *
     * @since 1.0.0
     */
    public static ISPSLegs LEGS = new ISPSLegs(ResourceLocation.fromNamespaceAndPath(GungingOotilitiesMod.MODID, "player_legs"));

    /**
     * The slot for armor boots
     *
     * @since 1.0.0
     */
    public static ISPSFeet FEET = new ISPSFeet(ResourceLocation.fromNamespaceAndPath(GungingOotilitiesMod.MODID, "player_feet"));

    /**
     * The four armor slots
     *
     * @since 1.0.0
     */
    public static ISPSArmor ARMOR = new ISPSArmor(ResourceLocation.fromNamespaceAndPath(GungingOotilitiesMod.MODID, "player_armor"));

    /**
     * The cursor slot, only really exists while the inventory GUI is open
     *
     * @since 1.0.0
     */
    public static ISPSCursor CURSOR = new ISPSCursor(ResourceLocation.fromNamespaceAndPath(GungingOotilitiesMod.MODID, "cursor"));

    /**
     * All the normal storage slots of the inventory, basically
     * the combination of the hotbar and the stash.
     *
     * @since 1.0.0
     */
    public static ISPSMain MAIN = new ISPSMain(ResourceLocation.fromNamespaceAndPath(GungingOotilitiesMod.MODID, "main"));

    /**
     * All the normal inventory storage slots except for those of
     * the hotbar. Basically (9-35) or something like that.
     *
     * @since 1.0.0
     */
    public static ISPSStash STASH = new ISPSStash(ResourceLocation.fromNamespaceAndPath(GungingOotilitiesMod.MODID, "stash"));

    /**
     * Both the main hand and the offhand
     *
     * @since 1.0.0
     */
    public static ISPSHands HANDS = new ISPSHands(ResourceLocation.fromNamespaceAndPath(GungingOotilitiesMod.MODID, "player_hands"));

    /**
     * Stands for the first 9 slots (0-8) of the player inventory.
     *
     * @since 1.0.0
     */
    public static ISPSHotbar HOTBAR = new ISPSHotbar(ResourceLocation.fromNamespaceAndPath(GungingOotilitiesMod.MODID, "hotbar"));

    /**
     * Absolutely all the slots of the inventory (except crafting grid)
     *
     * @since 1.0.0
     */
    public static ISPSAll ALL = new ISPSAll(ResourceLocation.fromNamespaceAndPath(GungingOotilitiesMod.MODID, "all"));

    /**
     * Targets specific slots of the crafting grid.
     *
     * @since 1.0.0
     */
    public static ISPSCrafting CRAFTING = new ISPSCrafting(ResourceLocation.fromNamespaceAndPath(GungingOotilitiesMod.MODID, "crafting"));

    /**
     * Targets the result slot of the crafting grid.
     *
     * @since 1.0.0
     */
    public static ISPSCraftingResult CRAFTING_RESULT = new ISPSCraftingResult(ResourceLocation.fromNamespaceAndPath(GungingOotilitiesMod.MODID, "craft_result"));

    /**
     * Targets all slots of the crafting grid.
     *
     * @since 1.0.0
     */
    public static ISPSAllCrafting ALL_CRAFTING = new ISPSAllCrafting(ResourceLocation.fromNamespaceAndPath(GungingOotilitiesMod.MODID, "all_crafting"));

    /**
     * Absolutely all the slots of the inventory including crafting grid
     *
     * @since 1.0.0
     */
    public static ISPSAllExtended ALL_EXTENDED = new ISPSAllExtended(ResourceLocation.fromNamespaceAndPath(GungingOotilitiesMod.MODID, "all_extended"));

    /**
     * A specific slot of the enderchest
     *
     * @since 1.0.0
     */
    public static ISPSEnderchest ENDERCHEST = new ISPSEnderchest(ResourceLocation.fromNamespaceAndPath(GungingOotilitiesMod.MODID, "enderchest"));

    /**
     * All the slots of the enderchest
     *
     * @since 1.0.0
     */
    public static ISPSAllEnderchest ALL_ENDERCHEST = new ISPSAllEnderchest(ResourceLocation.fromNamespaceAndPath(GungingOotilitiesMod.MODID, "all_enderchest"));

    /**
     * All the slots of the enderchest
     *
     * @since 1.0.0
     */
    public static ISPSAllEquipment  ALL_EQUIPMENT = new ISPSAllEquipment(ResourceLocation.fromNamespaceAndPath(GungingOotilitiesMod.MODID, "all_player_equipment"));

}
