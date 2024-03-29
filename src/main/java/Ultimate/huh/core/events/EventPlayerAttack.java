package Ultimate.huh.core.events;

import jdk.jfr.Event;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class EventPlayerAttack extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private boolean cancel = false;
    private Entity entity;


    public EventPlayerAttack(@NotNull final Player player, @NotNull final Entity entity) {
        super();
        this.entity = entity;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public void setCancelled(boolean cancel) {

    }
}
