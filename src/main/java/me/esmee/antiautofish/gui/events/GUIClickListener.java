package me.esmee.antiautofish.gui.events;

import me.esmee.antiautofish.fishing.Fishers;
import me.esmee.antiautofish.fishing.Hooked;
import me.esmee.antiautofish.gui.inventories.HookedDetailGUI;
import me.esmee.antiautofish.gui.inventories.PlayerAlertsGUI;
import me.esmee.antiautofish.gui.inventories.PlayerListGUI;
import me.esmee.antiautofish.gui.utils.Consts;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GUIClickListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) return;

        Inventory inventory = e.getClickedInventory();
        if (inventory == null) return;

        ItemStack clickedItem = e.getCurrentItem();
        if (clickedItem == null) return;
        if (clickedItem.getItemMeta() == null) return;

        String itemName = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName());
        Player player = (Player) e.getWhoClicked();

        if (clickedItem.equals(Consts.back())) {
            handleBackEvent(player);
            return;
        }

        if (inventory.getName().matches(ChatColor.RED + "" + ChatColor.BOLD + ".*'s Alerts")) {
            e.setCancelled(true);

            Hooked hook = Fishers.getHookById(itemName);
            if (hook == null) return;

            HookedDetailGUI gui = new HookedDetailGUI(hook);
            gui.open(player);

            return;
        }

        if (inventory.getName().matches(ChatColor.RED + "" + ChatColor.BOLD + "Viewing .*'s Alert")) {
            e.setCancelled(true);

            if (itemName == null) return;
            if (!(itemName.matches("View all .*'s alerts"))) return;

            String name = inventory.getName()
                    .replace(ChatColor.RED + "" + ChatColor.BOLD + "Viewing ", "")
                    .replace("'s Alert", "");

            OfflinePlayer target = Bukkit.getOfflinePlayer(name);
            if (target == null) return;

            PlayerAlertsGUI gui = new PlayerAlertsGUI(target);
            gui.open(player);
        }

        if (inventory.getName().equals(ChatColor.RED + "" + ChatColor.BOLD + "All Suspicious players")) {
            e.setCancelled(true);

            if (itemName == null) return;

            OfflinePlayer target = Bukkit.getOfflinePlayer(itemName);
            if (target == null) return;

            PlayerAlertsGUI gui = new PlayerAlertsGUI(target);
            gui.open(player);
        }
    }

    private void handleBackEvent(Player player) {
        PlayerListGUI gui = new PlayerListGUI();
        gui.open(player);
    }

}
