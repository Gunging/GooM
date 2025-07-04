package gunging.ootilities.GungingOotilitiesMod.events.extension;

import gunging.ootilities.GungingOotilitiesMod.exploring.ItemStackLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.EntityEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Event fired whenever the equipment of any entity changes, either
 * server-sided when a mob picks up items (example), or any player
 * sends a packet of moving their equipped items in inventory; or
 * client-sided when the local client moves their inventory or
 * receives any updates from the server of other entities/players
 * equipping stuff.
 *
 * @since 1.0.0
 * @author Gunging
 */
public class EntityEquipmentChangeEvent extends EntityEvent {

    /**
     * The ItemStack that changed, in the inventory of the entity
     *
     * @since 1.0.0
     */
    @NotNull final ItemStackLocation<? extends Entity> stackLocation;

    /**
     * @since 1.0.0
     * @author Gunging
     */
    @NotNull public ItemStackLocation<? extends Entity> getStackLocation() { return stackLocation; }

    /**
     * @return The ItemStack currently in this Entity's inventory
     *
     * @since 1.0.0
     * @author Gunging
     */
    @NotNull public ItemStack getCurrentItemStack() {
        ItemStack found = getStackLocation().getItemStack();
        return found == null ? ItemStack.EMPTY : found;
    }

    /**
     * The point in the minecraft code where this event was fired from.
     *
     * @since 1.0.0
     */
    @NotNull final ItemFlowExtensionReason reason;

    /**
     * @since 1.0.0
     * @author Gunging
     */
    public @NotNull ItemFlowExtensionReason getReason() { return reason; }

    /**
     * @param stackLocation The location of the ItemStack that changed
     * @param reason The reason this extension event was fired
     *
     * @since 1.0.0
     * @author Gunging
     */
    public EntityEquipmentChangeEvent(@NotNull ItemStackLocation<? extends Entity> stackLocation, @NotNull ItemFlowExtensionReason reason) {
        super(stackLocation.getHolder());
        this.stackLocation = stackLocation;
        this.reason = reason;
    }
}
