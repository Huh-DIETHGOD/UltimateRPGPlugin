package Ultimate.huh.core.commands;

import Ultimate.huh.core.UltimateRPGPlugin;
import Ultimate.huh.core.commands.impl.URPGCommandsFactory;
import Ultimate.huh.core.listeners.onPlayerLoginListener;
import cc.carm.lib.easysql.api.SQLManager;
import cc.carm.lib.easysql.api.action.query.QueryAction;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.sql.ResultSet;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;


public class CommandLogin extends URPGCommandsFactory {
    private static final String description = "Use this command to login";
    public CommandLogin() {
        super("login", description, new String[0]);
    }

    SQLManager sqlManager;
    private static UltimateRPGPlugin instance;
    private static String passwordResult;
    private onPlayerLoginListener listener;


    public void URPGCommand(@NotNull UltimateRPGPlugin plugin, @NotNull CommandSender sender, @NotNull String alias, @NotNull @Unmodifiable List<String> params) {
        String[] args = params.toArray(new String[params.size()]);


        if (listener.getIsIsLoggedIn() == false){
            Timer(sender);
            if (args[0] == passwordResult){
                listener.setIsLoggedIn(true);
            }
        }

    }

    /**
     * This method creates a new timer that sends a message to the specified command sender every second for a specified number of seconds.
     *
     * @param sender the command sender to send the messages to
     */
    private static void Timer(CommandSender sender){
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                sender.sendMessage("You are not logged in yet! Type /login <your password> to login!");
            }
        }, 100, 1000);

    }

    private boolean executeQuery(CommandSender sender, String args) {
            sender.sendMessage("current thread: " + Thread.currentThread().getName());
            QueryAction queryAction = sqlManager.createQuery()
                    .inTable("URPGTable")
                    .selectColumns("password")
                    .addCondition("playerName", sender.getName())
                    .orderBy("id", false)
                    .setPageLimit(0, 5)
                    .build();

            queryAction.executeAsync(successQuery -> {
                ResultSet resultSet1 = successQuery.getResultSet();
                ConcurrentHashMap<String, Object> concurrentHashMap = new ConcurrentHashMap<>();

                if (resultSet1.next()) {
                    concurrentHashMap.put("password", resultSet1.getString("password"));
                }
                passwordResult = concurrentHashMap.elements().toString();
                });
            return true;
    }

}