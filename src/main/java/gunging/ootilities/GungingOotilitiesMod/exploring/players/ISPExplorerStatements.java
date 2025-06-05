package gunging.ootilities.GungingOotilitiesMod.exploring.players;

import gunging.ootilities.GungingOotilitiesMod.exploring.players.specalization.*;

/**
 * Special slot names for use with {@link ISPPlayerExplorer} elaboration.
 *
 * @since 1.0.0
 * @author Gunging
 */
public class ISPExplorerStatements {

    /**
     * It is actually not a special slot, just
     * a simple numeric (or number range).
     *
     * @since 1.0.0
     */
    public static ISPSStandard STANDARD = new ISPSStandard();

    /**
     * The slot currently held by the player.
     *
     * @since 1.0.0
     */
    public static ISPSMainhand MAINHAND = new ISPSMainhand();

    /**
     * The offhand slot of the player.
     *
     * @since 1.0.0
     */
    public static ISPSOffhand OFFHAND = new ISPSOffhand();


    /**
     * The slot for armor helmet
     *
     * @since 1.0.0
     */
    public static ISPSHead HEAD = new ISPSHead();

    /**
     * The slot for armor chestplate
     *
     * @since 1.0.0
     */
    public static ISPSChestplate CHEST = new ISPSChestplate();

    /**
     * The slot for armor leggings
     *
     * @since 1.0.0
     */
    public static ISPSLegs LEGS = new ISPSLegs();

    /**
     * The slot for armor boots
     *
     * @since 1.0.0
     */
    public static ISPSFeet FEET = new ISPSFeet();

    /**
     * The four armor slots
     *
     * @since 1.0.0
     */
    public static ISPSArmor ARMOR = new ISPSArmor();

    /**
     * The cursor slot, only really exists while the inventory GUI is open
     *
     * @since 1.0.0
     */
    public static ISPSCursor CURSOR = new ISPSCursor();

    /**
     * All the normal storage slots of the inventory, basically
     * the combination of the hotbar and the stash.
     *
     * @since 1.0.0
     */
    public static ISPSMain MAIN = new ISPSMain();

    /**
     * All the normal inventory storage slots except for those of
     * the hotbar. Basically (9-35) or something like that.
     *
     * @since 1.0.0
     */
    public static ISPSStash STASH = new ISPSStash();

    /**
     * Stands for the first 9 slots (0-8) of the player inventory.
     *
     * @since 1.0.0
     */
    public static ISPSHotbar HOTBAR = new ISPSHotbar();

    /**
     * Absolutely all the slots of the inventory (except crafting grid)
     *
     * @since 1.0.0
     */
    public static ISPSAll ALL = new ISPSAll();

    /**
     * Targets specific slots of the crafting grid.
     *
     * @since 1.0.0
     */
    public static ISPSCrafting CRAFTING = new ISPSCrafting();

    /**
     * Targets the result slot of the crafting grid.
     *
     * @since 1.0.0
     */
    public static ISPSCraftingResult CRAFTING_RESULT = new ISPSCraftingResult();

    /**
     * Targets all slots of the crafting grid.
     *
     * @since 1.0.0
     */
    public static ISPSAllCrafting ALL_CRAFTING = new ISPSAllCrafting();

    /**
     * Absolutely all the slots of the inventory including crafting grid
     *
     * @since 1.0.0
     */
    public static ISPSAllExtended ALL_EXTENDED = new ISPSAllExtended();

    /**
     * A specific slot of the enderchest
     *
     * @since 1.0.0
     */
    public static ISPSEnderchest ENDERCHEST = new ISPSEnderchest();

    /**
     * All the slots of the enderchest
     *
     * @since 1.0.0
     */
     public static ISPSAllEnderchest ALL_ENDERCHEST = new ISPSAllEnderchest();

}
