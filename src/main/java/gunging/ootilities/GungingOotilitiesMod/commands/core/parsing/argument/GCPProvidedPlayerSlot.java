package gunging.ootilities.GungingOotilitiesMod.commands.core.parsing.argument;

import gunging.ootilities.GungingOotilitiesMod.GungingOotilitiesMod;
import gunging.ootilities.GungingOotilitiesMod.commands.core.parsing.GCPProvidedArgument;
import gunging.ootilities.GungingOotilitiesMod.exploring.ItemExplorerStatement;
import gunging.ootilities.GungingOotilitiesMod.exploring.entities.ISEEquipmentSlotted;
import gunging.ootilities.GungingOotilitiesMod.exploring.players.ISPExplorerStatements;
import gunging.ootilities.GungingOotilitiesMod.exploring.players.ISPPlayerStatement;
import org.jetbrains.annotations.NotNull;

/**
 * A slot specified by the player
 *
 * @author Gunging
 * @since 1.0.0
 */
public class GCPProvidedPlayerSlot extends GCPProvidedArgument<ISPPlayerStatement> {

    /**
     * @param explicit The text provided by the user
     *
     * @author Gunging
     * @since 1.0.0
     */
    public GCPProvidedPlayerSlot(@NotNull String explicit) {
        super(explicit);

        // Parse this explorer statement
        ItemExplorerStatement<?,?> decoded = GungingOotilitiesMod.getInstance().getExplorer().decodeStatement(explicit);

        if (decoded == null) {
            setParsingError("$bExpected a slot instead of '$f" + explicit + "$b'. ");
            return; }

        // Catch non-player slots
        if (!(decoded instanceof ISPPlayerStatement)) {
            if (decoded instanceof ISEEquipmentSlotted) {
                decoded = ISPExplorerStatements.getByEquipmentSlot(((ISEEquipmentSlotted) decoded).getEquipmentSlot());
            } else {
                setParsingError("$bInvalid slot for a player '$f" + explicit + "$b'. ");
                return;
            } }

        // Set as decoded
        setParsed((ISPPlayerStatement) decoded);
    }
}
