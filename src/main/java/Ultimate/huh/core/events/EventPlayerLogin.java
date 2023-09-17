package Ultimate.huh.core.events;

import Ultimate.huh.core.MySQL.MySQLManager;
import Ultimate.huh.core.UltimateRPGPlugin;
import org.bukkit.Location;
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
        Location PlayerLocation = event.getPlayer().getLocation();


    }


    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
