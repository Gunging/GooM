package gunging.ootilities.GungingOotilitiesMod.mod;

/**
 * Rules that affect the workings of this mod
 *
 * @author Gunging
 * @since 1.0.0
 */
public class GooMGamerules {

    /**
     * When a command succeeds, does it log its output?
     *
     * @since 1.0.0
     */
    boolean sendSuccessFeedback = true;

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public boolean isSendSuccessFeedback() {
        return sendSuccessFeedback;
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public void setSendSuccessFeedback(boolean sendSuccessFeedback) {
        this.sendSuccessFeedback = sendSuccessFeedback;
    }

    /**
     * When a command fails, does it log its output?
     *
     * @since 1.0.0
     */
    boolean sendFailFeedback = true;

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public boolean isSendFailFeedback() {
        return sendFailFeedback;
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public void setSendFailFeedback(boolean sendFailFeedback) {
        this.sendFailFeedback = sendFailFeedback;
    }

    /**
     * When a command errors, should we prevent it from logging the error?
     *
     * @since 1.0.0
     */
    boolean blockErrorFeedback;

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public boolean isBlockErrorFeedback() {
        return blockErrorFeedback;
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public boolean isSendErrorFeedback() {
        return !isBlockErrorFeedback();
    }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public void setBlockErrorFeedback(boolean blockErrorFeedback) {
        this.blockErrorFeedback = blockErrorFeedback;
    }
}
