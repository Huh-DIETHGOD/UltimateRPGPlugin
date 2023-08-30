package Ultimate.huh.core.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerLoginEvent;
import org.jetbrains.annotations.NotNull;

public class EventPlayerLogin extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    public EventPlayerLogin(PlayerLoginEvent event) {
        String playerName = event.getPlayer().getName();
        String PlayerUUID = event.getPlayer().getUniqueId().toString();
        String player = String.valueOf(event.getPlayer());


    }


    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
