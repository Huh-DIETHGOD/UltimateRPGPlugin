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
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class CommandEasy_SQL extends URPGCommandsFactory {

    public CommandEasy_SQL() {
        super("sql" , new String[0]);
    }

    SQLManager sqlManager;
    Logger logger;
    private static UltimateRPGPlugin instance;


    public void URPGCommand(@NotNull UltimateRPGPlugin plugin, @NotNull CommandSender sender, @NotNull String alias, @NotNull @Unmodifiable List<String> params) {
        boolean flag = false;
        String[] args = params.toArray(new String[params.size()]);

        switch (params.get(0)) {
            case "query":
                // /urpg sql query <playerName> <colum>
                flag = executeQuery(sender, args);
                break;
            case "insert":
                // /urpg sql insert <playerName> <selection> <newData>
                flag = executeInsert(sender, args);
                break;
            case "update":
                // /urpg sql update <playerName> <selection> <newData>
                flag = executeUpdate(sender, args);
                break;
            case "delete":
                // /urpg sql delete <playerName> <selection>
                flag = executeDelete(sender, args);
                break;
            case "replace":
                // /urpg sql replace <playerName> <selection> <newData>
                flag = executeReplace(sender, args);
                break;
        }
    }

    private boolean executeQuery(CommandSender sender, String[] args) {
        if (args.length >= 2) {
            sender.sendMessage("current thread: " + Thread.currentThread().getName());
            //得到查询构建器
            QueryAction queryAction = sqlManager.createQuery()
                    //指明需要操作的table表
                    .inTable("URPGTable")
                    //指明需要操作的column字段
                    .selectColumns(args[2])
                    //搜索内容
                    .addCondition("playerName", args[1])
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
                    concurrentHashMap.put("id", resultSet1.getInt("id"));
                    concurrentHashMap.put("playerName", resultSet1.getString("playerName"));
                    concurrentHashMap.put("uuid", resultSet1.getString("uuid"));
                    concurrentHashMap.put("value", resultSet1.getInt("value"));
                }

                sender.sendMessage("EasySql创建的线程：" + Thread.currentThread().getName());
                Bukkit.getScheduler().runTask(instance, () -> {
                    sender.sendMessage("查询成功！");
                    sender.sendMessage("服务器主线程：" + Thread.currentThread().getName());

                    //消费查询结果
                    concurrentHashMap.forEach((key, value) -> {
                        sender.sendMessage(key + ":" + value);
                    });
                    sender.sendMessage("Finished!！");
                });
            });
            return true;
        }
        return false;
    }

    private boolean executeInsert(CommandSender sender, String[] args) {
        if (args.length >= 2) {
            Player player = Bukkit.getPlayer(args[1]);

            if (Objects.nonNull(player)) {
                sqlManager.createInsert("URPGTable")
                        .setColumnNames("uuid", "playerName", "value") //需要插入的字段名，顺序与下面的Params顺序一致
                        .setParams(args[1], player.getUniqueId())
                        .executeAsync(); //非查询操作不需要build，直接execute | executeAsync

                sender.sendMessage("插入成功");
                return true;
            }

            sender.sendMessage("玩家不存在");
            return true;
        }
        return false;
    }

    private boolean executeUpdate(CommandSender sender, String[] args) {
        if (args.length >= 2) {
            Player player = Bukkit.getPlayer(args[1]);
            if (Objects.nonNull(player)) {

                /**
                 * 对于不影响程序运行的运行时异常，
                 * 可以对其进行捕获，并提示。
                 */
                try {
                    //参数校验，不通过就直接捕获处理
                    Integer.parseInt(args[2]);
                } catch (NumberFormatException e) {
                    sender.sendMessage("This must be a number");
                    return true;
                }
                // key: 字段名， value: 值
                LinkedHashMap<String, Object> infoMap = new LinkedHashMap<>();
                infoMap.put("name", player.getName());
                infoMap.put("uuid", player.getUniqueId());
                infoMap.put("money", Integer.parseInt(args[2]));

                /**
                 * UPDATE player
                 * SET name = '玩家名', uuid = '玩家uuid', money = 金钱
                 * WHERE name = '玩家名'
                 */
                sqlManager.createUpdate("player")
                        .setColumnValues(infoMap)                       //实现字段映射与数据
                        .setConditions("name = '" + args[1] + "'")      //where条件，String类型的数据记得像这样用 ' 单引号包裹住，这种单纯的拼接方法不如传入键值对的那个方便
                        .build()                                        //需要build得到SQLAction再execute | executeAsync
                        .executeAsync((success) -> {
                            //操作成功回调
                            sender.sendMessage("update successfully");
                        }, (exception, sqlAction) -> {
                            //操作失败回调
                            getLogger().warning("Database exception");
                            getLogger().warning("Request statements:"  + sqlAction.getSQLContents());
                            getLogger().warning(exception.getMessage());
                        });

                return true;
            }
            sender.sendMessage("player dose not exist");
            return true;
        }
        return false;
    }

    private boolean executeDelete(CommandSender sender, String[] args) {
        if (args.length >= 2) {
            Player player = Bukkit.getPlayer(args[1]);
            sender.sendMessage("searching for the player" + args[1]);
            if (Objects.nonNull(player)) {
                /**
                 * DELETE FROM player
                 * WHERE name = '玩家名'
                 */
                sqlManager.createDelete("player")
                        .setConditions("name = '" + args[1] + "'")
                        .build()
                        .execute(null);       //同步执行，异常回调为null
//                      .executeAsync();
                sender.sendMessage("delete successfully");
                return true;
            }
            sender.sendMessage("player does not exist");
            return true;
        }
        return false;
    }

    private boolean executeReplace(CommandSender sender, String[] args) {
        return false;
    }

    public Logger getLogger() {
        return logger;
    }

    public void complete(@NotNull UltimateRPGPlugin plugin, @NotNull CommandSender sender, @NotNull String alias, @NotNull @Unmodifiable List<String> params, @NotNull List<String> suggestions) {
        suggestions = new ArrayList();
        suggestions.add("insert");
        suggestions.add("query");
        suggestions.add("update");
        suggestions.add("delete");
        suggestions.add("replace");
    }

}

