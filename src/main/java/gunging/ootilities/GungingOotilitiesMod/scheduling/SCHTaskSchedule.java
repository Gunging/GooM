package gunging.ootilities.GungingOotilitiesMod.scheduling;

import org.jetbrains.annotations.NotNull;

/**
 * A wrapper for a task and when to run it
 *
 * @since 1.0.0
 * @author Gunging
 */
public class SCHTaskSchedule {

    /**
     * The task that will be run
     *
     * @since 1.0.0
     */
    @NotNull final SCHScheduled task;

    /**
     * @since 1.0.0
     * @author Gunging
     */
    public @NotNull SCHScheduled getTask() {
        return task;
    }

    /**
     * The tick when it will be run
     *
     * @since 1.0.0
     */
    final long date;

    /**
     * @since 1.0.0
     * @author Gunging
     */
    public long getDate() {
        return date;
    }

    /**
     * If this task has been resolved
     *
     * @since 1.0.0
     */
    boolean resolved;

    /**
     * @return If this task is resolved
     *
     * @since 1.0.0
     * @author Gunging
     */
    public boolean isResolved() {
        return resolved;
    }

    /**
     * Mark this task as resolved
     *
     * @since 1.0.0
     * @author Gunging
     */
    public void resolve() { resolved = true; }

    /**
     * @param task The task that will be run
     * @param date The tick when it will be run
     *
     * @since 1.0.0
     * @author Gunging
     */
    public SCHTaskSchedule(@NotNull SCHScheduled task, long date) {
        this.task = task;
        this.date = date;
    }
}
