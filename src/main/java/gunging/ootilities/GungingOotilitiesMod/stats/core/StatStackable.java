package gunging.ootilities.GungingOotilitiesMod.stats.core;

import gunging.ootilities.GungingOotilitiesMod.commands.friendly.FriendlyFeedbackProvider;
import gunging.ootilities.GungingOotilitiesMod.ootilityception.OotilityNumbers;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

/**
 * Something with the capacity to provide stat instances.
 *
 * @author Gunging
 * @since 1.0.0
 */
public interface StatStackable {

    /**
     * The stats provided by this stat stack
     * specifically and not any of its children.
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull HashMap<String, StatInstance<?>> getInherentStats();

    /**
     * Sets the specified inherent stat, marks this
     * and its parent (up to the root parent) as
     * having received changes.
     *
     * @param stat Stat instance to set/insert
     *
     * @author Gunging
     * @since 1.0.0
     */
    void setStat(@NotNull StatInstance<?> stat);

    /**
     * Sets the specified inherent stat, marks this
     * and its parent (up to the root parent) as
     * having received changes.
     *
     * @param statDefinition Stat definition to set
     * @param data Value for this stat
     *
     * @author Gunging
     * @since 1.0.0
     */
    <M> void setStat(@NotNull StatDefinition<M> statDefinition, @Nullable StatValue<? extends M> data);

    /**
     * @return The inherent stats contained in here, serialized.
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull default String serializeInherent() {
        return serializeInherent(false);
    }

    /**
     * @param forClientboundNetwork If these will be sent over the network to clients,
     *                              in which case we do not need to send server-sided stats
     *                              and the dirty network map will be sent instead.
     *
     * @return The inherent stats contained in here, serialized.
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull default String serializeInherent(boolean forClientboundNetwork) {
        StringBuilder ret = new StringBuilder();
        boolean first = true;

        // Choose which map to send
        HashMap<String, StatInstance<?>> map = forClientboundNetwork ? getDirtyInherent() : getInherentStats();

        // Add every stat
        for (StatInstance<?> stat : map.values()) {
            if (forClientboundNetwork && stat.getDefinition().isServerSided()) { continue; }

            // No need to save stats with the default value
            if (!forClientboundNetwork && stat.isDefault()) { continue; }

            // Semicolon as separator
            if (first) { first = false; } else { ret.append(OotilityNumbers.SERIALIZATION_SEPARATOR); }

            // STAT_DEFINITION=<VALUE>
            ret.append(OotilityNumbers.escapeForSerialization(stat.serializeFull()));
        }

        // Return result
        return ret.toString();
    }

    /**
     * This undoes {@link #serializeInherent()}
     *
     * @param serialized The serialized stats to read and include in the inherent list
     *
     * @author Gunging
     * @since 1.0.0
     */
    default void deserializeInherent(@Nullable String serialized, @Nullable FriendlyFeedbackProvider ffp) {
        if (serialized == null) { return; }
        if (serialized.isEmpty()) { return; }

        // Split by separator
        for (String chunk : OotilityNumbers.split(serialized, OotilityNumbers.SERIALIZATION_SEPARATOR)) {
            if (chunk.isEmpty()) { return; }

            // Attempt to parse
            StatInstance<?> parsed = StatInstance.deserializeFull(OotilityNumbers.unescapeFromSerialization(chunk), ffp);
            if (parsed == null) { continue; }

            // Include in inherent if parsed
            setStat(parsed);
        }
    }

    /**
     * The characteristic stats contained herein
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull HashMap<String, StatInstance<?>> getCharacteristicTotals();

    /**
     * Consider recalculating totals if there are changes,
     * then return the latest characteristic totals for real.
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull HashMap<String, StatInstance<?>> getRefreshedCharacteristicTotals();

    /**
     * Inherent stat changes accumulated since last
     * time they were sent over the network.
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull HashMap<String, StatInstance<?>> getDirtyInherent();
}
