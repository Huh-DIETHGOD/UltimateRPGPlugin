package Ultimate.huh.core.utils;

import Ultimate.huh.core.UltimateRPGPlugin;
import Ultimate.huh.core.scoreboard.ScoreboardCreation;
import Ultimate.huh.core.scoreboard.impl.ScoreboardCreationStrategyImpl;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.Team;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class UtilScoreboard {

    /**
     * 重复执行定时任务
     */
    private static BukkitTask task;

    /**
     * 定时任务运行状态
     * 防止重复注册定时任务
     */
    private static boolean isRunning;

    /**
     * 计分板的创建策略
     */
    private static ScoreboardCreation creationStrategy;

    /**
     * 需要被更新的玩家
     */
    private static Set<String> playerSet;

    static {
        //初始化静态变量
        creationStrategy = new ScoreboardCreationStrategyImpl();      //默认采用简单的创建策略
        playerSet = new HashSet<>();
    }

    public UtilScoreboard() {
    }

    public UtilScoreboard(ScoreboardCreationStrategyImpl creationStrategy) {
        if (Objects.isNull(creationStrategy)) {
            throw new IllegalArgumentException("creationStrategy is null");
        }
        this.creationStrategy = creationStrategy;
    }

    /**
     * 设置计分板的创建策略
     * @param creationStrategy {@link ScoreboardCreationStrategyImpl} 计分板创建策略
     */
    public static boolean setScoreboardCreationStrategy(ScoreboardCreation creationStrategy) {
        if (Objects.isNull(creationStrategy)) {
            return false;   //创建策略不能为空
        }
        stopTaskTimer();
        resetUpdatePlayerMainScoreboard();                          //重置更新玩家的主计分板
        UtilScoreboard.creationStrategy = creationStrategy;         //设置计分板的创建策略
        runTaskTimer();
        return true;
    }

    /**
     * 将更新队列中的玩家计分板改为主计分板，
     * 避免在定时任务执行时因切换生成策略导致面板显示不正常
     */
    private static void resetUpdatePlayerMainScoreboard() {
        playerSet.forEach(playerName -> {
            if (validatePlayer(playerName)) {
                Player player = Bukkit.getPlayer(playerName);
                player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());        //将更新队列中的玩家计分板改为主计分板
            }
        });
    }

    /**
     * 设置玩家的计分板
     * @param player    玩家
     * @return          计分板
     */
    public static boolean setScoreboard(Player player) {
        if (Objects.isNull(player)) {
            return false;
        }
        creationStrategy.setScoreboard(player);
        return true;
    }

    /**
     * 将玩家从需要更新列表中删除
     * @param player    玩家
     */
    public static boolean removeUpdatePlayer(Player player) {
        if (Objects.nonNull(player) && playerSet.contains(player.getName())) {
            playerSet.remove(player.getName());
            return true;
        }
        return false;
    }

    /**
     * 添加一个玩家到需要更新的玩家列表
     * @param player    玩家
     */
    public static boolean addUpdatePlayer(Player player) {
        if (Objects.nonNull(player)) {
            playerSet.add(player.getName());
            return true;
        }
        return false;
    }

    /**
     * 返回更新队列中的玩家数量
     * @return  更新队列中的玩家数量
     */
    public static int sizeOfUpdatePlayerSet() {
        return playerSet.size();
    }

    /**
     * 返回玩家更新队列
     * @return  玩家更新队列
     */
    public static HashSet<String> getUpdatePlayerSet() {
        return (HashSet<String>) playerSet;
    }

    /**
     * 显示更新队列中的玩家
     * @return  更新队列中的玩家
     */
    public static String getUpdatePlayersToString() {
        return ((HashSet<String>) playerSet).toString();
    }

    /**
     * 验证该玩家名是否能匹配到一个真实玩家
     * @param playerName        玩家名
     * @return                  真实玩家
     */
    public static boolean validatePlayer(String playerName) {
        assert  Objects.nonNull(playerName) && !playerName.isEmpty();
        Player player = Bukkit.getPlayer(playerName);
        if (Objects.isNull(player) || !player.isOnline()) {
            //如果匹配不到玩家，或者玩家不在线，则返回false
            return false;
        }
        return true;
    }

    /**
     * 玩家更新队列是否为空
     * @return  是否为空
     */
    public static boolean isEmpty() {
        return playerSet.isEmpty();
    }

    /**
     * 清空队列并、停止定时任务、恢复玩家的计分板为全局计分板
     */
    public static void resetUpdateAll() {
        stopTaskTimer();
        playerSet.forEach(playerName -> {
            if (!validatePlayer(playerName)) {
                return;
            }
            Player player = Bukkit.getPlayer(playerName);
            if (Objects.nonNull(player)) {
                player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard()); //重置计分板
            }
        });
        playerSet.clear();
    }

    /**
     * 将玩家从定时任务中删除  并恢复回全局计分板
     * @param playerName    玩家名
     * @return              是否清空成功
     */
    public static boolean resetUpdatePlayer(String playerName) {
        if (!validatePlayer(playerName)) {
            return false;                       //玩家不合规则，则返回false
        }

        Bukkit.getPlayer(playerName).setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());  //将玩家的计分板重置为主计分板

        if (playerSet.isEmpty()) {
            stopTaskTimer();                    //如果此时玩家名集合为空，则停止定时任务
            return true;
        }
        if (playerSet.contains(playerName)) {
            playerSet.remove(playerName);       //如果玩家名存在，则删除
        }
        return true;
    }

    /**
     * 停止重复执行任务
     */
    public static void stopTaskTimer() {
        if (Objects.nonNull(task)) {
            task.cancel();
            task = null;
        }
        isRunning = false;
    }

    /**
     * 每过1秒就给该玩家重新创建并设置计分板
     */
    public static boolean runTaskTimer() {
        if (isRunning) {
            //正在运行，则退出
            return false;
        }
        if (playerSet.size() == 0) {
            //没有玩家，则退出
            return false;
        }

        //创建重复执行任务，并将返回的任务赋值给task
        task = Bukkit.getScheduler().runTaskTimer(UltimateRPGPlugin.getInstance(), () -> {
            //遍历玩家集合，在更新计分板的同时让玩家使用同一个计分板
            playerSet.forEach(playerName -> {
                Player player = Bukkit.getPlayer(playerName);
                if (Objects.nonNull(player) && player.isOnline()) {
                    //玩家存在，则更新计分板
                    creationStrategy.setScoreboard(player);
                } else {
                    //玩家不存在，则从玩家集合中移除
                    if (playerSet.contains(playerName)) {
                        playerSet.remove(playerName);
                    }
                }
            });
        }, 0, 20);          //每过1秒就给该玩家重新创建并设置计分板

        isRunning = true;
        return true;
    }

    public static void changeTeamOptions(Team team) {
        team.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.NEVER);
        team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
        team.setAllowFriendlyFire(false);
        team.setCanSeeFriendlyInvisibles(true);
    }
}
