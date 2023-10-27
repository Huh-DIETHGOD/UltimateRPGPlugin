package Ultimate.huh.core.manufacotry;

import Ultimate.huh.core.manufacotry.impl.MetaDataValueFactory;
import org.bukkit.NamespacedKey;
import org.bukkit.metadata.MetadataValue;

public interface URPGFactory {
    NamespacedKey createNamespacedKey(String namespacedKey);

    MetadataValue createMetaData(String metadataValue);

}
