package gunging.ootilities.GungingOotilitiesMod.ootilityception;

/**
 * Represents an action that the program will perform.
 * <p>
 * You may build these in many different ways with the various
 * constructors, then you can check with {@link #isVerified()}
 * if the information is complete for a valid operation to be carried
 * out, then use {@link #isAllowed()} to check if the conditions to
 * execute it are favourable (including sending a cancelable event
 * for it to the event bus), and finally {@link #resolve()} to actually
 * carry it out - if it was allowed, then it was verified, and thus
 * it must always succeed basically.
 *
 * @since 1.0.0
 * @author Gunging
 */
public interface APIFriendlyProcess {

    /**
     * @return Checks if this process has all the information
     *         it needs to be carried out successfully.
     *
     * @since 1.0.0
     * @author Gunging
     */
    boolean isVerified();

    /**
     * Assumes that you already checked that {@link #isVerified()} returns true.
     *
     * @return Checks that the conditions by which this process
     *         may complete are present. Basically, even if it has
     *         all the information to be carried out, the context
     *         may reject it.
     *         <p>
     *         It is recommended to run cancellable events here for
     *         other developers to hook into, and thus decide if this
     *         process is allowed.
     *
     * @since 1.0.0
     * @author Gunging
     */
    boolean isAllowed();

    /**
     * Executes this process.
     * <p>
     * Assumes that {@link #isAllowed()} returned true, but you
     * know, that one is just a suggestion. You may go ahead
     * and call this to force the process to happen regardless
     * of it being allowed.
     * <p>
     * It also assumes {@link #isVerified()} returned true.
     *
     * @since 1.0.0
     * @author Gunging
     */
    void resolve();

    /**
     * A basic implementation of the three methods of this class
     *
     * @return If this method was verified, allowed, and resolved
     *
     * @since 1.0.0
     * @author Gunging
     */
    default boolean tryResolve() {
        if (!isVerified()) { return false; }
        if (!isAllowed()) { return false; }
        resolve();
        return true;
    }
}
