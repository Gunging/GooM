package gunging.ootilities.GungingOotilitiesMod.exploring;

import org.jetbrains.annotations.NotNull;

/**
 * This is the physical existing thing that we
 * will be exploring, it represents the actual
 * instance of something in the world that we
 * are about to explore.
 * <p></p>
 * For example, if you sought the "ec20" 20th
 * slot of an enderchest, you must provide a
 * Player object as the elaborator in order to
 * access that ender chest.
 *
 * @see ItemExplorerStatement
 *
 * @author Gunging
 * @since 1.0.0
 */
public interface ItemExplorerElaborator<E> {

    /**
     * @return The holder or container that will be used to
     *         resolve Item Stack exploration.
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull E getElaborator();
}
