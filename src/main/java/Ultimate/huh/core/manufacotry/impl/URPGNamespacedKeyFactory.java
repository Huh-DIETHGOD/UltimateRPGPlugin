package Ultimate.huh.core.manufacotry.impl;

import Ultimate.huh.core.PluginDependent;
import Ultimate.huh.core.UltimateRPGPlugin;
import Ultimate.huh.core.manufacotry.NamespaceFactory;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;

public final class URPGNamespacedKeyFactory extends PluginDependent<UltimateRPGPlugin> implements NamespaceFactory {

    public URPGNamespacedKeyFactory(UltimateRPGPlugin plugin) {
        super(plugin);
    }

    @Override
    public NamespacedKey create(@NotNull String namespace) {
        NamespacedKey nsk = new NamespacedKey(getPlugin(), namespace);
        return nsk;
    }
}
