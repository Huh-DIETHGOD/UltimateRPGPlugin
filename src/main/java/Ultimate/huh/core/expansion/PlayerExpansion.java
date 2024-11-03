package Ultimate.huh.core.expansion;

import Ultimate.huh.core.modify.state.intellgence.Intelligence;
import org.bukkit.entity.Player;


public interface PlayerExpansion extends Player {
    /**
     * Returns the Intelligence of the player
     */
    static Intelligence getIntelligence(Player player){
        return null;
    }

}