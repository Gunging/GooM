package gunging.ootilities.GungingOotilitiesMod.commands.core.parsing;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A stack of arguments provided by the user
 *
 * @author Gunging
 * @since 1.0.0
 */
public class GCPArgumentStack {

    /**
     * All arguments provided by the user
     *
     * @since 1.0.0
     */
    @NotNull final GCPProvidedArgument<?>[] arguments;

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public GCPProvidedArgument<?> get(int i) {
        if (i < 0 || i >= arguments.length) { return new GCPProvidedArgument<>(""); }
        return arguments[i];
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public int size() { return arguments.length; }

    /**
     * Transforms a string array into an array of Provided Arguments
     *
     * @param args String array to transform
     *
     * @author Gunging
     * @since 1.0.0
     */
    public GCPArgumentStack(@NotNull String... args) {
        arguments = new GCPProvidedArgument[args.length];
        for (int i = 0; i < args.length; i++) {
            arguments[i] = new GCPProvidedArgument<>(args[i]);
        }
        options = new GCPContextOptions();
    }

    /**
     * @param args Array of Provided Arguments to accept
     *
     * @author Gunging
     * @since 1.0.0
     */
    public GCPArgumentStack(@NotNull GCPProvidedArgument<?>... args) {
        arguments = args;
        options = new GCPContextOptions();
    }

    /**
     * @param contrivance Last index to exclude from the result.
     *                    As in, this index will NOT be included,
     *                    only everything after it.
     *
     * @return All arguments after the specified index
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public GCPArgumentStack after(int contrivance) {

        // Empty if invalid index
        if ((contrivance + 1) >= arguments.length) { return new GCPArgumentStack(new GCPProvidedArgument[0]); }

        GCPProvidedArgument<?>[] ret = new GCPProvidedArgument[arguments.length - (contrivance + 1)];
        for (int c = (contrivance + 1); c < arguments.length; c++) {
            ret[c - contrivance - 1] = arguments[c];
        }

        return new GCPArgumentStack(ret);
    }

    /**
     * @param stack Argument Stack which arguments to adopt
     *
     * @author Gunging
     * @since 1.0.0
     */
    public GCPArgumentStack(@NotNull GCPArgumentStack stack) {
        arguments = stack.arguments;
        options = stack.options;
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public @NotNull GCPContextOptions getOptions() { return options; }

    /**
     * The options context of this command
     *
     * @since 1.0.0
     */
    @NotNull GCPContextOptions options;

    /**
     * @param options The options context of this command
     *
     * @return this same Argument Stack object
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull GCPArgumentStack withOptions(@NotNull GCPContextOptions options) { this.options.withContext(options); return this; }
}
