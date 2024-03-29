package Ultimate.huh.core.commands;

import Ultimate.huh.core.UltimateRPGPlugin;
import Ultimate.huh.core.commands.impl.URPGCommandsFactory;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;

public class CommandAnnouncement extends URPGCommandsFactory {
    private static final String description = "";

    public CommandAnnouncement() {
        super("announce", description, new String[0]);
    }

    public void URPGCommand(@NotNull UltimateRPGPlugin plugin, @NotNull CommandSender sender, @NotNull String alias, @NotNull @Unmodifiable List<String> params) {
        Bukkit.getServer().broadcastMessage(ChatColor.AQUA + "[UltimateRPGPlugin]" + ChatColor.YELLOW + " Announcement:\n"
                + params.get(0));
    }
}
