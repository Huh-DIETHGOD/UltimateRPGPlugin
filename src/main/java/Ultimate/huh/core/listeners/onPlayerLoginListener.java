package Ultimate.huh.core.listeners;

import Ultimate.huh.core.utils.PlayerDataSaverUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class onPlayerLoginListener implements Listener {
    @EventHandler
    public void onPlayerLoginListener(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        String PlayerName = player.getName();


        PlayerDataSaverUtil PlayerDataSaverUtil = new PlayerDataSaverUtil(player);

        if (player.getName().equals("")) {

        }

    }


}
