package Ultimate.huh.core.recipes.impl;

import org.bukkit.NamespacedKey;

import java.io.File;

public class KeyFactory {
    NamespacedKey keys;
    public NamespacedKey createKeys(File file) {
        NamespacedKey key= NamespacedKey.randomKey();
        return key;
    }
}
