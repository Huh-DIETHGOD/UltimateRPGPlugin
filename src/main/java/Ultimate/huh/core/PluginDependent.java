package Ultimate.huh.core;

import org.jetbrains.annotations.NotNull;

public abstract class PluginDependent<T extends UltimateRPGPlugin> {
    private final T plugin;

    protected PluginDependent(@NotNull T plugin) {
        this.plugin = plugin;
    }

    protected T getPlugin() {
        return this.plugin;
    }
}