package Ultimate.huh.core.listeners;

import Ultimate.huh.core.utils.UtilPlayerDataSaver;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class onPlayerLoginListener implements Listener {
    private static boolean isLoggedIn = false;
    @EventHandler
    public void onPlayerLoginListener(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        String PlayerName = player.getName();
        player.setWalkSpeed(0);



        if(isLoggedIn == false){
        }


        UtilPlayerDataSaver PlayerDataSaverUtil = new UtilPlayerDataSaver(player);

        if (player.getName().equals("")) {

        }

    }


}
