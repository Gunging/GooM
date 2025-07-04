package gunging.ootilities.GungingOotilitiesMod.scheduling;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.event.TickEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * An event fired every 5 ticks
 *
 * @since 1.0.0
 * @author Gunging
 */
public class SCHFiveTicksEvent extends SCHTickEvent {

    /**
     * @param server The minecraft server
     * @param clientSide If this is running on the client side
     * @param sourceTickEvent The tick event that originally fired this ten-ticks event
     *
     * @author Gunging
     * @since 1.0.0
     */
    protected SCHFiveTicksEvent(@Nullable MinecraftServer server, boolean clientSide, @NotNull TickEvent sourceTickEvent) {
        super(server, clientSide, sourceTickEvent);
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override public int getTickFrequency() { return 5; }
}
