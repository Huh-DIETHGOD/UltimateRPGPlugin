package Ultimate.huh.core.modify.playerClass.impl;

import Ultimate.huh.core.UltimateRPGPlugin;
import Ultimate.huh.core.events.EventPlayerAttack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.HashMap;
import java.util.Map;

/**
 * class abilities
 */
public abstract class ClassFactory implements Player{
    private AbilityFactory abilityFactory;
    private Map<Integer, Integer> result = new HashMap<>();
    private Player player;
    private UltimateRPGPlugin instance;

    private String playerClass;

    public Map<Integer, Integer> leftClickAbility(EventPlayerAttack eventPlayerAttack) {
        player = eventPlayerAttack.getPlayer();
        playerClass = player.getPlayerClass();
        result.put(1, 1);
        return result;
    }

       /**
     * Gets the player's class.
     *
     * @param player The Player object whose class is to be retrieved
     * @return The player's class as a String
     */
    public String getPlayerClass(Player player) {
        return player.getMetadata("playerClass").get(0).asString();
    }

    /**
     * Sets the player's class.
     *
     * @param player The Player object whose class is to be set
     * @param playerClass The class to be set for the player
     */
    public void setPlayerClass(Player player, String playerClass) {
        player.setMetadata("playerClass", new FixedMetadataValue(instance, playerClass));
    }

}
