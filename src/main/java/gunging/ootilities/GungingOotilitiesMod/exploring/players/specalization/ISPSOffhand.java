package gunging.ootilities.GungingOotilitiesMod.exploring.players.specalization;

import gunging.ootilities.GungingOotilitiesMod.exploring.ItemStackExplorer;
import gunging.ootilities.GungingOotilitiesMod.exploring.players.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

/**
 * The offhand slot of the player.
 *
 * @author Gunging
 * @since 1.0.0
 */
public class ISPSOffhand extends ISPEquipmentStatement {

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @NotNull String getOptions() { return ""; }

    /**
     * @param statementName The internal name of this explorer statement
     *
     * @author Gunging
     * @since 1.0.0
     */
    public ISPSOffhand(@NotNull ResourceLocation statementName) {
        super(statementName);
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override public @Nullable ISPSOffhand withOptions(@NotNull String options) {
        return ISPExplorerStatements.OFFHAND;
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override public @NotNull EquipmentSlot getEquipmentSlot() { return EquipmentSlot.OFFHAND; }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @NotNull ArrayList<ItemStackExplorer<ISPPlayerElaborator, Player>> whenElaborated(@NotNull ISPPlayerElaborator elaborator) {
        ArrayList<ItemStackExplorer<ISPPlayerElaborator, Player>> ret = new ArrayList<>();

        ret.add(new ISPPlayerExplorer(ISPExplorerStatements.OFFHAND));

        return ret;
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @Nullable ISPPlayerLocation whenRealized(@NotNull ISPPlayerElaborator elaborator) {
        return new ISPPlayerLocation(elaborator.getPlayer(), this);
    }
}
