
package gunging.ootilities.GungingOotilitiesMod.exploring.entities;

import gunging.ootilities.GungingOotilitiesMod.exploring.ItemExplorerElaborator;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

/**
 * An Item Stack Elaborator that provides an entity. It might
 * be a player, but not necessarily. This will still work for
 * players, but it may only really access their Armor, Mainhand,
 * and Offhand items.
 *
 * @author Gunging
 * @since 1.0.0
 */
public class ISEEntityElaborator implements ItemExplorerElaborator<Entity> {

    /**
     * The player provided to elaborate the Item Explorer Statement
     *
     * @since 1.0.0
     */
    @NotNull Entity entity;

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull
    public Entity getEntity() { return entity; }

    /**
     * @param who Entity provided to elaborate the Item Explorer Statement
     *
     * @author Gunging
     * @since 1.0.0
     */
    public ISEEntityElaborator(@NotNull Entity who) { entity = who; }

    @Override public @NotNull Entity getElaborator() { return getEntity(); }
}