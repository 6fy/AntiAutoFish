package me.esmee.antiautofish.commands.command;

import me.esmee.antiautofish.config.Config;
import me.esmee.antiautofish.fishing.Fishers;
import me.esmee.antiautofish.gui.inventories.PlayerAlertsGUI;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class ViewAlertsCommand {

    public static void execute(Player player, String[] args) {
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);

        if (target == null) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    Config.prefix + ChatColor.RED + target.getName() + " does not exist!"));
            return;
        }

        if (!Fishers.hasAlerted(target.getUniqueId())) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    Config.prefix + ChatColor.RED + target.getName() + " has no alerts!"));
            return;
        }

        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                Config.prefix + ChatColor.GREEN + target.getName() + "'s alerts"));

        PlayerAlertsGUI gui = new PlayerAlertsGUI(target);
        gui.open(player);
    }

}
