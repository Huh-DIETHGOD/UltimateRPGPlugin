package Ultimate.huh.core;

import Ultimate.huh.core.MySQL.DataTable;
import Ultimate.huh.core.commands.impl.URPGCommandsRouter;
import Ultimate.huh.core.events.EventsManager;
import Ultimate.huh.core.metrics.Metrics;
import cc.carm.lib.easysql.EasySQL;
import cc.carm.lib.easysql.api.SQLManager;
import cc.carm.lib.easysql.api.enums.NumberType;
import me.yic.xconomy.api.XConomyAPI;
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
    private final XConomyAPI XAPI = new XConomyAPI();
    public UltimateRPGPlugin(){}

    @Override
    public void onEnable() {
        super.onEnable();
        Logger logger = getLogger();

        // bStatus plugin
        int pluginId = 19633;
        Metrics metrics = new Metrics(this, pluginId);
        metrics.addCustomChart(new Metrics.SimplePie("chart_id", () -> "My value"));

        // Vault plugin
        if (!setupEconomy() ) {
            getLogger().severe(String.format("- Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        setupPermissions();
        setupChat();

        // XConomy plugin
        XAPI.getversion();
        XAPI.getSyncChannalType();
        instance = this;
        getLogger().info("XConomy successfully enabled!");

        // Load plugin
        logger.info("Loading UltimateRPG plugin");
        // Integration registration
        this.setupCommand();
        this.setupEvents();
        this.setupSQLManager();
        this.setupSQL();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        super.onDisable();

        Bukkit.getScheduler().cancelTasks(this);

        HandlerList.unregisterAll(this);
        if (Objects.nonNull(sqlManager)) {
            EasySQL.shutdownManager(sqlManager);
        }
        instance = null;
        getLogger().info(String.format("Disabled Version %s", getDescription().getName(), getDescription().getVersion()));
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

    public void setupSQLManager() {
        this.saveDefaultConfig();
        this.reloadConfig();
        FileConfiguration config = this.getConfig();
        String driver = config.getString("Ultimate.datasource.driver");
        String url = config.getString("Ultimate.datasource.url");
        String username = config.getString("Ultimate.datasource.username");
        String password = config.getString("Ultimate.datasource.password");

        if (StringUtils.isBlank(driver) || StringUtils.isBlank(url) || StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            this.getLogger().severe("[UltimateRPGPlugin] Config can not be null! please check!");
            this.getLogger().severe("[UltimateRPGPlugin] The plugin will be disabled!");
            return;
        }

        try {
            sqlManager = EasySQL.createManager(driver, url, username, password);
        } catch (Exception e) {
            getLogger().severe(e.getMessage());
            e.printStackTrace();
        }

        try {
            if (!sqlManager.getConnection().isValid(5)) {
                getLogger().severe("[UltimateRPGPlugin] SQL Connection timed out!");
                Bukkit.getPluginManager().disablePlugin(this);
            }
        } catch (SQLException e) {
            getLogger().severe("[UltimateRPGPlugin] SQL Connection Failed! Please check the config file!");
            getLogger().warning("[UltimateRPGPlugin] " + e);
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    private void setupSQL() {
        try {
            sqlManager.getConnection();
            sqlManager.createTable("URPGTable")
                    .addAutoIncrementColumn("id", NumberType.INT, true, true)
                    .addColumn("playerName", "VARCHAR(64)")
                    .addColumn("uuid", "VARCHAR(64)")
                    .addColumn("value", "INT(128)")
                    .build().execute(null);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            getLogger().info("finding vault error");
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
        //chat = rsp.getProvider(); //NullPointerException
        return chat != null;
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        //perms = rsp.getProvider(); //NullPointerException
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
