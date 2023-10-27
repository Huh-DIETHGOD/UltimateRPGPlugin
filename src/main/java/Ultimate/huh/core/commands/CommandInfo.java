package Ultimate.huh.core.commands;

import Ultimate.huh.core.UltimateRPGPlugin;
import Ultimate.huh.core.commands.impl.URPGCommandsFactory;
import Ultimate.huh.core.utils.MsgUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;

public class CommandInfo extends URPGCommandsFactory {
    public CommandInfo() {
        super("info", new String[0]);
    }

    public void URPGCommand(@NotNull UltimateRPGPlugin plugin, @NotNull CommandSender sender, @NotNull String alias, @NotNull @Unmodifiable List<String> params) {
        PluginDescriptionFile description = plugin.getDescription();
        MsgUtil.msg(sender, ChatColor.AQUA + "[UltimateRPGPlugin]" + ChatColor.GREEN + "\n" +
                "--------------------------------\n" +
                "You are using " + description.getName() + description.getVersion() + "\n" +
                "Author:" + description.getAuthors() + "\n" +
                "--------------------------------" );
    }

    public void complete(@NotNull UltimateRPGPlugin plugin, @NotNull CommandSender sender, @NotNull String alias, @NotNull @Unmodifiable List<String> params, @NotNull List<String> suggestions) {
    }

}
