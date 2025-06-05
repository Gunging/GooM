package gunging.ootilities.GungingOotilitiesMod.scheduling;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * An event fired every X amount of ticks
 *
 * @author Gunging
 * @since 1.0.0
 */
public abstract class SCHTickEvent extends Event {

    /**
     * @param server The minecraft server
     * @param clientSide If this is running on the client side
     * @param sourceTickEvent The tick event that originally fired this ten-ticks event
     *
     * @since 1.0.0
     * @author Gunging
     */
    protected SCHTickEvent(@Nullable MinecraftServer server, boolean clientSide, @NotNull TickEvent sourceTickEvent) {
        this.clientSide = clientSide;
        this.sourceTickEvent = sourceTickEvent;
        this.server = server;
    }

    /**
     * @return Every how often in ticks this class fires
     *
     * @author Gunging
     * @since 1.0.0
     */
    public abstract int getTickFrequency();

    /**
     * If this is running on the client side
     *
     * @since 1.0.0
     */
    final boolean clientSide;

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public boolean isClientSide() { return clientSide; }

    /**
     * The tick event that originally fired this ten-ticks event
     *
     * @since 1.0.0
     */
    @NotNull
    final TickEvent sourceTickEvent;

    /**
     * @since 1.0.0
     * @author Gunging
     */
    @NotNull public TickEvent getSourceTickEvent() { return sourceTickEvent; }

    /**
     * The Minecraft Server being ticked. Guaranteed to
     * exist in server-side, always null in client-side.
     *
     * @since 1.0.0
     */
    @Nullable final MinecraftServer server;

    /**
     * @since 1.0.0
     * @author Gunging
     */
    @Nullable public MinecraftServer getServer() { return server; }
}
