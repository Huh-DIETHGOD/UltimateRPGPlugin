package Ultimate.huh.core.utils;

import Ultimate.huh.core.PluginDependent;
import Ultimate.huh.core.UltimateRPGPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;

public class UtilUpdateChecker extends PluginDependent<UltimateRPGPlugin> {
    public UtilUpdateChecker(@NotNull UltimateRPGPlugin plugin) {
        super(plugin);
    }

    public void getVersion(@NotNull Consumer<? super String> consumer) {
        this.getPlugin().getScheduler().runAsync(()-> {
            try {
                InputStream inputStream = (new URL("" + this.getPlugin().getResourceId())).openStream();
                Scanner scanner = new Scanner(inputStream);

                if (scanner.hasNext()) {
                    consumer.accept(scanner.next());
                }

            } catch (IOException e) {
                this.getPlugin().getLogger().warning("Failed to check for updates: " + e.getMessage());
            }

        });

    }
}
