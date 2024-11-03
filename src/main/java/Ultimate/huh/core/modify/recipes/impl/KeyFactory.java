package Ultimate.huh.core.modify.recipes.impl;

import Ultimate.huh.core.UltimateRPGPlugin;
import org.bukkit.NamespacedKey;

public class KeyFactory {
    UltimateRPGPlugin instance = new UltimateRPGPlugin();
    public NamespacedKey generateKey(String name) {
        NamespacedKey key = new NamespacedKey(instance, name);
        return key;
    }
}
