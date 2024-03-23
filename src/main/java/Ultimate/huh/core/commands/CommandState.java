package Ultimate.huh.core.commands;

import Ultimate.huh.core.UltimateRPGPlugin;
import Ultimate.huh.core.commands.impl.URPGCommandsFactory;
import Ultimate.huh.core.gui.GUIInventory;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.ArrayList;
import java.util.List;

public class CommandState extends URPGCommandsFactory {
    public CommandState(@NotNull String label, String description, String... alias) {
        super("state", description, new String[0]);
    }

    public void URPGCommand(@NotNull UltimateRPGPlugin plugin, @NotNull CommandSender sender, @NotNull String alias, @NotNull @Unmodifiable List<String> params) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Inventory remoteInventory = GUIInventory.getInventory(player.getName());
            if (remoteInventory == null) {
                //Storage not exists
                    remoteInventory = Bukkit.createInventory(player, 54, ChatColor.AQUA + "UltimateRPG GUI" );
                GUIInventory.addInventory(player.getName(), remoteInventory);
            }
            List<String> lore = new ArrayList<String>();
            lore.add("Click here to check your friends!");

            ItemStack IS1 = new ItemStack(Material.CLOCK);
            ItemMeta Clock = IS1.getItemMeta();
            Clock.setDisplayName(ChatColor.GREEN + "Menu");
            IS1.setItemMeta(Clock);
            remoteInventory.setItem(5, IS1);

            ItemStack IS2 = new ItemStack(Material.COMPASS);
            ItemMeta Compass = IS2.getItemMeta();
            Compass.setDisplayName(ChatColor.GREEN + "FriendList");
            Compass.setLore(lore);
            IS2.setItemMeta(Compass);
            remoteInventory.setItem(11, IS2);

            ItemStack IS3 = new ItemStack(Material.BARRIER);
            ItemMeta Barrier = IS3.getItemMeta();
            Barrier.setDisplayName(ChatColor.RED + "Close");
            IS3.setItemMeta(Barrier);
            remoteInventory.setItem(49, IS3);

            ItemStack IS4 = new ItemStack(Material.OAK_DOOR);
            ItemMeta OakDoor = IS4.getItemMeta();
            OakDoor.setDisplayName("Other");
            IS4.setItemMeta(OakDoor);
            remoteInventory.setItem(1, IS4);

            player.openInventory(remoteInventory);
        }

    }

}
