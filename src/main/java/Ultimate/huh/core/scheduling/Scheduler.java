package Ultimate.huh.core.scheduling;

import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

public interface Scheduler {
    BukkitTask runLater(@NotNull Runnable var1, long var2);

    BukkitTask runTimer(@NotNull Runnable var1, long var2, long var4);

    BukkitTask runAsyncTimer(@NotNull Runnable var1, long var2, long var4);

    BukkitTask run(@NotNull Runnable var1);

    BukkitTask runAsync(@NotNull Runnable var1);

    int syncRepeating(@NotNull Runnable var1, long var2, long var4);

    void cancelAll();
}
