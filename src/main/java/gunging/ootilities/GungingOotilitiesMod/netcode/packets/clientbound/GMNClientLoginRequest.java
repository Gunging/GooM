package gunging.ootilities.GungingOotilitiesMod.netcode.packets.clientbound;

import gunging.ootilities.GungingOotilitiesMod.netcode.GOOMClientsidePacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

/**
 * Sent to the client when they log in,
 * requesting their prefs or whatever.
 *
 * @since 1.0.0
 * @author Gunging
 */
public class GMNClientLoginRequest {

    /**
     * @since 1.0.0
     * @author Gunging
     */
    public GMNClientLoginRequest() {}

    /**
     * @since 1.0.0
     * @author Gunging
     */
    public GMNClientLoginRequest(@NotNull FriendlyByteBuf buff) {}

    /**
     * @since 1.0.0
     * @author Gunging
     */
    public void encode(@NotNull FriendlyByteBuf buff) {}

    /**
     * Find the holder, entity, and item, and link them together
     *
     * @since 1.0.0
     * @author Gunging
     */
    public void handle(@NotNull Supplier<NetworkEvent.Context> contextSupplier) {
        contextSupplier.get().enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () ->
                    GOOMClientsidePacketHandler.handleLoginRequest(this, contextSupplier));
        });
        contextSupplier.get().setPacketHandled(true);
    }
}
