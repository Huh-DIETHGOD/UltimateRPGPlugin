package Ultimate.huh.core.scheduling;

import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

/**
 * 创建管理BukkitTask
 */

public interface RunnableTask extends Runnable{
    /**
     * 同步执行并返回一个 BukkitTask 对象
     * @return
     */
    @NotNull BukkitTask runTask();

    /**
     * 异步执行任务并返回一个 BukkitTask 对象
     * @return
     */
    @NotNull BukkitTask runTaskAsynchronously();

    /**
     * 延迟一段时间后同步执行任务并返回一个 BukkitTask 对象。
     * @param delayTime 延迟时间
     * @return
     */
    @NotNull BukkitTask runTaskLater(long delayTime);

    /**
     * 延迟一段时间后异步执行任务并返回一个 BukkitTask 对象。
     * @param delayTime 延迟时间
     * @return
     */
    @NotNull BukkitTask runTaskLaterAsynchronously(long delayTime);

    /**
     * 定时同步执行任务并返回一个 BukkitTask 对象。
     * @param delayTime 延迟时间
     * @param intervalTime 执行的间隔时间
     * @return
     */
    @NotNull BukkitTask runTaskTimer(long delayTime, long intervalTime);

    /**
     * 定时异步执行任务并返回一个 BukkitTask 对象。
     * @param delayTime 延迟时间
     * @param intervalTime 执行的间隔时间
     * @return
     */
    @NotNull BukkitTask runTaskTimerAsynchronously(long delayTime, long intervalTime);

    /**
     * 取消任务
     */
    void cancel();
}
