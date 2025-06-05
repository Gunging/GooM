package gunging.ootilities.GungingOotilitiesMod.events.extension;

import gunging.ootilities.GungingOotilitiesMod.exploring.entities.ISEEntityLocation;
import org.jetbrains.annotations.NotNull;

/**
 * Fires when the equipment of an entity (any player included)
 * changes in the Server-Side. This would happen when a mob
 * picks up an item, when the player equips armor, drops something,
 * selects another item to hold, etc.
 *
 * @see ItemFlowExtensionReason
 *
 * @since   1.0.0
 * @author Gunging
 */
public class ServersideEntityEquipmentChangeEvent extends EntityEquipmentChangeEvent {
    /**
     * @param stackLocation The location of the ItemStack that changed
     * @param reason        The reason this extension event was fired
     *
     * @author Gunging
     * @since 1.0.0
     */
    public ServersideEntityEquipmentChangeEvent(@NotNull ISEEntityLocation stackLocation, @NotNull ItemFlowExtensionReason reason) {
        super(stackLocation, reason);
    }
}
