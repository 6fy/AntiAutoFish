package me.esmee.antiautofish.gui.utils;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemBuilder {

    private final Material material;
    private Byte data;
    private String name;
    private String[] lore;
    private int amount = 1;

    private OfflinePlayer skull;

    public ItemBuilder(Material material) {
        this.material = material;
    }

    public ItemBuilder(Material material, byte data) {
        this.material = material;
        this.data = data;
    }

    public ItemBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ItemBuilder setLore(String... lore) {
        this.lore = lore;
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public ItemBuilder skull(OfflinePlayer player) {
        this.skull = player;
        return this;
    }

    public ItemStack build() {
        ItemStack item = new ItemStack(material, amount, (data != null ? data : (byte) 0x0));

        if (this.name != null) {
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(this.name);
            item.setItemMeta(meta);
        }

        if (this.skull != null) {
            SkullMeta meta = (SkullMeta) item.getItemMeta();
            meta.setOwner(this.skull.getName());
            item.setItemMeta(meta);
        }

        if (this.lore != null) {
            ItemMeta meta = item.getItemMeta();
            meta.setLore(java.util.Arrays.asList(this.lore));
            item.setItemMeta(meta);
        }

        return item;
    }

}
