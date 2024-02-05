package Ultimate.huh.core.scoreboard.impl;

import Ultimate.huh.core.UltimateRPGPlugin;
import Ultimate.huh.core.scoreboard.ScoreboardCreation;
import Ultimate.huh.core.utils.UtilScoreboard;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class ScoreboardCreationStrategyImpl implements ScoreboardCreation {
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

        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        Economy economy = UltimateRPGPlugin.getEconomy();
        double playerBalance = economy.getBalance(player);

        sidebarObjective.getScore(ChatColor.GREEN + "name: " + ChatColor.WHITE + player.getName()).setScore(6);
        sidebarObjective.getScore(ChatColor.GREEN + "Team: " + ChatColor.WHITE + "").setScore(5);
        sidebarObjective.getScore(ChatColor.AQUA + "Permission: " + (player.isOp() ? "OP" : "Player")).setScore(4);
        sidebarObjective.getScore(ChatColor.GOLD + "value: " + playerBalance).setScore(3);
        sidebarObjective.getScore(ChatColor.YELLOW + "Damage Dealt: " + player.getStatistic(Statistic.DAMAGE_DEALT)).setScore(2);
        sidebarObjective.getScore(ChatColor.GREEN + "Time: " + ChatColor.WHITE + time).setScore(1);
        sidebarObjective.getScore("").setScore(0);

        Objective nameBelowObjective = scoreboard.getObjective("name-below");

        if (Objects.isNull(nameBelowObjective)) {
            nameBelowObjective = scoreboard.registerNewObjective("name-below", Criterias.HEALTH, ChatColor.GREEN + "Health");
            nameBelowObjective.setDisplaySlot(DisplaySlot.BELOW_NAME);
            nameBelowObjective.setRenderType(RenderType.HEARTS);
        }

        Objective tableListObjective = scoreboard.getObjective("table-list");
        if (Objects.isNull(tableListObjective)) {
            tableListObjective = scoreboard.registerNewObjective("table-list", "dummy", ChatColor.AQUA + "Kills");
            tableListObjective.setDisplaySlot(DisplaySlot.PLAYER_LIST);
        }

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            tableListObjective.getScore(onlinePlayer.getName()).setScore(onlinePlayer.getStatistic(Statistic.PLAYER_KILLS));
        }

        Team TeamA = scoreboard.getTeam("TeamA");

        if (Objects.isNull(TeamA)) {
            TeamA = scoreboard.registerNewTeam("TeamA");
        }

        TeamA.setColor(ChatColor.RED);
        TeamA.setPrefix(ChatColor.RED + "[TeamA]");
        UtilScoreboard.changeTeamOptions(TeamA);

        Team TeamB = scoreboard.getTeam("TeamB");

        if (Objects.isNull(TeamB)) {
            TeamB = scoreboard.registerNewTeam("TeamB");
        }

        TeamB.setColor(ChatColor.BLUE);
        TeamB.setPrefix(ChatColor.BLUE + "[TeamB]");
        UtilScoreboard.changeTeamOptions(TeamB);

        player.setScoreboard(scoreboard);

        Team Huh = scoreboard.getTeam("Huh");
        Huh.setColor(ChatColor.AQUA);
        Huh.setPrefix(ChatColor.AQUA + "[Huh]");
        UtilScoreboard.changeTeamOptions(Huh);

        Huh.addEntry("Huh_DIETHGOD");
    }

}
