package gunging.ootilities.GungingOotilitiesMod.stats.definitions;

import gunging.ootilities.GungingOotilitiesMod.commands.friendly.FriendlyFeedbackProvider;
import gunging.ootilities.GungingOotilitiesMod.ootilityception.OotilityNumbers;
import gunging.ootilities.GungingOotilitiesMod.stats.core.StatDefinition;
import gunging.ootilities.GungingOotilitiesMod.stats.core.StatValue;
import gunging.ootilities.GungingOotilitiesMod.stats.values.StringListStat;
import gunging.ootilities.GungingOotilitiesMod.stats.values.StringStat;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a metric that expects a list of strings
 *
 * @author Gunging
 * @since 1.0.0
 */
public class StringListDefinition extends StatDefinition<List<String>> {

    /**
     * @param definitionID The unique identifier of this stat
     * @param def          The default value of this stat
     *
     * @author Gunging
     * @since 1.0.0
     */
    public StringListDefinition(@NotNull String definitionID, @NotNull StatValue<? extends List<String>> def) {
        super(definitionID, def);
    }

    /**
     * @param definitionID The unique identifier of this stat
     * @param def          The default value of this stat
     *
     * @author Gunging
     * @since 1.0.0
     */
    public StringListDefinition(@NotNull String definitionID, @NotNull ArrayList<String> def) { super(definitionID, new StringListStat(def)); }


    /**
     * @param definitionID The unique identifier of this stat
     *
     * @author Gunging
     * @since 1.0.0
     */
    public StringListDefinition(@NotNull String definitionID) { super(definitionID, new StringListStat()); }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @NotNull StatValue<? extends List<String>> merge(@NotNull StatValue<? extends List<String>> current, @NotNull StatValue<?> incoming) {
        if (!accepts(incoming)) { return current; }

        // Single string added
        if (incoming.getValue() instanceof String) {
            StringListStat ret = new StringListStat(current.getValue());
            ret.getValue().add((String) incoming.getValue());
            return ret;
        }

        // List concatenation
        StringListStat ret = new StringListStat(current.getValue());
        ret.getValue().addAll((List<String>) incoming.getValue());
        return ret;
    }

    /**
     * Accepts any number
     *
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public boolean accepts(@NotNull StatValue<?> value) {
        if (value.getValue() instanceof String) { return true; }
        return super.accepts(value);
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public boolean isDefault(@NotNull StatValue<? extends List<String>> value) {

        // Most likely this will be the case
        if (getDefault().getValue().isEmpty()) { return value.getValue().isEmpty(); }

        // Otherwise match size
        if (getDefault().getValue().size() != value.getValue().size()) { return false; }

        // No match on the first entry that mismatches
        for (String inDef : getDefault().getValue()) {
            boolean found = false;
            for (String inVal : value.getValue()) { if (inDef.equals(inVal)) { found = true; break; } }
            if (!found) { return false; }
        }

        // All entries matched
        return true;
    }

    /**
     * @return The first entry in this list yay
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull String getFirst(@NotNull StatValue<? extends List<String>> current) {
        if (current.getValue().isEmpty()) { return ""; }
        return current.getValue().get(0);
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @Nullable StatValue<? extends List<String>> operation(@Nullable StatValue<? extends List<String>> current, @Nullable String operation, @Nullable FriendlyFeedbackProvider ffp) {

        // Immediate fail when no operation is provided
        if (operation == null) { FriendlyFeedbackProvider.logError(ffp, "Value $fnot$b provided. "); return null; }
        StatValue<? extends List<String>> old = current == null ? getDefault() : current;
        StatValue<? extends List<String>> ret = old.clone();

        // Are we removing a list entry?
        if (operation.startsWith("-")) {
            ret.getValue().remove(operation.substring(1));

        // Just adding
        } else { ret.getValue().add(operation); }

        // Done
        return ret;
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @NotNull String whenSerialized(@NotNull StatValue<? extends List<String>> current) {
        StringBuilder ret = new StringBuilder();
        boolean first = true;
        for (String content : current.getValue()) {
            if (first) { first = false; } else { ret.append(OotilityNumbers.SERIALIZATION_SEPARATOR); }
            ret.append(OotilityNumbers.escapeForSerialization(content)); }
        return ret.toString();
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public @Nullable StatValue<? extends List<String>> whenDeserialized(@NotNull String serialized, @Nullable FriendlyFeedbackProvider ffp) {
        ArrayList<String> escaped = OotilityNumbers.split(serialized, OotilityNumbers.SERIALIZATION_SEPARATOR);
        ArrayList<String> ret = new ArrayList<>();
        for (String content : escaped) { ret.add(OotilityNumbers.unescapeFromSerialization(content)); }
        return new StringListStat(ret);
    }
}
