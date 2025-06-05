package gunging.ootilities.GungingOotilitiesMod.events.extension;

import gunging.ootilities.GungingOotilitiesMod.exploring.entities.ISEEntityLocation;
import org.jetbrains.annotations.NotNull;

/**
 * Fires when the equipment of an entity (any player included)
 * changes in the Client-Side. This is either the local player
 * selecting a different hotbar slot or moving things around in
 * their inventory, or when a packet is received over the network
 * that another entity changed its equipment.
 *
 * @see ItemFlowExtensionReason
 *
 * @since   1.0.0
 * @author Gunging
 */
public class ClientsideEntityEquipmentChangeEvent extends EntityEquipmentChangeEvent {
    /**
     * @param stackLocation The location of the ItemStack that changed
     * @param reason        The reason this extension event was fired
     *
     * @author Gunging
     * @since 1.0.0
     */
    public ClientsideEntityEquipmentChangeEvent(@NotNull ISEEntityLocation stackLocation, @NotNull ItemFlowExtensionReason reason) {
        super(stackLocation, reason);
    }
}
