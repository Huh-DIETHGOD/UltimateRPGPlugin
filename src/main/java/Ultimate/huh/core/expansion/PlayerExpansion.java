package Ultimate.huh.core.expansion;

import Ultimate.huh.core.state.intellgence.Intelligence;
import org.bukkit.entity.Player;


public interface PlayerExpansion extends Player {
    /**
     * Returns the Intelligence of the player
     */
    static Intelligence getIntelligence(){
        return null;
    }

}