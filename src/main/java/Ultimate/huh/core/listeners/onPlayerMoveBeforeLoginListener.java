package Ultimate.huh.core.listeners;

import cc.carm.lib.easysql.api.SQLManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class onPlayerMoveBeforeLoginListener implements Listener {
    @EventHandler
    public void onPlayerMoveBeforeLogin(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        SQLManager sqlManager;
        String playerName = player.getName();
        String PlayerUUID = player.getUniqueId().toString();
    }

}
