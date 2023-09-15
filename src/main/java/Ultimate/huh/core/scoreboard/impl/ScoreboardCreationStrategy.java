package Ultimate.huh.core.scoreboard.impl;

import Ultimate.huh.core.UltimateRPGPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.Objects;

public class ScoreboardCreationStrategy implements ScoreboardCreation{
    private UltimateRPGPlugin instance;
    @Override
    public void setScoreboard(Player player) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = manager.getNewScoreboard();
        if (scoreboard.equals(manager.getMainScoreboard())) {
            scoreboard = manager.getNewScoreboard();
        }

        Objective sidebarObjective = scoreboard.getObjective("side-bar");
        if (Objects.nonNull(sidebarObjective)) {
         sidebarObjective.unregister();
        }


        sidebarObjective = scoreboard.registerNewObjective("side-bar", "dummy", ChatColor.AQUA + "Player Information");
        sidebarObjective.setDisplaySlot(DisplaySlot.SIDEBAR);

        sidebarObjective.getScore(ChatColor.GREEN + "Player name: " + ChatColor.WHITE + player.getName()).setScore(6);
        sidebarObjective.getScore(ChatColor.AQUA + "Player Permission: " + (player.isOp() ? "OP" : "Player")).setScore(5);
        sidebarObjective.getScore(ChatColor.GOLD + "Player coins: " + ).setScore(4);
        sidebarObjective.getScore(ChatColor.YELLOW + "Player Damage Dealt: " + player.getStatistic(Statistic.DAMAGE_DEALT)).setScore(3);
        sidebarObjective.getScore().setScore(2);
        sidebarObjective.getScore().setScore(1);
        sidebarObjective.getScore().setScore(0);



    }

}
