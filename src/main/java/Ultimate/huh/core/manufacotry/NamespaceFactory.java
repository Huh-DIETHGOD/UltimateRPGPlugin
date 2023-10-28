package Ultimate.huh.core.manufacotry;

import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;

public interface NamespaceFactory {
     NamespacedKey create(@NotNull String namespace);
}
