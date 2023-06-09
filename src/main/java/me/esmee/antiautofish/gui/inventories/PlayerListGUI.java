package me.esmee.antiautofish.gui.inventories;

import me.esmee.antiautofish.fishing.Fishers;
import me.esmee.antiautofish.fishing.Hooked;
import me.esmee.antiautofish.gui.utils.Consts;
import me.esmee.antiautofish.gui.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.UUID;

public class PlayerListGUI {
    public PlayerListGUI() {
        createInventory();
    }

    private Inventory inventory;

    private void createInventory() {
        this.inventory = Bukkit.createInventory(
                null,
                Consts.LONG_INVENTORY_SIZE,
                ChatColor.RED + "" + ChatColor.BOLD + "All Suspicious players"
        );

        ArrayList<Hooked> hooks = Fishers.getHooked().stream().filter(Hooked::isSuspicious).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        ArrayList<UUID> listed = new ArrayList<>();

        for (Hooked hook : hooks) {
            if (listed.size() >= (Consts.INVENTORY_SIZE - 9)) break;
            if (listed.contains(hook.getUUID())) continue;

            listed.add(hook.getUUID());

            OfflinePlayer player = Bukkit.getOfflinePlayer(hook.getUUID());

            double averageProbability = Fishers.getAverageProbability(player.getUniqueId());

            ItemStack item = new ItemBuilder(Material.SKULL_ITEM, (byte) 3)
                    .skull(player)
                    .setName(ChatColor.RED + hook.getFisher().getName())
                    .setLore(
                            ChatColor.GRAY + "Average probability: " + ChatColor.RED + averageProbability + "%"
                    )
                    .build();

            this.inventory.addItem(item);
        }
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void open(Player player) {
        player.openInventory(getInventory());
    }
}
