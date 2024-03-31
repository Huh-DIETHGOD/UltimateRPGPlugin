package Ultimate.huh.core.commands;

import Ultimate.huh.core.UltimateRPGPlugin;
import Ultimate.huh.core.commands.impl.URPGCommandsFactory;
import Ultimate.huh.core.gui.GUIInventory;
import Ultimate.huh.core.listeners.onPlayerLoginListener;
import cc.carm.lib.easysql.api.SQLManager;
import cc.carm.lib.easysql.api.action.query.QueryAction;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;
import java.util.logging.Logger;

public class CommandRegister extends URPGCommandsFactory {
    private static final String description = "Use this command to register a account";
    public CommandRegister() {
        super("register",description,  new String[0]);
    }

    private SQLManager sqlManager;
    private static UltimateRPGPlugin instance;
    private onPlayerLoginListener listener;

    public void URPGCommand(@NotNull UltimateRPGPlugin plugin, @NotNull CommandSender sender, @NotNull String alias, @NotNull @Unmodifiable List<String> params) {
        Player player = (Player) sender;
        QueryAction queryAction = sqlManager.createQuery()
                .inTable("URPGTable")
                .selectColumns("playerName")
                .addCondition("playerName")
                .orderBy("id",false)
                .setPageLimit(1, 5)
                .build();
        queryAction.executeAsync();

        String password = params.get(1);
        String verifyPassword = params.get(2);
        if (password.equals(verifyPassword)){
            sqlManager.createInsert("URPGTable")
                    .setColumnNames("playerName", "password")
                    .setParams(sender.getName(), params.get(1).toLowerCase())
                    .executeAsync();
            sender.sendMessage("Welcome!");
            listener.setIsLoggedIn(true);
        } else {
            sender.sendMessage("Password do not match, please check!");
        }

        Bukkit.createInventory(player, 54, ChatColor.BLACK + player.getName() + "'s State");


    }

}
