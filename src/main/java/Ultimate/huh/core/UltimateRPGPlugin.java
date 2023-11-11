package Ultimate.huh.core;

import Ultimate.huh.core.MySQL.URPGTable;
import Ultimate.huh.core.commands.impl.URPGCommandsRouter;
import Ultimate.huh.core.events.EventsManager;
import Ultimate.huh.core.metrics.Metrics;
import Ultimate.huh.core.scheduling.Scheduler;
import Ultimate.huh.core.utils.UpdateCheckerUtil;
import cc.carm.lib.easysql.EasySQL;
import cc.carm.lib.easysql.api.SQLManager;
import cc.carm.lib.easysql.api.enums.NumberType;
import cc.carm.lib.easysql.hikari.HikariConfig;
import me.yic.xconomy.api.XConomyAPI;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.apache.commons.lang.StringUtils;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;
import org.bukkit.Bukkit;
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
    private Scheduler scheduler;
    private final EventsManager eventsManager = new EventsManager();
    private static Economy econ = null;
    private static Permission perms = null;
    private static Chat chat = null;
    private final XConomyAPI XAPI = new XConomyAPI();
    private final int resourceId;
    private boolean outdated;

    public UltimateRPGPlugin(int resourceId){
        this.resourceId = resourceId;
    }

    @Override
    public void onEnable() {
        super.onEnable();

        // 自检插件版本
        if (this.getResourceId() != 0) {
            (new UpdateCheckerUtil(this)).getVersion((version) -> {
                DefaultArtifactVersion currentVersion = new DefaultArtifactVersion(this.getDescription().getVersion());
                DefaultArtifactVersion mostRecentVersion = new DefaultArtifactVersion(version);
                if (currentVersion.compareTo(mostRecentVersion) <= 0 && !currentVersion.equals(mostRecentVersion)) {
                    this.outdated = true;
                    this.getScheduler().runTimer(() -> {
                        String pluginName = instance.getDescription().getName();
                        getLogger().info(pluginName + " is out of date! (Version " + this.getDescription().getVersion() + ")");
                        getLogger().info("&cThe newest version is &f" + version);
                        getLogger().info("&cPlease download the new version!");
                        }, 0L, 864000L);
                }
            });
        }

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
        getLogger().info("Loading UltimateRPG plugin");

        // Integration registration
        this.setupCommand();
        this.setupEvents();
        this.setupSQLManager();
        this.setupSQLTable();
    }

    @Override
    public void onDisable() {
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

    /**
     * 链接SQL数据库
     */
    public void setupSQLManager() {
        this.saveDefaultConfig();
        this.reloadConfig();
        FileConfiguration config = this.getConfig();
        String driver = config.getString("Ultimate.datasource.driver");
        String url = config.getString("Ultimate.datasource.url");
        String username = config.getString("Ultimate.datasource.username");
        String password = config.getString("Ultimate.datasource.password");

        if (StringUtils.isBlank(driver) || StringUtils.isBlank(url) || StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            getLogger().severe("[UltimateRPGPlugin] Config can not be null! please check!");
            getLogger().severe("[UltimateRPGPlugin] The plugin will be disabled!");
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
            getLogger().warning("[UltimateRPGPlugin] " + e);
        }

    }

    /**
     * 创建SQL数据库
     */
    private void setupSQLTable() {
        FileConfiguration config = this.getConfig();
        Boolean firstSetUp = config.getBoolean("Ultimate.firstTimeLoad");
        if (firstSetUp.equals(true)) {
            try{
                URPGTable.initialize(sqlManager, "URPGTable");
            } catch(RuntimeException e){
                e.printStackTrace();
            }
            config.set("Ultimate.firstTimeLoad", false);
            this.saveConfig();
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


    public Scheduler getScheduler() {
        return scheduler;
    }

    public int getResourceId() {
        return this.resourceId;
    }

    public boolean isOutdated() {
        return outdated;
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
