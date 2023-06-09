package me.esmee.antiautofish.commands;

import me.esmee.antiautofish.commands.command.ToggleCommand;
import me.esmee.antiautofish.commands.command.ViewAlertsCommand;
import me.esmee.antiautofish.config.Config;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AutoFishCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;

        Player player = (Player) sender;

        if (!player.hasPermission(Config.permission)) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    Config.prefix + ChatColor.RED + "You do not have permission to use this command!"));
            return true;
        }

        if (args.length == 0) {
            ToggleCommand.execute(player);
            return true;
        }

        ViewAlertsCommand.execute(player, args);
        return true;
    }
}
