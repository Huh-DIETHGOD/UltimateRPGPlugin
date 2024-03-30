package Ultimate.huh.core.commands;

import Ultimate.huh.core.UltimateRPGPlugin;
import Ultimate.huh.core.commands.impl.URPGCommandsFactory;
import cc.carm.lib.easysql.api.SQLManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.sql.SQLException;
import java.util.List;

public class CommandTest extends URPGCommandsFactory {
    private static final String description = "Use this command to test the stats of the plugin";

    public CommandTest() {
        super("test", description, new String[0]);
    }

    private SQLManager sqlManager;

    public void URPGCommand(@NotNull UltimateRPGPlugin plugin, @NotNull CommandSender sender, @NotNull String alias, @NotNull @Unmodifiable List<String> params) throws SQLException {
        if (sqlManager.getConnection() != null) {
            sender.sendMessage(ChatColor.AQUA + "[UltimateRPGPlugin]  " + ChatColor.GREEN + "SQL Connection established!");
        }
            sender.sendMessage(ChatColor.AQUA + "[UltimateRPGPlugin]  " + ChatColor.GREEN + "The Plugin is available now.");
            sender.sendMessage(ChatColor.AQUA + "[UltimateRPGPlugin]  " + ChatColor.GREEN + "Enjoy :)");

    }
}
