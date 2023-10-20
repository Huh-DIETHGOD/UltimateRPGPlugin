package Ultimate.huh.core.listeners;

import Ultimate.huh.core.utils.MsgUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import me.yic.xconomy.api.event.AccountEvent;
import me.yic.xconomy.api.event.NonPlayerAccountEvent;
import me.yic.xconomy.api.event.PlayerAccountEvent;

public class onXconomy implements Listener {
    //监听所有转账事件（全局事件除外）
    //Listen to all transfer events (except global events)
    @EventHandler
    private void listenAccount(AccountEvent event) {
        MsgUtil.info(event.getaccountname());
        MsgUtil.info(event.getamount().toString());
    }

    //监听所有非玩家账户转账事件
    //Listen to all non-player account transfer events
    @EventHandler
    private void listenNonAccount(NonPlayerAccountEvent event) {
        MsgUtil.info(event.getaccountname());
        MsgUtil.info(event.getamount().toString());
    }

    //监听所有玩家账户转账事件（全局事件除外）
    //Listen to all player account transfer events (except global events)
    @EventHandler
    private void listenPlayerAccount(PlayerAccountEvent event) {
        MsgUtil.info(event.getUniqueId().toString());
        MsgUtil.info(event.getamount().toString());
    }
}
