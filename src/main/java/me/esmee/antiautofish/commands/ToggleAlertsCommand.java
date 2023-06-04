package me.esmee.antiautofish.commands;

import me.esmee.antiautofish.config.Config;
import me.esmee.antiautofish.staff.Alerts;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ToggleAlertsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;

        Player player = (Player) sender;

        if (!player.hasPermission(Config.permission)) {
            player.sendMessage(Config.prefix + ChatColor.RED + "You do not have permission to use this command!");
            return true;
        }

        if (Alerts.hasAlertsOn(player.getUniqueId())) {
            Alerts.removeToggledAlertsOn(player.getUniqueId());
            player.sendMessage(Config.prefix + ChatColor.RED + "You have toggled off alerts!");
        } else {
            Alerts.addToggledAlertsOn(player.getUniqueId());
            player.sendMessage(Config.prefix + ChatColor.GREEN + "You have toggled on alerts!");
        }

        return true;
    }
}
