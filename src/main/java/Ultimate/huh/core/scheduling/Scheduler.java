package Ultimate.huh.core.scheduling;

import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

/**
 * 调度器
 */
public interface Scheduler {
    /**
     * 在指定的延迟时间后执行一次任务。
     * @param task 实现了 Runnable 接口的对象,要执行的任务
     * @param delayTime 延迟时间
     * @return
     */
    BukkitTask runLater(@NotNull Runnable task, long delayTime);

    /**
     *以指定的延迟时间和间隔时间重复执行任务。
     * @param task 实现了 Runnable 接口的对象,要执行的任务
     * @param delayTime 延迟时间
     * @param intervalTime 任务执行间隔时间
     * @return
     */
    BukkitTask runTimer(@NotNull Runnable task, long delayTime, long intervalTime);

    /**
     * 以异步方式执行指定的延迟时间和间隔时间的重复任务。
     * @param task 实现了 Runnable 接口的对象,要执行的任务
     * @param delayTime 延迟时间
     * @param intervalTime 任务执行间隔时间
     * @return
     */
    BukkitTask runAsyncTimer(@NotNull Runnable task, long delayTime, long intervalTime);

    /**
     * 在主线程中执行一次任务。
     * @param task 实现了 Runnable 接口的对象,要执行的任务
     * @return
     */
    BukkitTask run(@NotNull Runnable task);

    /**
     * 在异步线程中执行一次任务。
     * @param task 实现了 Runnable 接口的对象,要执行的任务
     * @return
     */
    BukkitTask runAsync(@NotNull Runnable task);

    /**
     * 在主线程中以指定的延迟时间和间隔时间重复执行任务。
     * @param task 实现了 Runnable 接口的对象,要执行的任务
     * @param delayTime 延迟时间
     * @param intervalTime 任务执行间隔时间
     * @return
     */
    int syncRepeating(@NotNull Runnable task, long delayTime, long intervalTime);

    /**
     * 取消所有已调度的任务。
     */
    void cancelAll();
}
