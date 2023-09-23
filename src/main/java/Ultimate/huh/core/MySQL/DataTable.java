package Ultimate.huh.core.MySQL;

import cc.carm.lib.easysql.api.SQLManager;
import cc.carm.lib.easysql.api.SQLTable;
import cc.carm.lib.easysql.api.table.NamedSQLTable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.SQLException;

public enum DataTable {
    UltimateRPGPluginTable(SQLTable.of("UltimateRPGPluginTable", (table) -> {
        table.addAutoIncrementColumn("playerName", true);
        table.addColumn("ID", "TEXT NOT NULL");
        table.addColumn("value", "INT UNSIGNED NOT NULL");
    }));

    private final NamedSQLTable table;
    DataTable(NamedSQLTable table) {
        this.table = table;
    }

    public static void initialize(@NotNull SQLManager manager, @Nullable String tablePrefix) {
        for (DataTable value : values()) {
            try {
                value.get().create(manager, tablePrefix);
            } catch (SQLException e) {
                System.err.println(e.getMessage());
                // 提示异常
            }
        }
    }

    public NamedSQLTable get() {
        return this.table;
    }
}
