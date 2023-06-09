package me.esmee.antiautofish.gui.inventories;

import me.esmee.antiautofish.fishing.Fishers;
import me.esmee.antiautofish.fishing.Hooked;
import me.esmee.antiautofish.gui.utils.ItemBuilder;
import me.esmee.antiautofish.gui.utils.Consts;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class HookedDetailGUI {
    private Inventory inventory;

    private final Hooked hook;

    public HookedDetailGUI(Hooked hook) {
        this.hook = hook;

        createInventory();
    }

    private void createInventory() {
        this.inventory = Bukkit.createInventory(
                null,
                Consts.INVENTORY_SIZE,
                ChatColor.RED + "" + ChatColor.BOLD + "Viewing " + this.hook.getFisher().getName() + "'s Alert"
        );

        ItemStack padding = new ItemBuilder(Material.STAINED_GLASS_PANE, getGlassColor(hook.getProbability())).build();
        for (int i = 0; i < 9; i++) {
            inventory.setItem(i, padding);
        }

        double averageProbability = Fishers.getAverageProbability(this.hook.getUUID());
        inventory.setItem(11,
                new ItemBuilder(Material.TRIPWIRE_HOOK)
                        .setName(
                                ChatColor.GRAY + "" + ChatColor.BOLD + "Probability of "
                                        + ChatColor.RED + "" + ChatColor.BOLD + this.hook.getFisher().getName()
                                        + ChatColor.GRAY + "" + ChatColor.BOLD + " auto-fishing"
                        )
                        .setLore(
                                ChatColor.GRAY + "This Alert: " + ChatColor.RED + this.hook.getProbability() + "%",
                                ChatColor.GRAY + "On Average: " + ChatColor.RED + averageProbability + "%"
                        )
                        .build()
        );

        inventory.setItem(13,
                new ItemBuilder(Material.SKULL_ITEM, (byte) 3)
                        .skull(this.hook.getFisher())
                        .setName(
                                ChatColor.GRAY + "" + ChatColor.BOLD + "Viewing "
                                        + ChatColor.RED + "" + ChatColor.BOLD + this.hook.getFisher().getName()
                                        + ChatColor.GRAY + "" + ChatColor.BOLD + "'s alerts"
                        )
                        .setLore(ChatColor.GRAY + "Reasons: " + ChatColor.RED + hook.getReasons())
                        .build()
        );

        int totalAlerts = Fishers.getHooks(this.hook.getFisher().getUniqueId(), 0, null).size();

        inventory.setItem(15,
                new ItemBuilder(Material.PAPER)
                        .setName(
                                ChatColor.GRAY + "" + ChatColor.BOLD + "View all "
                                        + ChatColor.RED + "" + ChatColor.BOLD + this.hook.getFisher().getName()
                                        + ChatColor.GRAY + "" + ChatColor.BOLD + "'s alerts"
                        )
                        .setLore(
                                ChatColor.GRAY + this.hook.getFisher().getName() + " alerts: " + ChatColor.RED + totalAlerts + ChatColor.GRAY + "!"
                        )
                        .build()
        );


        for (int i = 19; i < 27; i++) {
            inventory.setItem(i, padding);
        }

        inventory.setItem(Consts.PREVIOUS_PAGE_SLOT, Consts.back());
    }

    private byte getGlassColor(double probability) {
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
