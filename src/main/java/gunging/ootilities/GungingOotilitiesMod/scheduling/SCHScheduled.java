package gunging.ootilities.GungingOotilitiesMod.scheduling;

/**
 * For use with the GooM Scheduler, a task ran after a certain number of ticks.
 *
 * @since 1.0.0
 * @author Gunging
 */
@FunctionalInterface
public interface SCHScheduled {

    /**
     * For use with the GooM Scheduler, the task to run
     * when the specified number of ticks have elapsed.
     *
     * @since 1.0.0
     * @author Gunging
     */
    void runGOOMTask();
}
