package Ultimate.huh.core.scheduling;

import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

public interface RunnableTask extends Runnable{
    @NotNull BukkitTask runTask();

    @NotNull BukkitTask runTaskAsynchronously();

    @NotNull BukkitTask runTaskLater(long var1);

    @NotNull BukkitTask runTaskLaterAsynchronously(long var1);

    @NotNull BukkitTask runTaskTimer(long var1, long var3);

    @NotNull BukkitTask runTaskTimerAsynchronously(long var1, long var3);

    void cancel();
}
