package Ultimate.huh.core.listeners;


import Ultimate.huh.core.utils.ScoreboardUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class onPlayerQuitListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerQuit(org.bukkit.event.player.PlayerQuitEvent event) {
        //将玩家从需要更新列表中删除
        ScoreboardUtil.removeUpdatePlayer(event.getPlayer());

        //如果更新玩家列表为空，则停止定时任务
        if (ScoreboardUtil.sizeOfUpdatePlayerSet() < 1) {
            ScoreboardUtil.stopTaskTimer();
        }
    }

}
