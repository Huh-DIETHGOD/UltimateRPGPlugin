package Ultimate.huh.core.commands;

import Ultimate.huh.core.UltimateRPGPlugin;
import Ultimate.huh.core.commands.impl.URPGCommandsFactory;
import cc.carm.lib.easysql.api.SQLManager;
import cc.carm.lib.easysql.api.action.query.QueryAction;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.sql.ResultSet;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import static org.graalvm.compiler.debug.DebugOptions.Timers;

public class CommandLogin extends URPGCommandsFactory {
    private static final String description = "Use this command to login";
    public CommandLogin() {
        super("login", description, new String[0]);
    }

    SQLManager sqlManager;
    private boolean isLogin = false;
    private static UltimateRPGPlugin instance;
    private static String passwordResult;



    public void URPGCommand(@NotNull UltimateRPGPlugin plugin, @NotNull CommandSender sender, @NotNull String alias, @NotNull @Unmodifiable List<String> params) {
        String[] args = params.toArray(new String[params.size()]);
        if (isLogin == false){
            Timer(sender);
            if (args[0] == passwordResult){
                isLogin = true;
            }
        }

    }

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

            //通过query对象执行里面的查询sql
            queryAction.executeAsync(successQuery -> {
                //获取resultSet
                ResultSet resultSet1 = successQuery.getResultSet();
                //保存resultSet到线程安全的数据结构中
                ConcurrentHashMap<String, Object> concurrentHashMap = new ConcurrentHashMap<>();

                //上面的查询结果只有一个
                if (resultSet1.next()) {
                    concurrentHashMap.put("password", resultSet1.getString("password"));
                }
                    //消费查询结果
                passwordResult = concurrentHashMap.elements().toString();
                });
            return true;
    }
}