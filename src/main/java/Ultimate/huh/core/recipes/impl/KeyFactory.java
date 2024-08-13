package Ultimate.huh.core.recipes.impl;

import org.bukkit.NamespacedKey;

import java.io.File;

public class KeyFactory {
    NamespacedKey keys;
    public NamespacedKey createKeys() {
        NamespacedKey key= NamespacedKey.randomKey();
        return key;
    }
}
