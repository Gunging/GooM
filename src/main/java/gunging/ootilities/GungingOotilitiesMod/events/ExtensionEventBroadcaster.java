package gunging.ootilities.GungingOotilitiesMod.events;

import gunging.ootilities.GungingOotilitiesMod.events.extension.ClientsideEntityEquipmentChangeEvent;
import gunging.ootilities.GungingOotilitiesMod.events.extension.EntityEquipmentChangeEvent;
import gunging.ootilities.GungingOotilitiesMod.events.extension.ItemFlowExtensionReason;
import gunging.ootilities.GungingOotilitiesMod.events.extension.ServersideEntityEquipmentChangeEvent;
import gunging.ootilities.GungingOotilitiesMod.exploring.entities.ISEExplorerStatements;
import gunging.ootilities.GungingOotilitiesMod.exploring.entities.ISEEntityLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraftforge.common.MinecraftForge;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Extension events are rather invasive into the minecraft code,
 * they use mixins to fire from everywhere a certain something
 * happens. This usually results in a lot of places where they
 * are being created. This class at least keeps the event being
 * sent to the event buss consistent.
 *
 * @since 1.0.0
 * @author Gunging
 */
public class ExtensionEventBroadcaster {

    /**
     * @param entity The entity in question
     * @param slot The slot that changed in this Entity inventory
     * @param reason The reason this event was fired, where in the minecraft code did it trigger.
     * @param clientside If the clientside version is running.
     *
     * @since 1.0.0
     * @author Gunging
     */
    @Contract(value = "_, _, _, null -> null;_, _, _, !null -> !null")
    public static EntityEquipmentChangeEvent BroadcastEquipmentChangeEvent(@NotNull ItemFlowExtensionReason reason, boolean clientside, @NotNull EquipmentSlot slot, @Nullable Entity entity) {
        if (entity == null) { return null; }
        return BroadcastEquipmentChangeEvent(reason, clientside, new ISEEntityLocation(entity, ISEExplorerStatements.getByEquipmentSlot(slot)));
    }

    /**
     * @param stackLocation The built ItemStack location containing information on the Holder, the Item, and the inventory
     * @param reason The reason this event was fired, where in the minecraft code did it trigger.
     * @param clientside If the clientside version is running.
     *
     * @since 1.0.0
     * @author Gunging
     */
    @NotNull public static EntityEquipmentChangeEvent BroadcastEquipmentChangeEvent(@NotNull ItemFlowExtensionReason reason, boolean clientside, @NotNull ISEEntityLocation stackLocation) {

        // Define event
        EntityEquipmentChangeEvent event;

        // Choose appropriate side
        if (clientside) {
            event = new ClientsideEntityEquipmentChangeEvent(stackLocation, reason);
        } else {
            event = new ServersideEntityEquipmentChangeEvent(stackLocation, reason);
        }

        //BEC//GungingOotilitiesMod.Log("GOOP BEC &a(Clientside: " + clientside + ") &f Event fired for reason &3 " + reason + " &e | " + (stackLocation.getItemStack() == null ? "null" : stackLocation.getItemStack().getDisplayName().getString()) + " in " + stackLocation.getHolder().getScoreboardName());

        // Run Event
        MinecraftForge.EVENT_BUS.post(event);
        return event;
    }
}
