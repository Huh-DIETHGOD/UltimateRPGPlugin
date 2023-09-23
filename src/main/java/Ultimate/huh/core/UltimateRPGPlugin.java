package Ultimate.huh.core;

import Ultimate.huh.core.MySQL.DataTable;
import Ultimate.huh.core.commands.impl.URPGCommandsRouter;
import Ultimate.huh.core.events.EventsManager;
import Ultimate.huh.core.listeners.onGUIClickListener;
import Ultimate.huh.core.metrics.Metrics;
import cc.carm.lib.easysql.EasySQL;
import cc.carm.lib.easysql.api.SQLManager;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.Objects;
import java.util.logging.Logger;


public final class UltimateRPGPlugin extends JavaPlugin {
    private static UltimateRPGPlugin instance;
    private SQLManager sqlManager;
    private final EventsManager eventsManager = new EventsManager();
    private static Economy econ = null;
    private static Permission perms = null;
    private static Chat chat = null;

    public UltimateRPGPlugin(){}

    @Override
    public void onEnable() {
        super.onEnable();
        Logger logger = getLogger();

        // bStatus plugin
        int pluginId = 19633;
        Metrics metrics = new Metrics(this, pluginId);

        // Vault plugin
        if (!setupEconomy() ) {
            getLogger().severe(String.format("- Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        setupPermissions();
        setupChat();

        // XConomy plugin
        instance = this;
        getLogger().info("XConomy successfully enabled!");

        logger.info(ChatColor.AQUA + "[UltimateRPGPlugin] " + ChatColor.GREEN + "Loading" + ChatColor.AQUA + "UltimateRPG plugin");
        // Plugin startup logic

        Bukkit.getPluginManager().registerEvents(new onGUIClickListener(), this);

        //Integration registration
        this.setupCommand();
        this.setupEvents();
        this.setupSQLManager();

        sqlManager.createTable("UltimateRPGPlugin")
                .addColumn("playerName", "VARCHAR(32) AUTO_INCREMENT NOT NULL PRIMARY KEY")
                .addColumn("id","VARCHAR(64)")
                .addColumn("name","VARCHAR(64)")
                .addColumn("value", "INT(100)")
        ;
        sqlManager.createInsert("UltimateRPGPlugin");

        DataTable.initialize(sqlManager,"UltimateRPGPlugin");

        metrics.addCustomChart(new Metrics.SimplePie("chart_id", () -> "My value"));
    }

    @Override
    public void onDisable() {
        super.onDisable();
        getLogger().info(String.format("Disabled Version %s", getDescription().getName(), getDescription().getVersion()));
        // Plugin shutdown logic
        HandlerList.unregisterAll(this);
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

    private void setupSQLManager() {
        this.saveDefaultConfig();
        this.reloadConfig();
        FileConfiguration config = this.getConfig();
        String Driver = config.getString("Ultimate.datasource.driver");
        String Url = config.getString("Ultimate.datasource.url");
        String userName = config.getString("Ultimate.datasource.username");
        String Password = config.getString("Ultimate.datasource.password");

        if (StringUtils.isBlank(Driver) || StringUtils.isBlank(Url) || StringUtils.isBlank(userName) || StringUtils.isBlank(Password)) {
            getLogger().severe(ChatColor.AQUA + "[UltimateRPGPlugin] " + ChatColor.RED + "Config can not be null! please check!");
            getLogger().severe(ChatColor.AQUA + "[UltimateRPGPlugin] " + ChatColor.RED + "The plugin will be disabled!");
            //Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        sqlManager = EasySQL.createManager(Driver, Url, userName, Password);
        try {
            if (!sqlManager.getConnection().isValid(5)) {
                getLogger().severe(ChatColor.AQUA + "[UltimateRPGPlugin] " + ChatColor.RED + "SQL Connection out of time!");
                //Bukkit.getPluginManager().disablePlugin(this);
            }
        } catch (SQLException e) {
            getLogger().severe(ChatColor.AQUA + "[UltimateRPGPlugin] " + ChatColor.RED + "SQL Connection Failed! Please Check config file!");
            getLogger().warning(ChatColor.AQUA + "[UltimateRPGPlugin] " + ChatColor.RED + e);
            //Bukkit.getPluginManager().disablePlugin(this);
        }

    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }

    public final void afterLoad() {
        getLogger().info("Plugin loaded!");
    }

    public final void onLoad() {
        getLogger().info("Plugin loading...");
    }


    public static UltimateRPGPlugin getInstance(){
        return instance;
    }

    public EventsManager getEventsManager() {
        return this.eventsManager;
    }

    public static Economy getEconomy() {
        return econ;
    }

    public static Permission getPermissions() {
        return perms;
    }

    public static Chat getChat() {
        return chat;
    }

}
