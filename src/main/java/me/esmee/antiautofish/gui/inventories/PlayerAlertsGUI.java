package me.esmee.antiautofish.gui.inventories;

import me.esmee.antiautofish.fishing.Fishers;
import me.esmee.antiautofish.fishing.Hooked;
import me.esmee.antiautofish.gui.utils.ItemBuilder;
import me.esmee.antiautofish.gui.utils.Consts;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class PlayerAlertsGUI {

    private Inventory inventory;

    private final OfflinePlayer target;

    public PlayerAlertsGUI(OfflinePlayer target) {
        this.target = target;

        createInventory();
    }

    private void createInventory() {
        ArrayList<Hooked> hooks = Fishers.getHooks(target.getUniqueId(), 0, 45);

        int size = Consts.INVENTORY_SIZE;
        int backSlot = Consts.PREVIOUS_PAGE_SLOT;
        if (hooks.size() > 18) {
            size = Consts.LONG_INVENTORY_SIZE;
            backSlot = Consts.LONG_PREVIOUS_PAGE_SLOT;
        }

        this.inventory = Bukkit.createInventory(
                null,
                size,
                ChatColor.RED + "" + ChatColor.BOLD + this.target.getName() + "'s Alerts"
        );

        this.inventory.setItem(backSlot, Consts.back());

        for (Hooked hook : hooks) {
            ItemStack item = new ItemBuilder(
                    Material.WOOL,
                    getWoolColor(hook.getProbability())
            )
            .setName(ChatColor.RED + hook.getId())
            .setLore(
                    ChatColor.GRAY + "Caught by: " + ChatColor.RED + hook.getFisher().getName(),
                    ChatColor.GRAY + "Reasons: " + ChatColor.RED + hook.getReasons(),
                    ChatColor.GRAY + "Probability: " + ChatColor.RED + hook.getProbability() + "%",
                    ChatColor.GRAY + "Reaction time: " + ChatColor.RED + hook.getRetractReactionTime() + "ms"
            )
            .build();

            this.inventory.addItem(item);
        }
    }

    private byte getWoolColor(double probability) {
        if (probability >= 0 && probability < 20) {
            return 5;
        }
        if (probability >= 20 && probability < 60) {
            return 1;
        }
        if (probability >= 60 && probability <= 100) {
            return 14;
        }
        return 15;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void open(Player player) {
        player.openInventory(getInventory());
    }
}
