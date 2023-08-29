package Ultimate.huh.core;

import Ultimate.huh.core.commands.impl.URPGCommandsRouter;
import Ultimate.huh.core.listeners.onGUIClickListener;
import Ultimate.huh.core.metrics.Metrics;
import cc.carm.lib.easysql.EasySQL;
import cc.carm.lib.easysql.api.SQLManager;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.Objects;
import java.util.logging.Logger;

public final class UltimateRPGPlugin extends JavaPlugin {
    private static FileConfiguration config;
    private static UltimateRPGPlugin instance;
    private SQLManager SQLManager;

    @Override
    public void onEnable() {
        super.onEnable();

        Logger logger = getLogger();
        logger.info(ChatColor.AQUA + "[UltimateRPGPlugin] " + ChatColor.GREEN + "Loading" + ChatColor.AQUA + "UltimateRPG plugin");
        // Plugin startup logic
        int pluginId = 19633;

        Bukkit.getPluginManager().registerEvents(new onGUIClickListener(), this);

        //Integration registration
        this.setupCommand();
        initSQLManager();

        Metrics metrics = new Metrics(this, pluginId);
        metrics.addCustomChart(new Metrics.SimplePie("chart_id", () -> "My value"));
    }

    private void initSQLManager() {
        this.saveDefaultConfig();
        this.reloadConfig();
        config = this.getConfig();
        String Driver = config.getString("Ultimate.datasource.driver");
        String Url = config.getString("Ultimate.datasource.url");
        String userName = config.getString("Ultimate.datasource.username");
        String Password = config.getString("Ultimate.datasource.password");

        if (StringUtils.isBlank(Driver) | StringUtils.isBlank(Url) || StringUtils.isBlank(userName) | StringUtils.isBlank(Password)) {
            this.getLogger().severe(ChatColor.AQUA + "[UltimateRPGPlugin] " + ChatColor.RED + "config can not be null! please check!");
            //Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        SQLManager = EasySQL.createManager(Driver, Url, userName, Password);
        try {
            if (!SQLManager.getConnection().isValid(5)) {
                this.getLogger().severe(ChatColor.AQUA + "[UltimateRPGPlugin] " + ChatColor.RED + "SQL Connection out of time");

            }
        } catch (SQLException e) {
            this.getLogger().severe(ChatColor.AQUA + "[UltimateRPGPlugin] " + ChatColor.RED + "SQL Connection Failed! Please Check config file!");
            //Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
        HandlerList.unregisterAll(this);
        super.onDisable();
        if (Objects.nonNull(SQLManager)) {
            EasySQL.shutdownManager(SQLManager);
        }
        instance = null;
    }

    private void setupCommand() {
        PluginCommand pluginCommand = this.getCommand("urpg");
        if (pluginCommand != null) {
            URPGCommandsRouter router = new URPGCommandsRouter(this);
            pluginCommand.setExecutor(router);
            pluginCommand.setTabCompleter(router);
        }
    }



    public final void afterLoad() {
        getLogger().info(ChatColor.AQUA + "[UltimateRPGPlugin]" + ChatColor.GREEN + "Plugin loaded!");
    }

    public final void onLoad() {
        getLogger().info(ChatColor.AQUA + "[UltimateRPGPlugin]" + ChatColor.GREEN + "Plugin loading...");
    }

    public static UltimateRPGPlugin getInstance(){
        return instance;
    }

}
