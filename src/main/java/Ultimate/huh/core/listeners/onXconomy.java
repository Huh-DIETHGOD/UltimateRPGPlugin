package Ultimate.huh.core.listeners;

import Ultimate.huh.core.utils.UtilMsg;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import me.yic.xconomy.api.event.AccountEvent;
import me.yic.xconomy.api.event.NonPlayerAccountEvent;
import me.yic.xconomy.api.event.PlayerAccountEvent;

public class onXconomy implements Listener {
    //Listen to all transfer events (except global events)
    @EventHandler
    private void listenAccount(AccountEvent event) {
        UtilMsg.info(event.getaccountname());
        UtilMsg.info(event.getamount().toString());
    }

    //Listen to all non-player account transfer events
    @EventHandler
    private void listenNonAccount(NonPlayerAccountEvent event) {
        UtilMsg.info(event.getaccountname());
        UtilMsg.info(event.getamount().toString());
    }

    //Listen to all player account transfer events (except global events)
    @EventHandler
    private void listenPlayerAccount(PlayerAccountEvent event) {
        UtilMsg.info(event.getUniqueId().toString());
        UtilMsg.info(event.getamount().toString());
    }
}
