package gunging.ootilities.GungingOotilitiesMod.stats.events;

import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;
import org.jetbrains.annotations.NotNull;

/**
 * A fundamental event that fires every time an
 * entity's stats are recalculated, allowing
 * listeners to respond to the new values.
 *
 * @author Gunging
 * @since 1.0.0
 */
public class StatsRecalculatedEvent extends LivingEvent {

    /**
     * @param entity Entity whose stats were recalculated
     *
     * @author Gunging
     * @since 1.0.0
     */
    public StatsRecalculatedEvent(@NotNull LivingEntity entity) {
        super(entity);
    }
}
