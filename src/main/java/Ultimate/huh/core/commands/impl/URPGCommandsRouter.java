package Ultimate.huh.core.commands.impl;

import Ultimate.huh.core.UltimateRPGPlugin;
import Ultimate.huh.core.commands.*;
import Ultimate.huh.core.utils.MsgUtil;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.*;
import java.util.stream.Stream;

public class URPGCommandsRouter implements CommandExecutor, TabCompleter {
    private static final @Unmodifiable List<URPGCommands> COMMANDS = ImmutableList.of(new CommandInfo(),
            new CommandAnnouncement(), new CommandGUI(), new CommandStorage(), new CommandTest(), new CommandRua(),
            new CommandSystemEnvironment(), new CommandEasy_SQL(), new CommandLogin(), new CommandRegister(),new CommandScoreboard());
    private final @NotNull UltimateRPGPlugin plugin;
    private final @NotNull @Unmodifiable Map<String, URPGCommands> commands;

    public URPGCommandsRouter(@NotNull UltimateRPGPlugin plugin) {
        this.plugin = plugin;
        ImmutableMap.Builder<String, URPGCommands> commands = ImmutableMap.builder();
        Iterator CommandIterator = COMMANDS.iterator();

        while(CommandIterator.hasNext()) {
            URPGCommands command = (URPGCommands)CommandIterator.next();
            command.getLabels().forEach((label) -> {
                commands.put(label, command);
            });
        }

        this.commands = commands.build();
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 0) {
            URPGCommands fallback = (URPGCommands)this.commands.get("version");
            if (fallback != null) {
                fallback.URPGCommand(this.plugin, sender, "", Collections.emptyList());
            }
            return true;

        } else {
            String search = args[0].toLowerCase(Locale.ROOT);
            URPGCommands target = (URPGCommands)this.commands.get(search);
            if (target == null) {
                MsgUtil.msg(sender, new String[]{ChatColor.AQUA + "[UltimateRPGPlugin] " + ChatColor.RED + "Unknown command " + ChatColor.YELLOW + search});
                return true;

            } else {
                String permission = target.getPermission();
                if (permission != null && !permission.isEmpty() && !sender.hasPermission(permission)) {
                    MsgUtil.msg(sender, new String[]{ChatColor.AQUA + "[UltimateRPGPlugin] " + ChatColor.RED + "You do not have permission to do this!"});
                    return true;

                } else {
                    target.URPGCommand(this.plugin, sender, search, Arrays.asList(Arrays.copyOfRange(args, 1, args.length)));
                    return true;

                }
            }
        }
    }

    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> suggestions = new ArrayList();
        if (args.length > 1) {
            URPGCommands target = (URPGCommands)this.commands.get(args[0].toLowerCase(Locale.ROOT));
            if (target != null) {
                target.complete(this.plugin, sender, args[0].toLowerCase(Locale.ROOT), Arrays.asList(Arrays.copyOfRange(args, 1, args.length)), suggestions);
            }

            return suggestions;
        } else {
            Stream<String> targets = URPGCommands.filterByPermission(sender, this.commands.values().stream()).map(URPGCommands::getLabels).flatMap(Collection::stream);
            URPGCommands.suggestByParameter(targets, suggestions, args.length == 0 ? null : args[0]);
            return suggestions;
        }
    }
}
