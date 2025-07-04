package gunging.ootilities.GungingOotilitiesMod.exploring.players;

import gunging.ootilities.GungingOotilitiesMod.exploring.ItemExplorerElaborator;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

/**
 * The most fundamental ItemStack slot elaborator is one that provides
 * a player. Most applications deal with the player inventory, after all,
 * then a player is required to fully realize these slots.
 *
 * @author Gunging
 * @since 1.0.0
 */
public class ISPPlayerElaborator implements ItemExplorerElaborator<Player> {

    /**
     * The player provided to elaborate the ItemStack slot
     *
     * @since 1.0.0
     */
    @NotNull Player player;

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public Player getPlayer() { return player; }

    /**
     * @param who Player provided to elaborate the ItemStack slot
     * @author Gunging
     * @since 1.0.0
     */
    public ISPPlayerElaborator(@NotNull Player who) { player = who; }

    /**
     * @since 1.0.0
     * @author Gunging
     */
    @Override public @NotNull Player getElaborator() {return getPlayer(); }
}