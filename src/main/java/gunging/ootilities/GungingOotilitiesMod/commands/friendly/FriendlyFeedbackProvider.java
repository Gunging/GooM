package gunging.ootilities.GungingOotilitiesMod.commands.friendly;

import gunging.ootilities.GungingOotilitiesMod.GungingOotilitiesMod;
import gunging.ootilities.GungingOotilitiesMod.mod.GooMGamerules;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * In our case, us developers, Java throws {@link Exception}s at us when we fuck something up.
 * Normal users don't need to be blasted with such technical messages, but they also
 * make mistakes.
 * <br><br>
 * This interface is meant to provide a better user experience by telling
 * the user why their input has failed with easier implementation in our side (not having
 * to check the input before trying to use it elsewhere).
 * <br><br>
 * This is designed with <b>commands</b> in mind, where the only user input is ultimately
 * {@link String}s, and the messages users can receive are console lines or chat messages.
 *
 * @author Gunging
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class FriendlyFeedbackProvider {

    //region Main
    /**
     * The palette used by this Friendly Feedback Provider
     *
     * @since 1.0.0
     */
    @NotNull FriendlyFeedbackPalette brandPalette;

    /**
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public FriendlyFeedbackPalette getPalette() { return brandPalette; }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public void setPalette(@NotNull FriendlyFeedbackPalette palette) { brandPalette = palette; }

    /**
     * @param palette The palette used by this Friendly Feedback Provider
     *
     * @author Gunging
     * @since 1.0.0
     */
    public FriendlyFeedbackProvider(@NotNull FriendlyFeedbackPalette palette) { brandPalette = palette; }

    /**
     * The gamerules effective for this feedback provider
     * If not set, the default gamerules will be used {@link GungingOotilitiesMod#getGamerules()}
     *
     * @since 1.0.0
     */
    @Nullable GooMGamerules overridingGamerules;

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public void setOverridingGamerules(@Nullable GooMGamerules gamerules) { overridingGamerules = gamerules; }

    /**
     * @return The gamerules effective for this feedback provider
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public GooMGamerules getGamerules() { return overridingGamerules == null ? GungingOotilitiesMod.getInstance().getGamerules() : overridingGamerules; }

    /**
     * A mode where instead of several collections of categories, only one message is used
     *
     * @since 1.0.0
     */
    boolean conciseMode;

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public boolean isConciseMode() { return conciseMode; }

    /**
     * @author Gunging
     * @since 1.0.0
     */
    public void setConciseMode(boolean conciseMode) { this.conciseMode = conciseMode; }
    //endregion

    //region Collecting Messages
    /**
     * The messages collected by this Friendly Feedback Provider
     *
     * @since 1.0.0
     */
    @NotNull HashMap<FriendlyFeedbackCategory, ArrayList<FriendlyFeedbackMessage>> feedback = new HashMap<>();

    /**
     * Alternate mode where everything is just one message and logging simply concatenates.
     *
     * @since 1.0.0
     */
    @Nullable FriendlyFeedbackMessage conciseFeedback;

    /**
     * Get the feedback of this category that has been registered.
     * <br><br>
     * Will return an empty array if no messages have been issued concerning this topic.
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public ArrayList<FriendlyFeedbackMessage> getFeedbackOf(@NotNull FriendlyFeedbackCategory category) {

        // Make sure it is registered
        return feedback.computeIfAbsent(category, k -> new ArrayList<>());
    }

    /**
     * Clears the feedback of this category that has been registered.
     *
     * @author Gunging
     * @since 1.0.0
     */
    public void clearFeedbackOf(@NotNull FriendlyFeedbackCategory category) {

        // Make sure it is registered
        feedback.put(category, new ArrayList<>());
    }

    /**
     * Clears all the feedback stored in this FFP.
     *
     * @author Gunging
     * @since 1.0.0
     */
    public void clearFeedback() { feedback.clear(); }

    /**
     * Include a message to be sent later under this category.
     * <b>This does not actually send a message</b>
     * <br><br>
     * Fails silently if the message is <code>null</code> or empty.
     * <br><br>
     * The first <code>$b</code> is included. Check out the codes
     * accepted in the description of {@link FriendlyFeedbackPalette}
     *
     * @param category The category to register this message to
     * @param message The message to register to this Friendly Feedback Provider
     * @param replaces The (ordered) list of string variables to be replaced.
     *                 <br><br>
     *                 Suppose your <code>message</code> is
     *                 <b><code>"Your input $i{0}$b is not a number!"</code></b>
     *                 <br><br>
     *                 This means that the first element of the array will be
     *                 inserted in the place of that <code>{0}</code>.
     *
     * @author Gunging
     * @since 1.0.0
     */
    public void log(@NotNull FriendlyFeedbackCategory category, @Nullable String message, String... replaces) {

        // Cancel if null
        if (message == null) { return; }
        if (message.isEmpty()) { return; }

        // Add, simple
        FriendlyFeedbackMessage logMessage = getMessage(message, replaces);
        getFeedbackOf(category).add(logMessage);

        // Add to concise feedback
        if (conciseMode) {

            // Merge prefix settings
            if (conciseFeedback == null) { conciseFeedback = getMessage(""); }
            if (prefixSample.hasPrefix()) { conciseFeedback.usesPrefix(true); }
            if (conciseFeedback.getSubdivision() == null) { conciseFeedback.setSubdivision(prefixSample.getSubdivision()); }

            // Merge message
            conciseFeedback.setMessage(conciseFeedback.getMessage() + logMessage.getMessage());
        }
    }

    /**
     * Gets a message from these arguments, applying prefix if needed! <br>
     * (Won't include palette yet, that is applied immediately before actually sending)
     *
     * @param message The message to register to this Friendly Feedback Provider
     * @param replaces The (ordered) list of string variables to be replaced.
     *                 <br><br>
     *                 Suppose your <code>message</code> is
     *                 <b><code>"Your input $i{0}$b is not a number!"</code></b>
     *                 <br><br>
     *                 This means that the first element of the array will be
     *                 inserted in the place of that <code>{0}</code>.
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public FriendlyFeedbackMessage getMessage(@NotNull String message, String... replaces) {

        // That's the result
        return generateMessage(prefixSample, message, replaces);
    }

    /**
     * Shorthand for: <br><code>if (ffp != null) { ffp.Log(category, message, replaces);</code>
     * <br><br>
     * To read what this actually does, the description is in {@link #log(FriendlyFeedbackCategory, String, String...)}
     * <br><br>
     * <b>It is convention to end all your logs in a period and a space</b> so that, if anything else gets added on top,
     * we don't have to worry about it looking like this:
     * <br><code>You messed that up man, try againEncountered error at file 3.Give me the plant</code>
     * <br><br>
     * Instead of like this:
     * <br><code>You messed that up man, try again. Encountered error at file 3. Give me the plant</code>
     *
     * @param ffp FriendlyFeedbackProvided that may be null.
     *
     * @author Gunging
     * @since 1.0.0
     */
    public static void log(@Nullable FriendlyFeedbackProvider ffp, @NotNull FriendlyFeedbackCategory category, @Nullable String message, String... replaces) { if (ffp != null) { ffp.log(category, message, replaces); } }

    @NotNull FriendlyFeedbackMessage prefixSample = new FriendlyFeedbackMessage("");
    /**
     * Call this method to make incoming messages acquire a prefix of your choosing.
     * <br><br>
     * The prefix is added as soon as the message is registered, so you may change
     * (or remove) the prefix afterward for new messages without messing with old
     * ones.
     *
     * @param usePrefix Whether to actually use prefix
     * @param subdivision A subdivision to add to the prefix
     *
     * @author Gunging
     * @since 1.0.0
     */
    public void activatePrefix(boolean usePrefix, @Nullable String subdivision) {

        // If used
        prefixSample.usesPrefix(usePrefix);
        prefixSample.setSubdivision(subdivision);
    }

    /**
     * @return How many messages are in this category
     *
     * @param ofCategory Category to count
     *
     * @author Gunging
     * @since 1.0.0
     */
    public int messagesTotal(@NotNull FriendlyFeedbackCategory ofCategory) { return getFeedbackOf(ofCategory).size(); }

    /**
     * @return How many messages are in this FriendlyFeedbackProvider
     *
     * @author Gunging
     * @since 1.0.0
     */
    public int messagesTotal() { int t = 0; for (FriendlyFeedbackCategory cat : FriendlyFeedbackCategory.values()) { t+= messagesTotal(cat); } return t; }
    //endregion

    //region Sending Messages
    /**
     * @param player Player to send the concise message to
     *
     * @author Gunging
     * @since 1.0.0
     */
    public void sendConciseTo(@NotNull FriendlyComponentReceiver player) {

        // Just send the concise message that is here
        if (conciseFeedback == null) { return; }
        player.receive(conciseFeedback.forPlayer(getPalette()));
    }
    /**
     * @param player Player to send all these messages to
     *
     * @author Gunging
     * @since 1.0.0
     */
    public void sendAllTo(@NotNull FriendlyComponentReceiver player) {

        // Just send all categories
        for (FriendlyFeedbackCategory cat : feedback.keySet()) { sendTo(cat, player); }
    }
    /**
     * @param player Player to send all these messages to
     * @param category Category of messages to send
     *
     * @author Gunging
     * @since 1.0.0
     */
    public void sendTo(@NotNull FriendlyFeedbackCategory category, @NotNull FriendlyComponentReceiver player) {

        // Get List and foreach
        for (FriendlyFeedbackMessage msg : getFeedbackOf(category)) { player.receive(msg.forPlayer(getPalette())); }
    }
    /**
     * @param console Console to send the concise message to
     *
     * @author Gunging
     * @since 1.0.0
     */
    public void sendConciseToConsole(@NotNull FriendlyStringReceiver console) {

        // Just send all categories
        if (conciseFeedback == null) { return; }
        console.receive(conciseFeedback.forConsole(getPalette()));
    }
    /**
     * @param console Console to send all these messages to
     *
     * @author Gunging
     * @since 1.0.0
     */
    public void sendAllToConsole(@NotNull FriendlyStringReceiver console) {

        // Just send all categories
        for (FriendlyFeedbackCategory cat : feedback.keySet()) { sendToConsole(cat, console); }
    }
    /**
     * @param console Console to send all these messages to
     * @param category Category of messages to send
     *
     * @author Gunging
     * @since 1.0.0
     */
    public void sendToConsole(@NotNull FriendlyFeedbackCategory category, @NotNull FriendlyStringReceiver console) {

        // Get List and foreach
        for (FriendlyFeedbackMessage msg : getFeedbackOf(category)) { console.receive(msg.forConsole(getPalette())); }
    }
    //endregion

    //region Ease-Of-Use Utils
    /**
     * Generates a {@link FriendlyFeedbackMessage} from the string and args you provide.
     * Remember that the first <code>$b</code> is included.
     *
     * @param message Actual message you are sending.
     *                <br><br>
     *                For example:
     *                <br><code>Hey! You forgot your $e{0}$b! Come back!</code>
     *
     * @param replaces What to replace each variable of the message with.
     *                 <br><br>
     *                 For example:
     *                 <br><code>Lunchbox</code> (That, as the index zero of this array, will replace the variable <code>{0}</code>)
     *
     * @return A wrapped message. Colors have not parsed yet (Notice that you didn't even specify a palette).
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public static FriendlyFeedbackMessage generateMessage(@NotNull String message, @Nullable String... replaces) {

        // That's the result
        return generateMessage(null, message, replaces);
    }

    /**
     * Generates a {@link FriendlyFeedbackMessage} from the string and args you provide.
     * Remember that the first <code>$b</code> is included.
     *
     * @param prefixTemplate A dummy, empty message with the following information:
     *                       <br> > Will the message be prefixed?
     *                       <br> > Which subdivision to put in the prefix?
     *
     * @param message Actual message you are sending.
     *                <br><br>
     *                For example:
     *                <br><code>Hey! You forgot your $e{0}$b! Come back!</code>
     *
     * @param replaces What to replace each variable of the message with.
     *                 <br><br>
     *                 For example:
     *                 <br><code>Lunchbox</code> (That, as the index zero of this array, will replace the variable <code>{0}</code>)
     *
     * @return A message with prefix information. Colors have not parsed yet (Notice that you didn't even specify a palette).
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public static FriendlyFeedbackMessage generateMessage(@Nullable FriendlyFeedbackMessage prefixTemplate, @NotNull String message, @Nullable String... replaces) {

        // Fresh (non-prefixed) message if unspecified.
        if (prefixTemplate == null) { prefixTemplate = new FriendlyFeedbackMessage(""); }

        // Bake message
        if (replaces != null) {
            for (int i = 0; i < replaces.length; i++) {
                String rep = replaces[i];
                if (rep == null) { rep = ""; }
                message =
                        message.
                                replace(
                                        "{" + i + "}",
                                        rep
                                );
            }
        }

        // Build with prefix
        FriendlyFeedbackMessage msg = prefixTemplate.clone();
        msg.setMessage("$b" + message);

        // That's the result
        return msg;
    }

    /**
     * Straight up get the styled message ready to be sent to the console!
     * <br><br>
     * Only parses {@link FriendlyFeedbackPalette} style codes, you may parse other
     * color codes afterward.
     *
     * @param palette Palette to style the color codes of the message.
     *
     * @param message Actual message you are sending.
     *                <br><br>
     *                For example:
     *                <br><code>Hey! You forgot your $e{0}$b! Come back!</code>
     * @param replaces What to replace each variable of the message with.
     *                 <br><br>
     *                 For example:
     *                 <br><code>Lunchbox</code> (That, as the index zero of this array, will replace the variable <code>{0}</code>)
     *
     * @return A message ready to be sent to the console (or a pre 1.16 client that supports no HEX codes).
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public static String quickForConsole(@NotNull FriendlyFeedbackPalette palette, @NotNull String message, @Nullable String... replaces) {

        // Generate
        FriendlyFeedbackMessage msg = generateMessage(null, message, replaces);

        // Style and send
        return msg.forConsole(palette);
    }

    /**
     * Straight up get the styled message ready to be sent to a player!
     * <br><br>
     * Only parses {@link FriendlyFeedbackPalette} style codes, you may parse other
     * color codes afterward.
     *
     * @param palette Palette to style the color codes of the message.
     *
     * @param message Actual message you are sending.
     *                <br><br>
     *                For example:
     *                <br><code>Hey! You forgot your $e{0}$b! Come back!</code>
     *
     * @param replaces What to replace each variable of the message with.
     *                 <br><br>
     *                 For example:
     *                 <br><code>Lunchbox</code> (That, as the index zero of this array, will replace the variable <code>{0}</code>)
     *
     * @return A message ready to be sent to a player. As always, if mc version is less than 1.16,
     *         it instead delegates to {@link #quickForConsole(FriendlyFeedbackPalette, String, String...)} which
     *         is assumed t have no HEX codes.
     *
     * @author Gunging
     * @since 1.0.0
     */
    @NotNull public static MutableComponent quickForPlayer(@NotNull FriendlyFeedbackPalette palette, @NotNull String message, @Nullable String... replaces) {

        // Generate
        FriendlyFeedbackMessage msg = generateMessage(null, message, replaces);

        // Style and send
        return msg.forPlayer(palette);
    }

    /**
     * @param ffp The Friendly Feedback Provider into which to log this error
     * @param message The message to send to the user in category {@link FriendlyFeedbackCategory#ERROR}
     * @param replaces The replaces to include in the message instead of {0}, {1}, ...{N}
     *
     * @author Gunging
     * @since 1.0.0
     */
    public static void logError(@Nullable FriendlyFeedbackProvider ffp, @Nullable String message, @Nullable String... replaces) {
        if (message == null) { return; }
        if (ffp != null && ffp.getGamerules().isSendErrorFeedback()) {
            ffp.log(FriendlyFeedbackCategory.ERROR, message, replaces); }
    }
    /**
     * @param ffp The Friendly Feedback Provider into which to log this win
     * @param message The message to send to the user in category {@link FriendlyFeedbackCategory#SUCCESS}
     * @param replaces The replaces to include in the message instead of {0}, {1}, ...{N}
     *
     * @author Gunging
     * @since 1.0.0
     */
    public static void logSuccess(@Nullable FriendlyFeedbackProvider ffp, @Nullable String message, @Nullable String... replaces) {
        if (message == null) { return; }
        if (ffp != null && ffp.getGamerules().isSendSuccessFeedback()) {
            ffp.log(FriendlyFeedbackCategory.SUCCESS, message, replaces); }
    }
    /**
     * @param ffp The Friendly Feedback Provider into which to log this fail
     * @param message The message to send to the user in category {@link FriendlyFeedbackCategory#FAILURE}
     * @param replaces The replaces to include in the message instead of {0}, {1}, ...{N}
     *
     * @author Gunging
     * @since 1.0.0
     */
    public static void logFailure(@Nullable FriendlyFeedbackProvider ffp, @Nullable String message, @Nullable String... replaces) {
        if (message == null) { return; }
        if (ffp != null && ffp.getGamerules().isSendFailFeedback()) {
            ffp.log(FriendlyFeedbackCategory.FAILURE, message, replaces); }
    }
    /**
     * @param ffp The Friendly Feedback Provider into which to log this info
     * @param message The message to send to the user in category {@link FriendlyFeedbackCategory#INFORMATION}
     * @param replaces The replaces to include in the message instead of {0}, {1}, ...{N}
     *
     * @author Gunging
     * @since 1.0.0
     */
    public static void logInfo(@Nullable FriendlyFeedbackProvider ffp, @Nullable String message, @Nullable String... replaces) {
        if (message == null) { return; }
        if (ffp != null) { ffp.log(FriendlyFeedbackCategory.INFORMATION, message, replaces); }
    }
    //endregion
}
