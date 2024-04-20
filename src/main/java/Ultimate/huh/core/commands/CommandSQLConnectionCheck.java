package Ultimate.huh.core.commands;

import Ultimate.huh.core.UltimateRPGPlugin;
import Ultimate.huh.core.commands.impl.URPGCommandsFactory;
import cc.carm.lib.easysql.api.SQLManager;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

public class CommandSQLConnectionCheck extends URPGCommandsFactory {
    SQLManager sqlManager;
    Logger logger;
    public CommandSQLConnectionCheck(@NotNull String label, String description, String... alias) {
        super("sqlcc", description, new String[0]);
    }
    public void URPGCommand(@NotNull UltimateRPGPlugin plugin, @NotNull CommandSender sender, @NotNull String alias, @NotNull @Unmodifiable List<String> params) {
        try {
            sqlManager.getConnection();
            sqlManager.createQuery()
                    .inTable("urpgtable")
                    .selectColumns("playerName","Huh_DIETHGOD")
                    .setPageLimit(1,5);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
