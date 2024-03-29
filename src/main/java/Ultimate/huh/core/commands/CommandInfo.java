package Ultimate.huh.core.commands;

import Ultimate.huh.core.UltimateRPGPlugin;
import Ultimate.huh.core.commands.impl.URPGCommandsFactory;
import Ultimate.huh.core.utils.UtilMsg;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;

public class CommandInfo extends URPGCommandsFactory {
    private static final String description = "Use this command to get information about the plugin";
    public CommandInfo() {
        super("info", description, new String[0]);
    }

    public void URPGCommand(@NotNull UltimateRPGPlugin plugin, @NotNull CommandSender sender, @NotNull String alias, @NotNull @Unmodifiable List<String> params) {
        PluginDescriptionFile pluginDescription = plugin.getDescription();
        UtilMsg.msg(sender, ChatColor.AQUA + "[UltimateRPGPlugin]" + ChatColor.GREEN + "\n" +
                "--------------------------------\n" +
                "You are using " + pluginDescription.getName() + pluginDescription.getVersion() + "\n" +
                "Author:" + pluginDescription.getAuthors() + "\n" +
                "--------------------------------" );
    }

    public void complete(@NotNull UltimateRPGPlugin plugin, @NotNull CommandSender sender, @NotNull String alias, @NotNull @Unmodifiable List<String> params, @NotNull List<String> suggestions) {
    }

}
