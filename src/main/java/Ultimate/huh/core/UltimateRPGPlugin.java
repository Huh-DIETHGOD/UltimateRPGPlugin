package Ultimate.huh.core;

import Ultimate.huh.core.commands.impl.URPGCommandsRouter;
import Ultimate.huh.core.events.EventsManager;
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
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public abstract class UltimateRPGPlugin extends JavaPlugin {
    private static FileConfiguration config;
    private static UltimateRPGPlugin instance;
    private SQLManager sqlManager;



    private final EventsManager eventsManager = new EventsManager();

    protected UltimateRPGPlugin() {
        if (UltimateRPG.getHandler() == null && this instanceof Handler) {
            UltimateRPG.setHandler((Handler) this);
        }

        assert UltimateRPG.getHandler() != null;
        UltimateRPG.getHandler().addNewPlugin(this);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        int pluginId = 19633;

        Logger logger = getLogger();
        logger.info(ChatColor.AQUA + "[UltimateRPGPlugin] " + ChatColor.GREEN + "Loading" + ChatColor.AQUA + "UltimateRPG plugin");
        // Plugin startup logic

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

        if (StringUtils.isBlank(Driver) || StringUtils.isBlank(Url) || StringUtils.isBlank(userName) || StringUtils.isBlank(Password)) {
            getLogger().severe(ChatColor.AQUA + "[UltimateRPGPlugin] " + ChatColor.RED + "Config can not be null! please check!");
            getLogger().severe(ChatColor.AQUA + "[UltimateRPGPlugin] " + ChatColor.RED + "The plugin will be disabled!");
            Bukkit.getPluginManager().disablePlugin(this);
            //Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        sqlManager = EasySQL.createManager(Driver, Url, userName, Password);
        try {
            if (!sqlManager.getConnection().isValid(5)) {
                getLogger().severe(ChatColor.AQUA + "[UltimateRPGPlugin] " + ChatColor.RED + "SQL Connection out of time!");
                Bukkit.getPluginManager().disablePlugin(this);
            }
        } catch (SQLException e) {
            getLogger().severe(ChatColor.AQUA + "[UltimateRPGPlugin] " + ChatColor.RED + "SQL Connection Failed! Please Check config file!");
            getLogger().warning(ChatColor.AQUA + "[UltimateRPGPlugin] " + ChatColor.RED + e);
            //Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        HandlerList.unregisterAll(this);
        super.onDisable();
        if (Objects.nonNull(sqlManager)) {
            EasySQL.shutdownManager(sqlManager);
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

    private void setupEvents() {
        Bukkit.getPluginManager().registerEvents(this.getEventsManager(), this);

    }

    public final void afterLoad() {
        getLogger().info(ChatColor.AQUA + "[UltimateRPGPlugin]" + ChatColor.GREEN + "Plugin loaded!");
    }

    public final void onLoad() {
        getLogger().info(ChatColor.AQUA + "[UltimateRPGPlugin]" + ChatColor.GREEN + "Plugin loading...");
    }


    protected abstract List<Listener> loadListeners();


    public static UltimateRPGPlugin getInstance(){
        return instance;
    }

    public EventsManager getEventsManager() {
        return this.eventsManager;
    }

}
