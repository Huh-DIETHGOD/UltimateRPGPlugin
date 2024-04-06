package Ultimate.huh.core.MySQL;

import cc.carm.lib.easysql.api.SQLManager;
import cc.carm.lib.easysql.api.SQLTable;
import cc.carm.lib.easysql.api.builder.TableCreateBuilder;
import cc.carm.lib.easysql.api.enums.IndexType;
import cc.carm.lib.easysql.api.enums.NumberType;
import cc.carm.lib.easysql.api.table.NamedSQLTable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.SQLException;
import java.util.function.Consumer;

public enum URPGTable implements SQLTable{
    URPGTable((table) -> {
        table.addAutoIncrementColumn("id", NumberType.INT, true, true);
        table.addColumn("playerName", "VARCHAR(64) NOT NULL");
        table.addColumn("value", "DECIMAL(64,2) UNSIGNED NOT NULL");
        table.addColumn("permission", "VARCHAR(8)");
        table.addColumn("password", "VARCHAR(64)");
        table.setIndex("playerName", IndexType.PRIMARY_KEY);
    });

    private final Consumer<TableCreateBuilder> builder;
    private @Nullable String tablePrefix;
    private @Nullable SQLManager manager;

    URPGTable(Consumer<TableCreateBuilder> builder) {
        this.builder = builder;
    }

    @Override
    public @Nullable SQLManager getSQLManager() {
        return this.manager;
    }

    @Override
    public @NotNull String getTableName() {
        return (tablePrefix != null ? tablePrefix : "") + name();
    }

    @Override
    public boolean create(SQLManager sqlManager) throws SQLException {
        return create(sqlManager, null);
    }

    public boolean create(@NotNull SQLManager sqlManager, @Nullable String tablePrefix) throws SQLException {
        if (this.manager == null) this.manager = sqlManager;
        this.tablePrefix = tablePrefix;

        TableCreateBuilder tableBuilder = sqlManager.createTable(getTableName());
        if (builder != null) builder.accept(tableBuilder);
        return tableBuilder.build().executeFunction(l -> l > 0, false);
    }

    public static void initialize(@NotNull SQLManager manager, @Nullable String tablePrefix) {
        for (URPGTable value : values()) {
            try {
                value.create(manager, tablePrefix);
            } catch (SQLException e) {
                // 提示异常
                System.err.println("Failed to initalize the SQL table, please check!");
            }
        }
    }
}
