package gunging.ootilities.GungingOotilitiesMod.netcode;

import gunging.ootilities.GungingOotilitiesMod.GungingOotilitiesMod;
import gunging.ootilities.GungingOotilitiesMod.netcode.packets.clientbound.GMNClientLoginRequest;
import gunging.ootilities.GungingOotilitiesMod.netcode.packets.clientbound.GMNClientboundMomentum;
import gunging.ootilities.GungingOotilitiesMod.netcode.packets.clientbound.GMNClientboundStatementSync;
import gunging.ootilities.GungingOotilitiesMod.netcode.packets.serverbound.GMNServerboundStatementSyncRequest;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The class tasked to handle Netcode for GooM
 *
 * @since 1.0.0
 * @author Gunging
 */
public class GOOMNetworkManager {

    /**
     * The main serverbound connection channel
     *
     * @since 1.0.0
     */
    private static final SimpleChannel MAIN_CHANNEL =
            NetworkRegistry.ChannelBuilder.named(
                            ResourceLocation.fromNamespaceAndPath(GungingOotilitiesMod.MODID, "main_channel"))
                    .serverAcceptedVersions((version) -> true )
                    .clientAcceptedVersions((version) -> true)
                    .networkProtocolVersion(() -> "1").simpleChannel();

    /**
     * Registers all network packets to their appropriate channel, intended
     * to be called ONCE during mod loading and only then.
     *
     * @since 1.0.0
     * @author Gunging
     */
    public static void register() {
        int i = 0;

        MAIN_CHANNEL.messageBuilder(GMNServerboundStatementSyncRequest.class, i++, NetworkDirection.PLAY_TO_SERVER)
                .encoder(GMNServerboundStatementSyncRequest::encode)
                .decoder(GMNServerboundStatementSyncRequest::new)
                .consumerMainThread(GMNServerboundStatementSyncRequest::handle).add();

        MAIN_CHANNEL.messageBuilder(GMNClientboundStatementSync.class, i++, NetworkDirection.PLAY_TO_CLIENT)
                .encoder(GMNClientboundStatementSync::encode)
                .decoder(GMNClientboundStatementSync::new)
                .consumerMainThread(GMNClientboundStatementSync::handle).add();

        MAIN_CHANNEL.messageBuilder(GMNClientboundMomentum.class, i++, NetworkDirection.PLAY_TO_CLIENT)
                .encoder(GMNClientboundMomentum::encode)
                .decoder(GMNClientboundMomentum::new)
                .consumerMainThread(GMNClientboundMomentum::handle).add();

        MAIN_CHANNEL.messageBuilder(GMNClientLoginRequest.class, i++, NetworkDirection.PLAY_TO_CLIENT)
                .encoder(GMNClientLoginRequest::encode)
                .decoder(GMNClientLoginRequest::new)
                .consumerMainThread(GMNClientLoginRequest::handle).add();
    }

    /**
     * @param msg The packet to send from the local client to the server.
     *
     * @since 1.0.0
     * @author Gunging
     */
    public static void playerToServer(@Nullable Object msg) {
        MAIN_CHANNEL.send(PacketDistributor.SERVER.noArg(), msg);
    }

    /**
     * @param msg The packet to send from the local client to everyone online.
     *
     * @since 1.0.0
     * @author Gunging
     */
    public static void playerToEveryone(@Nullable Object msg) {
        MAIN_CHANNEL.send(PacketDistributor.ALL.noArg(), msg);
    }

    /**
     * @param msg The packet to send from a local client to another specific remote client.
     *
     * @since 1.0.0
     * @author Gunging
     */
    public static void playerToPlayer(@NotNull ServerPlayer player, @Nullable Object msg) {
        MAIN_CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), msg);
    }

    /**
     * Broadcasts from the server to all clients tracking this entity
     *
     * @param who Entity in question
     * @param msg Packet to send
     *
     * @since 1.0.0
     * @author Gunging
     */
    public static void broadcastEntityUpdate(@NotNull Entity who, @Nullable Object msg) {
        MAIN_CHANNEL.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> who), msg);
    }

    /**
     * Broadcasts from the server to the specified player
     *
     * @param who Player in question
     * @param msg Packet to send
     *
     * @since 1.0.0
     * @author Gunging
     */
    public static void serverToPlayer(@NotNull ServerPlayer who, @Nullable Object msg) {
        MAIN_CHANNEL.send(PacketDistributor.PLAYER.with(() -> who), msg);
    }
}
