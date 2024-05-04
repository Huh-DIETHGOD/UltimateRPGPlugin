package Ultimate.huh.core.events;

import Ultimate.huh.core.UltimateRPGPlugin;
import cc.carm.lib.easysql.api.SQLManager;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerLoginEvent;
import org.jetbrains.annotations.NotNull;

public class EventPlayerLogin extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private UltimateRPGPlugin instance;

    public EventPlayerLogin(PlayerLoginEvent event) {
        String playerName = event.getPlayer().getName();
        String PlayerUUID = event.getPlayer().getUniqueId().toString();
        String player = String.valueOf(event.getPlayer());
        Location PlayerLocation = event.getPlayer().getLocation();
        SQLManager sqlManager = instance.getSqlManager();
    }


    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }
}
