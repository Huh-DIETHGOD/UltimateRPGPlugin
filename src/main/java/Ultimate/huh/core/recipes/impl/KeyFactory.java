package Ultimate.huh.core.recipes.impl;

import Ultimate.huh.core.UltimateRPGPlugin;
import org.bukkit.NamespacedKey;

public class KeyFactory {
    NamespacedKey keys;
    public NamespacedKey generateKey(String name) {
        UltimateRPGPlugin instance = new UltimateRPGPlugin();
        NamespacedKey key= new NamespacedKey(instance, name);
        return key;
    }
}
