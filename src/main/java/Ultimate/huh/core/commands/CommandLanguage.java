package Ultimate.huh.core.commands;

import Ultimate.huh.core.UltimateRPGPlugin;
import Ultimate.huh.core.commands.impl.CommandDescription;
import Ultimate.huh.core.commands.impl.URPGCommandsFactory;
import Ultimate.huh.core.config.language.LanguageSetting;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;
import java.util.logging.Logger;

public class CommandLanguage extends URPGCommandsFactory {
    protected CommandLanguage(@NotNull String label, String... alias) {
        super("language", new String[0]);
    }

    private static UltimateRPGPlugin instance;
    private static LanguageSetting language;
    private static FileConfiguration config = instance.getConfig();
    private Logger logger;
    private static CommandDescription cd;


    public void URPGCommand(@NotNull UltimateRPGPlugin plugin, @NotNull CommandSender sender, @NotNull String alias, @NotNull @Unmodifiable List<String> params) {
        String[] args = params.toArray(new String[params.size()]);
        switch (args[1]){
        case "English":
            config.set("language", "English");
            break;
        case "Chinese":
            config.set("language", "Chinese");
            break;
        default:
            sender.sendMessage("value not found! Please check!");
            return;
        }
        language.setLanguage(args[1]);
        sender.sendMessage("Language successfully changed to" + args[1] + "!");
        sender.sendMessage("reloading plugin!");
    }
}
