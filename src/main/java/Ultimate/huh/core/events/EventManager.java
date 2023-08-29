package Ultimate.huh.core.events;

import org.bukkit.event.Listener;

public interface EventManager {
    void registerListener(Listener Listener);

    void unregisterListener(Listener Listener);

    void unregisterAllListeners();
}
