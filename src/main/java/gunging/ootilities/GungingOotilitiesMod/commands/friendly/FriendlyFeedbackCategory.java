package gunging.ootilities.GungingOotilitiesMod.commands.friendly;

/**
 * When you must tell the user something, what kind of topic does it relate to?
 *
 * @author Gunging
 * @since 1.0.0
 */
public enum FriendlyFeedbackCategory {

    /**
     * Messages sent when the whole operation reached completion.
     *
     * @since 1.0.0
     */
    SUCCESS,

    /**
     * The list of messages concerning why the operation did not reach completion.
     * <br><br>
     * These <i>errors</i> are not really a fault of the user, in fact,
     * despite the operation not reaching completion, it is working
     * as it is supposed to (perhaps it was stopped by certain conditions),
     * and sometimes this same input produces a success.
     * <br><br>
     * Like the operation stopping due to a 'failed' RNG roll, that could
     * have been a success in a more lucky occasion.
     *
     * @see #ERROR
     *
     * @since 1.0.0
     */
    FAILURE,

    /**
     * A list of neutral messages that you think the user should know about.
     * <br><br>
     * Like something deprecated or a soft warning.
     *
     * @since 1.0.0
     */
    INFORMATION,

    /**
     * Messages concerning serious syntax errors.
     * <br><br>
     * These errors will never get fixed unless the user themselves
     * correct their input - Under no circumstances their input is
     * correct.
     * <br><br>
     * Like attempting to parse '<code>Potato</code>' as a number.
     *
     * @see #FAILURE
     *
     * @since 1.0.0
     */
    ERROR,

    /**
     * Avoid using as much as possible.
     *
     * @since 1.0.0
     */
    OTHER
}
