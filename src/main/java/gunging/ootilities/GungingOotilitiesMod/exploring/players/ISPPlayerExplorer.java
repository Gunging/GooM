package gunging.ootilities.GungingOotilitiesMod.exploring.players;

import gunging.ootilities.GungingOotilitiesMod.exploring.ItemStackExplorer;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

/**
 * ItemStack explorer meant to work with the player inventory.
 *
 * @since 1.0.0
 * @author Gunging
 */
public class ISPPlayerExplorer implements ItemStackExplorer<ISPPlayerElaborator, Player> {

    /**
     * If it is a special kind of slot code, the slot code
     *
     * @since 1.0.0
     */
    @NotNull ISPPlayerStatement specialization;

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public ISPPlayerStatement getSpecialization() { return specialization; }

    /**
     * Reference a special slot of the inventory, or family of slots
     *
     * @param slot The special code slot you are referencing
     *
     * @author Gunging
     * @since 1.0.0
     */
    public ISPPlayerExplorer(@NotNull ISPPlayerStatement slot) {
        specialization = slot;
    }

    @Override
    public @NotNull ArrayList<ItemStackExplorer<ISPPlayerElaborator, Player>> elaborate(@NotNull ISPPlayerElaborator elaborator) {
        return getSpecialization().whenElaborated(elaborator);
    }

    @Override
    public @Nullable ISPPlayerLocation realize(@NotNull ISPPlayerElaborator elaborator) {
        return getSpecialization().whenRealized(elaborator);
    }
}
