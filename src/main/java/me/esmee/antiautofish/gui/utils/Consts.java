package me.esmee.antiautofish.gui.utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Consts {

    public static int PREVIOUS_PAGE_SLOT = 18;
    public static int INVENTORY_SIZE = 9 * 3;

    public static int LONG_PREVIOUS_PAGE_SLOT = 45;
    public static int LONG_INVENTORY_SIZE = 9 * 6;
    public static ItemStack back() {
        return new ItemBuilder(Material.ARROW)
                .setName(ChatColor.RED + "" + ChatColor.BOLD + "View all players")
                .setLore(ChatColor.GRAY + "Click to go back to the player list menu!")
                .build();
    }

}
