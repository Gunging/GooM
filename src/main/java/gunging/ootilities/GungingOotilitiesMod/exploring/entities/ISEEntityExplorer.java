package gunging.ootilities.GungingOotilitiesMod.exploring.entities;

import gunging.ootilities.GungingOotilitiesMod.exploring.ItemStackExplorer;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

/**
 * ItemStack Explorer meant to target an Entity.
 *
 * @since 1.0.0
 * @author Gunging
 */
public class ISEEntityExplorer implements ItemStackExplorer<ISEEntityElaborator, Entity> {

    /**
     * The explore statement
     *
     * @since 1.0.0
     */
    @NotNull ISEEntityStatement specialization;

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public ISEEntityStatement getSpecialization() { return specialization; }

    /**
     * Reference a special slot of the inventory, or family of slots
     *
     * @param slot The special code slot you are referencing
     *
     * @author Gunging
     * @since 1.0.0
     */
    public ISEEntityExplorer(@NotNull ISEEntityStatement slot) {
        specialization = slot;
    }

    @Override
    public @NotNull ArrayList<ItemStackExplorer<ISEEntityElaborator, Entity>> elaborate(@NotNull ISEEntityElaborator elaborator) {
        return getSpecialization().whenElaborated(elaborator);
    }

    @Override
    public @Nullable ISEEntityLocation realize(@NotNull ISEEntityElaborator elaborator) {
        return getSpecialization().whenRealized(elaborator);
    }
}
