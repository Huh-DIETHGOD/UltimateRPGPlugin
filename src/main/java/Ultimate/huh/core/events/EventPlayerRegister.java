package Ultimate.huh.core.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

public class EventPlayerRegister extends PlayerEvent {
    protected Player player;

    public EventPlayerRegister(@NotNull Player player) {
        super(player);
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return null;
    }

}
