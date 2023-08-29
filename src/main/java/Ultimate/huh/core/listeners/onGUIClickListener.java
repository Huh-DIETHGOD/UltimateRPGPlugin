package Ultimate.huh.core.listeners;

import Ultimate.huh.core.gui.GUIInventory;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class onGUIClickListener implements Listener {
    /**
     * prevent player take stuff from GUI
     * @param event
     */
    @EventHandler
    public void onGUIListener(InventoryClickEvent event) {
        Inventory currentInventory = event.getInventory();
        HumanEntity player = event.getWhoClicked();
        Inventory remoteInventory = GUIInventory.getInventory(player.getName());
        if (event.getInventory().getViewers().equals(remoteInventory)) {
        } else {
            if (remoteInventory == currentInventory) {
                ItemStack clickedStuff = event.getCurrentItem();
                if (clickedStuff.getType() == Material.GREEN_STAINED_GLASS_PANE) {
                    event.setCancelled(true);
                } else if (clickedStuff.getType() == Material.PLAYER_HEAD) {
                    event.setCancelled(true);
                } else if (clickedStuff.getType() == Material.NETHER_STAR) {
                    event.setCancelled(true);
                } else if (clickedStuff.getType() == Material.OAK_DOOR){
                    event.setCancelled(true);
                } else if (clickedStuff.getType() ==  Material.CLOCK) {
                    event.setCancelled(true);
                } else if (clickedStuff.getType() == Material.COMPASS) {
                    event.setCancelled(true);
                } else if (clickedStuff.getType() == Material.BARRIER) {
                    event.setCancelled(true);
                }else if (clickedStuff.getType() == Material.BEACON) {
                    event.setCancelled(true);
                } else if (clickedStuff.getType() == Material.BOOK) {
                    event.setCancelled(true);
                } else if (clickedStuff.getType() == Material.CHEST) {
                    event.setCancelled(true);
                } else if (clickedStuff.getType() == Material.DRAGON_HEAD) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
