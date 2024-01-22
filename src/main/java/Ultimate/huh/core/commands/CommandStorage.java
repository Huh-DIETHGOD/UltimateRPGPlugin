package Ultimate.huh.core.commands;

import Ultimate.huh.core.UltimateRPGPlugin;
import Ultimate.huh.core.commands.impl.URPGCommandsFactory;
import Ultimate.huh.core.holders.StorageHolder;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;

public class CommandStorage extends URPGCommandsFactory {
    private static final String description = "Use this command to open storage gui";
    private static Integer storageSpace = 54;
    public CommandStorage() {
        super("storage",description, new String[0]);
    }

    public void URPGCommand(@NotNull UltimateRPGPlugin plugin, @NotNull CommandSender sender, @NotNull String alias, @NotNull @Unmodifiable List<String> params) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Inventory remoteInventory = StorageHolder.getInventory(player.getName());
            if (remoteInventory == null) {
                //Storage not exists
                remoteInventory = Bukkit.createInventory(player, storageSpace, player.getName() + "'s private storage");
                StorageHolder.addInventory(player.getName(), remoteInventory);
            }
            player.openInventory(remoteInventory);
        }

    }



}
