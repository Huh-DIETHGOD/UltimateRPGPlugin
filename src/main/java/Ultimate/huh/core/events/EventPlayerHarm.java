package Ultimate.huh.core.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.jetbrains.annotations.NotNull;


public class EventPlayerHarm extends Event {
    EntityDamageByEntityEvent event;
    private Player player = (Player) event.getEntity();

    @NotNull
    @Override
    public HandlerList getHandlers() {
        double damage = event.getDamage();

        return null;
    }







}
