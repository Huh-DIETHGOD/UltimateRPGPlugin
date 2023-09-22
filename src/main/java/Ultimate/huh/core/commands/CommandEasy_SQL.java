package Ultimate.huh.core.commands;

import Ultimate.huh.core.UltimateRPGPlugin;
import Ultimate.huh.core.commands.impl.URPGCommands;
import cc.carm.lib.easysql.api.SQLManager;
import cc.carm.lib.easysql.api.action.query.QueryAction;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class CommandEasy_SQL extends URPGCommands {
    protected CommandEasy_SQL(@NotNull String label, String... alias) {
        super("sql", new String[0]);
    }
    SQLManager sqlManager;

    Logger logger;
    private static UltimateRPGPlugin instance;


    public void URPGCommand(@NotNull UltimateRPGPlugin plugin, @NotNull CommandSender sender, @NotNull String alias, @NotNull @Unmodifiable List<String> params) {
        boolean flag = false;
        String[] args = params.toArray(new String[params.size()]);
        if (params.size() >= 0) {
            switch (params.get(0)) {
                case "query":
                    // /urpg sql query <playerName>
                    flag = executeQuery(sender, args);
                    break;
                case "insert":
                    // /urpg sql insert <playerName>
                    flag = executeInsert(sender, args);
                    break;
                case "update":
                    // /urpg sql update <playerName> <money>
                    flag = executeUpdate(sender, args);
                    break;
                case "delete":
                    // /urpg sql delete <playerName>
                    flag = executeDelete(sender, args);
                    break;
            }
        }
    }

    private boolean executeQuery(CommandSender sender, String[] args) {
        if (args.length >= 2) {
            sender.sendMessage("查询前线程：" + Thread.currentThread().getName());
            /**
             * SELECT id, name, uuid, money
             * FROM player
             * WHERE name = 'args[1]的值'
             * ORDER BY id DESC
             * LIMIT 0, 5
             */
            //得到查询构建器
            QueryAction queryAction = sqlManager.createQuery()
                    //指明需要操作的table表
                    .inTable("player")
                    //指明需要操作的column字段
                    .selectColumns("id", "name", "uuid", "money")
                    //添加where查询条件
                    .addCondition("name", args[1])
                    //还支持字符串拼接的形式，以及多次调用#addCondition来拼接多条where条件
                    //.addCondition("name = '" + args[1] + "'")
                    //.addCondition("uuid", "玩家UUID")
                    //Order by
                    .orderBy("id", false)
                    //LIMIT 0, 5    LIMIT实现的分页，CRUD人基操了（
                    .setPageLimit(0, 5)
                    //.setLimit(5)      //同效果于上面的 LIMIT 0, 5
                    //构建查询
                    .build();

            /**
             * 函数式编程
             * 这里是个Async回调函数，此时EasySql会将任务提交到自己的线程池里，
             * 接着主动开启链接、执行Sql语句，关闭连接，
             * 并返回一个包装了ResultSet结果等信息的实参，也就是这里的successQuery。
             * 由于我们调用的是Async异步方法，如果需要通过Spigot/Bukkit API对查询到的信息
             * 进行消费，则需要回到服务器的主线程。
             */
            //通过query对象执行里面的查询sql
            queryAction.executeAsync(successQuery -> {

                //获取resultSet
                ResultSet resultSet1 = successQuery.getResultSet();
                //保存resultSet到线程安全的数据结构中
                ConcurrentHashMap<String, Object> concurrentHashMap = new ConcurrentHashMap<>();

                //上面的查询结果只有一个，就直接这样写了
                if (resultSet1.next()) {
                    concurrentHashMap.put("id", resultSet1.getInt("id"));
                    concurrentHashMap.put("name", resultSet1.getString("name"));
                    concurrentHashMap.put("uuid", resultSet1.getString("uuid"));
                    concurrentHashMap.put("value", resultSet1.getInt("value"));
                }

                sender.sendMessage("EasySql创建的线程：" + Thread.currentThread().getName());

                /**
                 * Bukkit.getScheduler.runTask(plugin, task)
                 * 可以让代码回到服务端的主线程运行，对于需要调用Spigot/Bukkit API的场景，
                 * 请尽量回到主线程后在调用。
                 */
                Bukkit.getScheduler().runTask(instance, () -> {
                    sender.sendMessage("查询成功！");
                    sender.sendMessage("服务器主线程：" + Thread.currentThread().getName());

                    /**
                     * 查询一般用同步形式就好了，
                     * 这里是作为一个例子来告诉大家怎么在异步代码里正确调用Spigot/Bukkit API
                     */
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
                /**
                 * INSERT INTO player(name, uuid)
                 * VALUE('玩家名', '玩家的UUID')
                 */
                //直接链式编程一气呵成
                sqlManager.createInsert("player")
                        .setColumnNames("name", "uuid")                 //需要插入的字段名，顺序与下面的Params顺序一致
                        .setParams(args[1], player.getUniqueId())
                        .executeAsync();                                //非查询操作不需要build，直接execute | executeAsync

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
                    sender.sendMessage("金额必须是数字!");
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
                            sender.sendMessage("更新成功");
                        }, (exception, sqlAction) -> {
                            //操作失败回调
                            getLogger().warning("数据库异常！");
                            getLogger().warning("请求语句："  + sqlAction.getSQLContents());
                            getLogger().warning(exception.getMessage());
                        });

                return true;
            }
            sender.sendMessage("玩家不存在");
            return true;
        }
        return false;
    }

    private boolean executeDelete(CommandSender sender, String[] args) {
        if (args.length >= 2) {
            Player player = Bukkit.getPlayer(args[1]);
            sender.sendMessage("正在搜寻玩家：" + args[1]);
            if (Objects.nonNull(player)) {

                /**
                 * DELETE FROM player
                 * WHERE name = '玩家名'
                 */
                sqlManager.createDelete("player")
                        .setConditions("name = '" + args[1] + "'")
                        .build()
                        .execute(null);       //同步执行，异常回调为null
//                        .executeAsync();
                sender.sendMessage("删除成功");
                return true;
            }
            sender.sendMessage("玩家不存在");
            return true;
        }
        return false;
    }

    public Logger getLogger() {
        return logger;
    }

}

