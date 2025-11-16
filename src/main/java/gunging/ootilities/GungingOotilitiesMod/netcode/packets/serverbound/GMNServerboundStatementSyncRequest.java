package gunging.ootilities.GungingOotilitiesMod.netcode.packets.serverbound;

import gunging.ootilities.GungingOotilitiesMod.exploring.ExplorerManager;
import gunging.ootilities.GungingOotilitiesMod.exploring.ItemExplorerStatement;
import gunging.ootilities.GungingOotilitiesMod.netcode.GOOMNetworkManager;
import gunging.ootilities.GungingOotilitiesMod.netcode.packets.clientbound.GMNClientboundStatementSync;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.function.Supplier;

/**
 * Asks from the server to send their statement network IDs
 *
 * @since 1.0.0
 * @author Gunging
 */
public class GMNServerboundStatementSyncRequest {

    /**
     * @since 1.0.0
     * @author Gunging
     */
    public GMNServerboundStatementSyncRequest() {}

    /**
     * @since 1.0.0
     * @author Gunging
     */
    public GMNServerboundStatementSyncRequest(@NotNull FriendlyByteBuf buff) { }

    /**
     * @param buff A buffer in which to write the bytes to send over the network
     *
     * @since 1.0.0
     * @author Gunging
     */
    public void encode(@NotNull FriendlyByteBuf buff) { }

    /**
     * Find the holder, entity, and item, and link them together
     *
     * @since 1.0.0
     * @author Gunging
     */
    public void handle(@NotNull Supplier<NetworkEvent.Context> contextSupplier) {
        contextSupplier.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.DEDICATED_SERVER, () -> () -> {

            // Identify the sender
            ServerPlayer player = contextSupplier.get().getSender();
            if (player == null) { return; }

            // Iterate all namespaces
            for (String namespace : ExplorerManager.listStatementNamespaces()) {

                // To Sync
                ArrayList<ItemExplorerStatement<?,?>> toSync = ExplorerManager.listStatements(namespace);
                if (toSync.isEmpty()) { continue; }

                // Send information
                GMNClientboundStatementSync packet = new GMNClientboundStatementSync(namespace, toSync);
                GOOMNetworkManager.serverToPlayer(player, packet);
            }
        }));
        contextSupplier.get().setPacketHandled(true);
    }
}
