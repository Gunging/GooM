package gunging.ootilities.GungingOotilitiesMod.netcode;

import gunging.ootilities.GungingOotilitiesMod.GungingOotilitiesMod;
import gunging.ootilities.GungingOotilitiesMod.exploring.ExplorerManager;
import gunging.ootilities.GungingOotilitiesMod.instants.GOOMClientsidePlayerLoginEvent;
import gunging.ootilities.GungingOotilitiesMod.mixininterfaces.WithStatsStack;
import gunging.ootilities.GungingOotilitiesMod.netcode.packets.clientbound.GMNClientLoginRequest;
import gunging.ootilities.GungingOotilitiesMod.netcode.packets.clientbound.GMNClientboundInherentStatsEntity;
import gunging.ootilities.GungingOotilitiesMod.netcode.packets.clientbound.GMNClientboundStatementSync;
import gunging.ootilities.GungingOotilitiesMod.netcode.packets.clientbound.GMNClientboundMomentum;
import gunging.ootilities.GungingOotilitiesMod.stats.core.StatStack;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

/**
 * The class that handles the Clientbound packets sent from the server
 *
 * @since 1.0.0
 * @author Gunging
 */
public class GOOMClientsidePacketHandler {

    /**
     * @return The world where this network context is taking place.
     *
     * @since 1.0.0
     * @author Gunging
     */
    @Nullable public static Level getContextWorld(@NotNull Supplier<NetworkEvent.Context> contextSupplier) {
        Level world = Minecraft.getInstance().level;
        if (world == null) {
            if (Minecraft.getInstance().player == null) { return null; }
            world = Minecraft.getInstance().player.level(); }
        return world;
    }

    /**
     * @param syncing Information to sync statement network indices
     * @param contextSupplier The network context by which this is taking place
     *
     * @since 1.0.0
     * @author Gunging
     */
    public static void handleInherentStatsEntity(@NotNull GMNClientboundInherentStatsEntity syncing, @NotNull Supplier<NetworkEvent.Context> contextSupplier) {

        // Identify
        Level world = getContextWorld(contextSupplier);
        if (world == null) { return; }
        LivingEntity target = syncing.getInherentEntity(world);
        if (target == null) { return; }

        // Apply provided stats
        WithStatsStack asStats = (WithStatsStack) target;
        StatStack asStack = asStats.gungingoom$getStatStack();
        asStack.deserializeInherent(syncing.getSerializedInherent(), null);
        asStack.getRefreshedStatTotals();
    }

    /**
     * @param syncing Information to sync statement network indices
     * @param contextSupplier The network context by which this is taking place
     *
     * @since 1.0.0
     * @author Gunging
     */
    public static void handleStatementSync(@NotNull GMNClientboundStatementSync syncing, @NotNull Supplier<NetworkEvent.Context> contextSupplier) {
        GungingOotilitiesMod.getInstance().getExplorer().receiveNetworkSync(syncing);
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

        // Basic Pos and Delta
        local.setPos(thrown.getPosition());
        local.setDeltaMovement(thrown.getVelocity());

        // Rubberbanding and Smoothing
        local.xo = thrown.getPosition().x;
        local.yo = thrown.getPosition().y;
        local.zo = thrown.getPosition().z;
        local.xOld = thrown.getPosition().x;
        local.yOld = thrown.getPosition().y;
        local.zOld = thrown.getPosition().z;
        local.fallDistance = 0;
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
