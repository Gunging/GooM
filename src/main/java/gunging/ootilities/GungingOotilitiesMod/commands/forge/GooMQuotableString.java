package gunging.ootilities.GungingOotilitiesMod.commands.forge;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

/**
 * It is like the {@link StringArgumentType} quotable string
 * but without the stupid restriction on no symbols.
 *
 * @author Gunging
 * @since 1.0.0
 */
public class GooMQuotableString implements ArgumentType<String> {

    /**
     * Examples to suggest for this argument
     *
     * @since 1.0.0
     */
    @NotNull ArrayList<String> examples;

    /**
     * It is like the {@link StringArgumentType} quotable string
     * but without the stupid restriction on no symbols.
     *
     * @author Gunging
     * @since 1.0.0
     */
    public GooMQuotableString() {
        examples = new ArrayList<>();
        examples.add("%placeholder%");
        examples.add("@selector");
        examples.add("word");
        examples.add("word_with_underscores");
        examples.add("\"\"");
        examples.add("\"quoted phrase with spaces\"");
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override
    public String parse(final StringReader reader) throws CommandSyntaxException {
        if (!reader.canRead()) { return ""; }

        // Remember starting position, and whether it starts quoted
        boolean isQuoted = reader.peek() == '"';
        if (isQuoted) { reader.skip(); }
        int start = reader.getCursor();

        // It will stop when finding a space
        boolean foundSpace = false;
        boolean isEscaped = false;
        int exclusion = 0;
        while (reader.canRead() && !foundSpace) {
            char obs = reader.peek();

            // When quoted, the end is at the first unescaped quote \"
            if (isQuoted) {

                // If it was escaped, special characters are ignored
                if (isEscaped) {
                    isEscaped = false;

                // Otherwise, we may evaluate special characters
                } else {
                    if (obs == '\\') { isEscaped = true; }
                    else if (obs == '"') { foundSpace = true; exclusion = 1; }
                }
                reader.skip();

            // When not quoted, the end is at the first space
            } else {
                if (obs == ' ') { foundSpace = true; } else { reader.skip(); }
            }
        }

        // That is the extract from this reader
        return reader.getString().substring(start, reader.getCursor() - exclusion);
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override public String toString() { return "string()"; }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @Override public Collection<String> getExamples() { return examples; }
}
