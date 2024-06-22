package Ultimate.huh.core.commands.impl;

import Ultimate.huh.core.UltimateRPGPlugin;
import Ultimate.huh.core.commands.*;
import Ultimate.huh.core.utils.UtilMsg;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Stream;

public class URPGCommandsRouter implements CommandExecutor, TabCompleter {
    private static final @Unmodifiable List<URPGCommandsFactory> COMMANDS = ImmutableList.of(new CommandInfo(),
            new CommandAnnouncement(), new CommandGUI(), new CommandStorage(), new CommandTest(), new CommandRua(),
            new CommandSystemEnvironment(), new CommandSQL(), new CommandLogin(), new CommandRegister(),new CommandScoreboard());
    private final @NotNull UltimateRPGPlugin plugin;
    private final @NotNull @Unmodifiable Map<String, URPGCommandsFactory> commands;

    public URPGCommandsRouter(@NotNull UltimateRPGPlugin plugin) {
        this.plugin = plugin;
        ImmutableMap.Builder<String, URPGCommandsFactory> commands = ImmutableMap.builder();
        Iterator CommandIterator = COMMANDS.iterator();

        while(CommandIterator.hasNext()) {
            URPGCommandsFactory command = (URPGCommandsFactory)CommandIterator.next();
            command.getLabels().forEach((label) -> {
                commands.put(label, command);
            });
        }

        this.commands = commands.build();
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 0) {
            URPGCommandsFactory fallback = (URPGCommandsFactory)this.commands.get("version");
            if (fallback != null) {
                try {
                    fallback.URPGCommand(this.plugin, sender, "", Collections.emptyList());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            return true;

        } else {
            String search = args[0].toLowerCase(Locale.ROOT);
            URPGCommandsFactory target = (URPGCommandsFactory)this.commands.get(search);
            if (target == null) {
                UtilMsg.msg(sender, new String[]{ChatColor.AQUA + "[UltimateRPGPlugin] " + ChatColor.RED + "Unknown command " + ChatColor.YELLOW + search});
                return true;

            } else {
                String permission = target.getPermission();
                if (permission != null && !permission.isEmpty() && !sender.hasPermission(permission)) {
                    UtilMsg.msg(sender, new String[]{ChatColor.AQUA + "[UltimateRPGPlugin] " + ChatColor.RED + "You do not have permission to do this!"});
                    return true;

                } else {
                    try {
                        target.URPGCommand(this.plugin, sender, search, Arrays.asList(Arrays.copyOfRange(args, 1, args.length)));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    return true;

                }
            }
        }
    }

    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> suggestions = new ArrayList();

        if (args.length > 1) {
            URPGCommandsFactory target = (URPGCommandsFactory)this.commands.get(args[0].toLowerCase(Locale.ROOT));
            if (target != null) {
                target.complete(this.plugin, sender, args[0].toLowerCase(Locale.ROOT), Arrays.asList(Arrays.copyOfRange(args, 1, args.length)), suggestions);
            }

            return suggestions;
        } else {
            Stream<String> targets = URPGCommandsFactory.filterByPermission(sender, this.commands.values().stream()).map(URPGCommandsFactory::getLabels).flatMap(Collection::stream);
            URPGCommandsFactory.suggestByParameter(targets, suggestions, args.length == 0 ? null : args[0]);

            return suggestions;
        }
    }
}
