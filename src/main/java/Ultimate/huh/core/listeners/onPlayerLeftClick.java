package Ultimate.huh.core.listeners;

import Ultimate.huh.core.modify.playerClass.MageClass.AbilityLaserBeam;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class onPlayerLeftClick implements Listener {
    AbilityLaserBeam laserBeam;

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction().toString().contains("LEFT_CLICK")) { // 检查是否为左键点击
            Player player = event.getPlayer();
            laserBeam.laser();
        }
    }

}
