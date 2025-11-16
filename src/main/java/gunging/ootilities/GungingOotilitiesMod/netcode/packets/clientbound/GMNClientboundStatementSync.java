package gunging.ootilities.GungingOotilitiesMod.netcode.packets.clientbound;

import gunging.ootilities.GungingOotilitiesMod.exploring.ItemExplorerStatement;
import gunging.ootilities.GungingOotilitiesMod.netcode.GOOMClientsidePacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * A packet with the capability of syncing all statements over
 * the network, at the very least their network indices.
 *
 * @since 1.0.0
 * @author Gunging
 */
public class GMNClientboundStatementSync {

    /**
     * The list that contains the statements
     *
     * @since 1.0.0
     */
    @NotNull HashMap<String, Integer> synced;

    /**
     * @since 1.0.0
     * @author Gunging
     */
    public @NotNull HashMap<String, Integer> getSynced() { return synced; }

    /**
     * @since 1.0.0
     * @author Gunging
     */
    public @NotNull String getNamespace() { return namespace; }

    /**
     * The namespace of these statements
     *
     * @since 1.0.0
     */
    @NotNull String namespace;

    /**
     * @param namespace The namespace of these statements, theoretically it
     *                  could be read from the first statement since those are
     *                  guaranteed to be the same namespace, BUT, you know, if
     *                  I put it as a separate argument it will make this even
     *                  more explicit. All these statements belong to the same
     *                  namespace!
     *
     * @param sync The list of statements to sync over the network
     *
     * @since 1.0.0
     * @author Gunging
     */
    public GMNClientboundStatementSync(@NotNull String namespace, @NotNull ArrayList<ItemExplorerStatement<?,?>> sync) {
        synced = new HashMap<>();
        for (ItemExplorerStatement<?,?> st : sync) { synced.put(st.getStatementName().getPath(), st.getNetworkIndex()); }
        this.namespace = namespace;
    }

    /**
     * @param buf The list of statements received from the network
     *
     * @since 1.0.0
     * @author Gunging
     */
    public GMNClientboundStatementSync(@NotNull FriendlyByteBuf buf) {
        synced = new HashMap<>();
        namespace = buf.readUtf();
        int count = buf.readVarInt();
        for (int i = 0; i < count; i++) {

            // Decode
            String path = buf.readUtf();
            int network = buf.readVarInt();

            // Put
            synced.put(path, network);
        }
    }

    /**
     * @param buff A buffer in which to write the bytes to send over the network
     *
     * @since 1.0.0
     * @author Gunging
     */
    public void encode(@NotNull FriendlyByteBuf buff) {

        // Append namespace
        buff.writeUtf(namespace);

        // Append list size
        buff.writeVarInt(synced.size());

        // Append each statement
        for (Map.Entry<String, Integer> st : synced.entrySet()) {

            // Encode only the path
            buff.writeUtf(st.getKey());

            // Encode network index
            buff.writeVarInt(st.getValue());
        }
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
                    GOOMClientsidePacketHandler.handleStatementSync(this, contextSupplier));
        });
        contextSupplier.get().setPacketHandled(true);
    }
}
