package Ultimate.huh.core.listeners;

import Ultimate.huh.core.events.EventPlayerLogin;
import cc.carm.lib.easysql.api.SQLManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerLoginEvent;

import java.sql.SQLException;
import java.util.EventListener;

public class onPlayerRegister implements EventListener {
    SQLManager sqlManager;
    @EventHandler
    public void onPlayerRegister(PlayerLoginEvent event) throws SQLException {
        sqlManager.getConnection();
        sqlManager.createInsert("URPGTABLE")
                .setColumnNames();


    }

}
