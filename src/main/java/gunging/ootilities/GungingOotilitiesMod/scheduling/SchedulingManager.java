package gunging.ootilities.GungingOotilitiesMod.scheduling;

import gunging.ootilities.GungingOotilitiesMod.GungingOotilitiesMod;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * A few events that fire every few ticks rather than every tick,
 * for operations that don't require such high priority.
 *
 * @since 1.0.0
 * @author Gunging
 */
@Mod.EventBusSubscriber(modid = GungingOotilitiesMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SchedulingManager {

    /**
     * Keeps track of the ticks elapsed
     *
     * @since 1.0.0
     */
    static long clientTicks;

    /**
     * Keeps track of the ticks elapsed
     *
     * @since 1.0.0
     */
    static long serverTicks;

    /**
     * @return The client ticks elapsed since the application began ticking.
     *
     * @since 1.0.0
     * @author Gunging
     */
    public static long getClientTicks() { return clientTicks; }

    /**
     * @return The server ticks elapsed since the application began ticking.
     *
     * @since 1.0.0
     * @author Gunging
     */
    public static long getServerTicks() { return serverTicks; }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void OnTick(@NotNull TickEvent.ServerTickEvent event) {

        // GooM ticks on the END phase
        if (event.phase == TickEvent.Phase.START) { return; }

        // Advance ticks
        serverTicks++;
        tryBroadcastEvents(event, serverTicks, false);
        if (!serversideQueue.isEmpty()) { tryRunTasks(serversideQueue, serverTicks); }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void OnTick(@NotNull TickEvent.ClientTickEvent event) {

        // GooM ticks on the END phase
        if (event.phase == TickEvent.Phase.START) { return; }

        // Advance ticks
        clientTicks++;
        tryBroadcastEvents(event, clientTicks, true);
        if (!clientsideQueue.isEmpty()) { tryRunTasks(clientsideQueue, clientTicks); }
    }

    /**
     * @param task The task to run
     * @param clientSided The logic side to run it in
     *
     * @since 1.0.0
     * @author Gunging
     */
    public static void scheduleTask(@NotNull SCHScheduled task, long waitTicks, boolean clientSided) {
        if (clientSided) {
            SCHTaskSchedule scheduled = new SCHTaskSchedule(task, waitTicks + clientTicks);
            clientsideQueue.add(scheduled);
        } else {
            SCHTaskSchedule scheduled = new SCHTaskSchedule(task, waitTicks + serverTicks);
            serversideQueue.add(scheduled);
        }
    }

    /**
     * The tasks waiting to be run in the server
     *
     * @since 1.0.0
     */
    @NotNull static ArrayList<SCHTaskSchedule> serversideQueue = new ArrayList<>();

    /**
     * The tasks waiting to be run in the client
     *
     * @since 1.0.0
     */
    @NotNull static ArrayList<SCHTaskSchedule> clientsideQueue = new ArrayList<>();

    /**
     * Based on the current tick counter, fires a few events.
     *
     * @param event The event originally firing these
     *
     * @since 1.0.0
     * @author Gunging
     */
    static void tryBroadcastEvents(@NotNull TickEvent event, long localTicks, boolean clientSided) {

        // Identify server
        MinecraftServer server = null;
        if (event instanceof TickEvent.ServerTickEvent) { server = ((TickEvent.ServerTickEvent) event).getServer(); }

        // Tick
        if (localTicks % 10 == 0) {
            SCHTenTicksEvent broadcast = new SCHTenTicksEvent(server, clientSided, event);
            MinecraftForge.EVENT_BUS.post(broadcast);
        }
        if (localTicks % 20 == 0) {
            SCHTwentyTicksEvent broadcast = new SCHTwentyTicksEvent(server, clientSided, event);
            MinecraftForge.EVENT_BUS.post(broadcast);
        }
        if (localTicks % 100 == 0) {
            SCHHundredTicksEvent broadcast = new SCHHundredTicksEvent(server, clientSided, event);
            MinecraftForge.EVENT_BUS.post(broadcast);

            // Clean queue task lists every 5 or so seconds
            if (clientSided) {
                if (!clientsideQueue.isEmpty()) { clientsideQueue = cleanTasks(clientsideQueue); }
            } else {
                if (!serversideQueue.isEmpty()) { serversideQueue = cleanTasks(serversideQueue); }
            }
        }
    }

    /**
     * @param tasks The list of tasks to try to run
     * @param localTicks The ticks we are at
     */
    static void tryRunTasks(@NotNull ArrayList<SCHTaskSchedule> tasks, long localTicks) {

        // Attempt to resolve all tasks
        for (SCHTaskSchedule task : tasks) {
            if (!task.isResolved()) {
                if (task.getDate() <= localTicks) {
                    task.getTask().runGOOMTask();
                    task.resolve();
                }
            }
        }
    }

    /**
     * Basically copies this list, but excludes tasks that were resolved from the copy
     *
     * @param tasks The tasks that may have been resolved
     * @return A list with only the unresolved tasks
     *
     * @since 1.0.0
     * @author Gunging
     */
    static @NotNull ArrayList<SCHTaskSchedule> cleanTasks(@NotNull ArrayList<SCHTaskSchedule> tasks) {
        ArrayList<SCHTaskSchedule> ret = new ArrayList<>();

        // Attempt to resolve all tasks
        for (SCHTaskSchedule task : tasks) {
            if (!task.isResolved()) { ret.add(task); }
        }

        return ret;
    }
}
