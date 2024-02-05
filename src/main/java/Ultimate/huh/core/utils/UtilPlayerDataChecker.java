package Ultimate.huh.core.utils;

import cc.carm.lib.easysql.api.SQLManager;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class UtilPlayerDataChecker {
    private SQLManager sqlManager;

    public boolean checkPlayerData(Player player) {
        try {
            String playerName = player.getName();
            sqlManager.getConnection();
            sqlManager.createQuery().withPreparedSQL("SELECT * FROM URPGTable WHERE playerName");


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
    }
}
