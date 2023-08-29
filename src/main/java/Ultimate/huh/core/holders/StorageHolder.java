package Ultimate.huh.core.holders;

import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;

/**
 * Player's storage
 */
public class StorageHolder {
    private static Map<String, Inventory> inventoryMap = new HashMap<>();

    public static boolean contains(String PlayerName) {
        return inventoryMap.containsKey(PlayerName);
    }

    public static Inventory getInventory(String PlayerName) {
        if (contains(PlayerName)) {
            //如果map中已经存在元素
            return inventoryMap.get(PlayerName);
        } else {
            return null;
        }
    }

    public static Inventory addInventory(String PlayerName, Inventory inventory) {
        return inventoryMap.put(PlayerName, inventory);
    }

}
