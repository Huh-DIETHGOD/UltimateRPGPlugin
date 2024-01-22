package Ultimate.huh.core.commands;

import Ultimate.huh.core.UltimateRPGPlugin;
import Ultimate.huh.core.commands.impl.CommandDescription;
import Ultimate.huh.core.commands.impl.URPGCommandsFactory;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.junit.runner.Description;

import java.util.List;

public class CommandHelp extends URPGCommandsFactory {
    private final String description = "Use 'help' command to get a list of commands and their usage";
    protected CommandHelp(@NotNull String label, String description , String... alias) {
        super("help", description, new String[0]);
    }
    private static UltimateRPGPlugin instance;
    private static CommandDescription cd;

    public void URPGCommand(@NotNull UltimateRPGPlugin plugin, @NotNull CommandSender sender, @NotNull String alias, @NotNull @Unmodifiable List<String> params) {
        String[] args = params.toArray(new String[params.size()]);
        switch (args[1]){
            case "":

            case "help":
                sender.sendMessage(this.getDescription());
                break;
            case "sql":
                sender.sendMessage("");
                break;
            case "language":

                break;
            case "register":

                break;
            case "login":

                break;
            case "test":

                break;
            case "systemenvironment":

                break;
            case "announcement":


                break;
        }



    }

}