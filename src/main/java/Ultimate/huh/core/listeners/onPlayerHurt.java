package Ultimate.huh.core.listeners;


import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class onPlayerHurt implements Listener {
    private static double damage;
    private Player player;
    private Entity damageDealer;
    @EventHandler
    public void onPlayerHurt(EntityDamageByEntityEvent event){
        damage = event.getDamage();
        damageDealer = event.getDamager();
        player = (Player) event.getEntity();
    }

    public static double getDamage() {
        return damage;
    }

    public static void setDamage(double damage) {
        onPlayerHurt.damage = damage;
    }


}
