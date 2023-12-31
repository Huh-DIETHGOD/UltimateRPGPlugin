package Ultimate.huh.core.commands;

import Ultimate.huh.core.UltimateRPGPlugin;
import Ultimate.huh.core.commands.impl.URPGCommandsFactory;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;

public class CommandTest extends URPGCommandsFactory {
    public CommandTest() {
        super("test", new String[0]);
    }

    public void URPGCommand(@NotNull UltimateRPGPlugin plugin, @NotNull CommandSender sender, @NotNull String alias, @NotNull @Unmodifiable List<String> params) {
        sender.sendMessage(ChatColor.AQUA + "[UltimateRPGPlugin]  " + ChatColor.GREEN + "The Plugin is available now.");
        sender.sendMessage(ChatColor.AQUA + "[UltimateRPGPlugin]  " + ChatColor.GREEN + "Enjoy :)");
    }
}
