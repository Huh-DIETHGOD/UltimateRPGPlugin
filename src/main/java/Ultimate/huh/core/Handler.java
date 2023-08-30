package Ultimate.huh.core;

import Ultimate.huh.core.scheduling.Scheduler;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public interface Handler {
    Scheduler createScheduler(@NotNull UltimateRPGPlugin Scheduler);
    Logger createLogger(@NotNull UltimateRPGPlugin logger);
    void addNewPlugin(@NotNull UltimateRPGPlugin plugin);


}
