package Ultimate.huh.core.commands;

import Ultimate.huh.core.UltimateRPGPlugin;
import Ultimate.huh.core.commands.impl.URPGCommandsFactory;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;

public class CommandTest extends URPGCommandsFactory {
    private static final String description = "Use this command to test the stats of the plugin";
    public CommandTest() {
        super("test", description, new String[0]);
    }

    public void URPGCommand(@NotNull UltimateRPGPlugin plugin, @NotNull CommandSender sender, @NotNull String alias, @NotNull @Unmodifiable List<String> params) {
        sender.sendMessage(ChatColor.AQUA + "[UltimateRPGPlugin]  " + ChatColor.GREEN + "The Plugin is available now.");
        sender.sendMessage(ChatColor.AQUA + "[UltimateRPGPlugin]  " + ChatColor.GREEN + "Enjoy :)");
    }
}
