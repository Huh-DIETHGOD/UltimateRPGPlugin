package Ultimate.huh.core.utils;

import Ultimate.huh.core.UltimateRPGPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class Msg {
    public Msg() {
    }

    public static void log(Level level, String msg, Object... args) {
        UltimateRPGPlugin.getInstance().getLogger().log(level, String.format(msg, args));
    }

    public static void info(String msg, Object... args) {
        log(Level.INFO, msg, args);
    }

    public static void warn(String msg, Object... args) {
        log(Level.WARNING, msg, args);
    }

    public static void warn(String msg, Throwable throwable, Object... args) {
        UltimateRPGPlugin.getInstance().getLogger().log(Level.WARNING, String.format(msg, args), throwable);
    }

    public static void severe(String msg, Object... args) {
        log(Level.SEVERE, msg, args);
    }

    public static void severe(String msg, Throwable throwable, Object... args) {
        UltimateRPGPlugin.getInstance().getLogger().log(Level.SEVERE, String.format(msg, args), throwable);
    }

    public static void msg(@NotNull CommandSender sender, String... messages) {
        if (messages.length != 0) {
            sender.sendMessage((String) Arrays.stream(messages).map(Ultimate.huh.core.utils.Msg::color).collect(Collectors.joining("\n")));
        }
    }

    public static void broadcast(String... messages) {
        if (messages.length != 0) {
            Bukkit.broadcastMessage((String)Arrays.stream(messages).map(Ultimate.huh.core.utils.Msg::color).collect(Collectors.joining("\n")));
        }
    }

    public static String color(@NotNull String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}
