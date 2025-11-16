package gunging.ootilities.GungingOotilitiesMod.netcode;

import gunging.ootilities.GungingOotilitiesMod.GungingOotilitiesMod;
import gunging.ootilities.GungingOotilitiesMod.exploring.ExplorerManager;
import gunging.ootilities.GungingOotilitiesMod.instants.GOOMClientsidePlayerLoginEvent;
import gunging.ootilities.GungingOotilitiesMod.netcode.packets.clientbound.GMNClientLoginRequest;
import gunging.ootilities.GungingOotilitiesMod.netcode.packets.clientbound.GMNClientboundStatementSync;
import gunging.ootilities.GungingOotilitiesMod.netcode.packets.clientbound.GMNClientboundMomentum;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

/**
 * The class that handles the Clientbound packets sent from the server
 *
 * @since 1.0.0
 * @author Gunging
 */
public class GOOMClientsidePacketHandler {

    /**
     * @param syncing Information to sync statement network indices
     * @param contextSupplier The network context by which this is taking place
     *
     * @since 1.0.0
     * @author Gunging
     */
    public static void handleStatementSync(@NotNull GMNClientboundStatementSync syncing, @NotNull Supplier<NetworkEvent.Context> contextSupplier) {
        ExplorerManager.receiveNetworkSync(syncing);
    }

    /**
     * @param thrown Momentum to sync across the network
     * @param contextSupplier The network context by which this is taking place
     *
     * @since 1.0.0
     * @author Gunging
     */
    public static void handleMomentumSync(@NotNull GMNClientboundMomentum thrown, @NotNull Supplier<NetworkEvent.Context> contextSupplier) {
        Player local = Minecraft.getInstance().player;
        if (local == null) { return; }
        local.setPos(thrown.getPosition());
        local.setDeltaMovement(thrown.getVelocity());
    }



    /**
     * @param request Empty packet probably just to trigger the login event
     * @param contextSupplier The network context by which this is taking place
     *
     * @since 1.0.0
     * @author Gunging
     */
    public static void handleLoginRequest(@NotNull GMNClientLoginRequest request, @NotNull Supplier<NetworkEvent.Context> contextSupplier) {
        Player local = Minecraft.getInstance().player;
        if (local == null) { return; }

        GOOMClientsidePlayerLoginEvent event = new GOOMClientsidePlayerLoginEvent(local);
        MinecraftForge.EVENT_BUS.post(event);
    }


}
