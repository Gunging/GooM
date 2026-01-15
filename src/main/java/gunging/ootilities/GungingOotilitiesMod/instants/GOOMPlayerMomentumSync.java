package gunging.ootilities.GungingOotilitiesMod.instants;

import gunging.ootilities.GungingOotilitiesMod.netcode.GOOMNetworkManager;
import gunging.ootilities.GungingOotilitiesMod.netcode.packets.clientbound.GMNClientboundMomentum;
import gunging.ootilities.GungingOotilitiesMod.ootilityception.APIFriendlyProcess;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Call on a player to send them their server position and velocity information again
 *
 * @since 1.0.0
 * @author Gunging
 */
public class GOOMPlayerMomentumSync implements APIFriendlyProcess {

    /**
     * The player to which set their position and momentum
     *
     * @since 1.0.0
     */
    @NotNull Player who;

    /**
     * @since 1.0.0
     * @author Gunging
     */
    @NotNull public Player getWho() { return who; }

    /**
     * @param who The player to which set their position and momentum
     *
     * @since 1.0.0
     * @author Gunging
     */
    public GOOMPlayerMomentumSync(@NotNull Player who) {
        this.who = who;
    }

    @Override public boolean isVerified() {

        // Should only be used from the server bruh
        return who instanceof ServerPlayer;
    }

    @Override public boolean isAllowed() { return true; }

    @Override public void resolve() {

        /*
         * (1) Just build the packet and send it
         */
        GMNClientboundMomentum momentum = new GMNClientboundMomentum(who.position(), who.getDeltaMovement());
        GOOMNetworkManager.serverToPlayer((ServerPlayer) who, momentum);

        /*
         * (2) Stick the values in the ServerSide as well
         */
        who.xo = who.position().x;
        who.yo = who.position().y;
        who.zo = who.position().z;
        who.xOld = who.position().x;
        who.yOld = who.position().y;
        who.zOld = who.position().z;
        who.fallDistance = 0;
    }
}
