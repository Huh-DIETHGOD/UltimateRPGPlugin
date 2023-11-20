package Ultimate.huh.core.commands;

import Ultimate.huh.core.UltimateRPGPlugin;
import Ultimate.huh.core.manufacotry.URPGCommandsFactory;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;

public class CommandRua extends URPGCommandsFactory {
    public CommandRua() {
        super("rua", new String[0]);
    }

    public void URPGCommand(@NotNull UltimateRPGPlugin plugin, @NotNull CommandSender sender, @NotNull String alias, @NotNull @Unmodifiable List<String> params) {
        sender.sendMessage(ChatColor.AQUA + "[UltimateRPGPlugin] \n" + ChatColor.AQUA +
                "             ＿＿\n" +
                "          ／ ＞  フ\n" +
                "          |  _  _ l\n" +
                "        ／` ミ＿xノ\n" +
                "       /        |\n" +
                "      /  ヽ    ﾉ\n" +
                "     │   |  |   |\n" +
                "  ／￣|    |  |  |\n" +
                "  | (￣ヽ＿_ヽ_)__)\n" +
                "  ＼二つ");
        sender.sendMessage(ChatColor.AQUA + "[UltimateRPGPlugin] " + ChatColor.GREEN + "Cute Cat");
    }
}
