package me.esmee.antiautofish.commands.command;

import me.esmee.antiautofish.config.Config;
import me.esmee.antiautofish.staff.Alerts;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;

public class ToggleCommand {

    public static void execute(Player player) {
        if (Alerts.hasAlertsOn(player.getUniqueId())) {
            Alerts.removeToggledAlertsOn(player.getUniqueId());
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    Config.prefix + ChatColor.RED + "You have toggled off alerts!"));
        } else {
            Alerts.addToggledAlertsOn(player.getUniqueId());
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    Config.prefix + ChatColor.GREEN + "You have toggled on alerts!"));
        }
    }

}
