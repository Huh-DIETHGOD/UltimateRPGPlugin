package Ultimate.huh.core;

import Ultimate.huh.core.events.EventManager;
import Ultimate.huh.core.scheduling.Scheduler;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public interface Handler {
    Scheduler createScheduler(@NotNull UltimateRPGPlugin var1);
    EventManager createEventManager(@NotNull UltimateRPGPlugin var1);
    Logger createLogger(@NotNull UltimateRPGPlugin var1);
    void addNewPlugin(@NotNull UltimateRPGPlugin var1);


}
