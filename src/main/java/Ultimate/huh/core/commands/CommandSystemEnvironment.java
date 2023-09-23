package Ultimate.huh.core.commands;

import Ultimate.huh.core.UltimateRPGPlugin;
import Ultimate.huh.core.commands.impl.URPGCommands;
import Ultimate.huh.core.utils.MsgUtil;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;
import java.util.Properties;

public class CommandSystemEnvironment extends URPGCommands {
    public CommandSystemEnvironment() {
        super("system", new String[0]);
    }

    public void URPGCommand(@NotNull UltimateRPGPlugin plugin, @NotNull CommandSender sender, @NotNull String alias, @NotNull @Unmodifiable List<String> params) {
        Properties p = System.getProperties();// 获取当前的系统属性
        p.list(System.out);// 将属性列表输出
        MsgUtil.msg(sender,"CPU core:\n"+//Runtime.getRuntime() 获取当前运行时的实例
        Runtime.getRuntime().availableProcessors()+"\n"+//availableProcessors() 获取当前电脑 CPU 数量
        "Ram：\n"+
        Runtime.getRuntime().totalMemory()+"\n"+//totalMemory() 获取 java 虚拟机中的内存总量
        "Rest Ram：\n"+
        Runtime.getRuntime().freeMemory()+"\n"+//freeMemory() 获取 java 虚拟机中的空闲内存量
        "Max Ram：\n"+
        Runtime.getRuntime().maxMemory()+"\n"+//maxMemory() 获取 java 虚拟机试图使用的最大内存量
        "Java Version：\n" +
        p.getProperty("java.version")+"\n"+
        "Java Path：\n" +
        p.getProperty("java.home")+"\n"+
        "Java library path：\n" +
        p.getProperty("java.library.path"));
    }
}