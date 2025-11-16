package gunging.ootilities.GungingOotilitiesMod.netcode.packets.clientbound;

import gunging.ootilities.GungingOotilitiesMod.netcode.GOOMClientsidePacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

/**
 * A packet to tell a client what position and momentum their
 * local player should have, best used within low distances
 * of where they previously were you probably don't want to
 * send one of these to teleport them across large distances.
 *
 * @since 1.0.0
 * @author Gunging
 */
public class GMNClientboundMomentum {

    /**
     * The position to set
     *
     * @since 1.0.0
     */
    @NotNull Vec3 position;

    /**
     * @since 1.0.0
     * @author Gunging
     */
    public @NotNull Vec3 getPosition() { return position; }

    /**
     * The velocity to set
     *
     * @since 1.0.0
     */
    @NotNull Vec3 velocity;

    /**
     * @since 1.0.0
     * @author Gunging
     */
    public @NotNull Vec3 getVelocity() { return velocity; }

    /**
     * @param pos The position to set
     * @param vel The velocity to set
     *
     * @since 1.0.0
     * @author Gunging
     */
    public GMNClientboundMomentum(@NotNull Vec3 pos, @NotNull Vec3 vel) {
        this.position = pos;
        this.velocity = vel;
    }

    /**
     * @param buf The list of statements received from the network
     *
     * @since 1.0.0
     * @author Gunging
     */
    public GMNClientboundMomentum(@NotNull FriendlyByteBuf buf) {
        position = new Vec3(buf.readDouble(), buf.readDouble(), buf.readDouble());
        velocity = new Vec3(buf.readDouble(), buf.readDouble(), buf.readDouble());
    }

    /**
     * @param buff A buffer in which to write the bytes to send over the network
     *
     * @since 1.0.0
     * @author Gunging
     */
    public void encode(@NotNull FriendlyByteBuf buff) {
        buff.writeDouble(position.x);
        buff.writeDouble(position.y);
        buff.writeDouble(position.z);
        buff.writeDouble(velocity.x);
        buff.writeDouble(velocity.y);
        buff.writeDouble(velocity.z);
    }

    /**
     * Find the holder, entity, and item, and link them together
     *
     * @since 1.0.0
     * @author Gunging
     */
    public void handle(@NotNull Supplier<NetworkEvent.Context> contextSupplier) {
        contextSupplier.get().enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () ->
                    GOOMClientsidePacketHandler.handleMomentumSync(this, contextSupplier));
        });
        contextSupplier.get().setPacketHandled(true);
    }
}
