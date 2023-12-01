package Ultimate.huh.core.commands;

import Ultimate.huh.core.UltimateRPGPlugin;
import Ultimate.huh.core.commands.impl.URPGCommandsFactory;
import cc.carm.lib.easysql.api.SQLManager;
import cc.carm.lib.easysql.api.action.query.QueryAction;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;
import java.util.logging.Logger;

public class CommandRegister extends URPGCommandsFactory {
    public CommandRegister() {
        super("register", new String[0]);
    }

    private SQLManager sqlManager;
    private static UltimateRPGPlugin instance;
    private Logger logger;


    public void URPGCommand(@NotNull UltimateRPGPlugin plugin, @NotNull CommandSender sender, @NotNull String alias, @NotNull @Unmodifiable List<String> params) {
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
        } else {
            getLogger().info("你输入的密码不一致,请检查!");
        }

    }

    public Logger getLogger() {
        return logger;
    }
}
