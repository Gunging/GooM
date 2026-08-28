package gunging.ootilities.GungingOotilitiesMod.netcode.packets.clientbound;

import gunging.ootilities.GungingOotilitiesMod.mixininterfaces.WithStatsStack;
import gunging.ootilities.GungingOotilitiesMod.netcode.GOOMClientsidePacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.charset.StandardCharsets;
import java.util.function.Supplier;

/**
 * A packet sent to clients when an Inherent Stat object changes
 *
 * @since 1.0.0
 * @author Gunging
 */
public class GMNClientboundInherentStatsEntity {

    /**
     * The entity ID of the Entity Counterpart of the Item-Entity duality
     *
     * @since 1.0.0
     */
    final int inherentEntityID;

    /**
     * The Entity of the Item-Entity duality
     *
     * @since 1.0.0
     */
    @Nullable LivingEntity inherentEntity;

    /**
     * @since 1.0.0
     * @author Gunging
     */
    @Nullable public LivingEntity getInherentEntity() { return inherentEntity; }

    /**
     * The serialized inherent stats of this entity
     *
     * @since 1.0.0
     */
    @NotNull String serializedInherent;

    /**
     * @since 1.0.0
     * @author Gunging
     */
    @NotNull public String getSerializedInherent() { return serializedInherent; }

    /**
     * @since 1.0.0
     * @author Gunging
     */
    @Nullable public LivingEntity getInherentEntity(@NotNull Level world) {

        // Done is done
        if (inherentEntity != null) { return inherentEntity; }

        Entity asEntity = world.getEntity(inherentEntityID);
        if (!(asEntity instanceof LivingEntity)) { return null; }

        // Find and return
        inherentEntity = (LivingEntity) asEntity;
        return inherentEntity;
    }

    /**
     * @param inherentEntity The entity which inherent stats to update
     *
     * @since 1.0.0
     * @author Gunging
     */
    public GMNClientboundInherentStatsEntity(@NotNull LivingEntity inherentEntity) {
        inherentEntityID = inherentEntity.getId();
        this.inherentEntity = inherentEntity;
        this.serializedInherent = SerializeNetworkInherent(inherentEntity);
    }

    /**
     * @param buf The list of stats received from the network
     *
     * @since 1.0.0
     * @author Gunging
     */
    public GMNClientboundInherentStatsEntity(@NotNull FriendlyByteBuf buf) {
        inherentEntityID = buf.readVarInt();
        int len = buf.readVarInt();
        serializedInherent = buf.readCharSequence(len, StandardCharsets.UTF_8).toString();
    }

    /**
     * @param buff A buffer in which to write the bytes to send over the network
     *
     * @since 1.0.0
     * @author Gunging
     */
    public void encode(@NotNull FriendlyByteBuf buff) {
        buff.writeVarInt(inherentEntityID);
        buff.writeVarInt(serializedInherent.length());
        buff.writeCharSequence(serializedInherent, StandardCharsets.UTF_8);
    }

    /**
     * @param entity The entity whose stats to serialize for the network
     *
     * @return The serialized inherent stats of this entity, to be sent over the network
     *
     * @since 1.0.0
     * @author Gunging
     */
    @NotNull public static String SerializeNetworkInherent(@Nullable LivingEntity entity) {
        if (entity == null) { return ""; }
        WithStatsStack asStats = (WithStatsStack) entity;
        return asStats.gungingoom$getStatStack().serializeInherent(true);
    }

    /**
     * @since 1.0.0
     * @author Gunging
     */
    public void handle(@NotNull Supplier<NetworkEvent.Context> contextSupplier) {
        contextSupplier.get().enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () ->
                    GOOMClientsidePacketHandler.handleInherentStatsEntity(this, contextSupplier));
        });
        contextSupplier.get().setPacketHandled(true);
    }
}
