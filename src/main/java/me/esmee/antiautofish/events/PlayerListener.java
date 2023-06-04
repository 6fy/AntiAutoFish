package me.esmee.antiautofish.events;

import me.esmee.antiautofish.config.Config;
import me.esmee.antiautofish.fishing.Fishers;
import me.esmee.antiautofish.fishing.Hooked;
import me.esmee.antiautofish.staff.Alerts;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

public class PlayerListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onFishCaught(PlayerFishEvent e) {
        Player player = e.getPlayer();

        // Player threw their fishing rod in the water
        if (e.getState().equals(PlayerFishEvent.State.FISHING)) {
            Hooked hook = new Hooked(player);
            Fishers.addHooked(hook);
            return;
        }

        // Player didn't catch a fish or the rod went down, but hasn't retracted yet
        if (e.getState().equals(PlayerFishEvent.State.FAILED_ATTEMPT)) return;

        Hooked hook = Fishers.getLastHooked(player.getUniqueId());
        if (hook == null) return;

        if (e.getState().equals(PlayerFishEvent.State.BITE)) {
            hook.setWentUnderWater();
            return;
        }

        // Queue checks to see if the player is suspicious
        hook.queue();

        if (!hook.isSuspicious()) return;

        double averageProbability = Fishers.getAverageProbability(player.getUniqueId());
        double roundAverageProbability = (double) Math.round(averageProbability * 100) / 100;

        String text = ChatColor.translateAlternateColorCodes('&',
                Config.prefix + Config.alertMessage
                        .replace("%player%", player.getName())
                        .replace("%probability%", String.valueOf(hook.getProbability()))
                        .replace("%average%", String.valueOf(roundAverageProbability))
        );

        TextComponent msg = new TextComponent(text);
        msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "Click to teleport").create()));
        msg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tp " + player.getName()));

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!p.hasPermission(Config.permission)) return;
            if (!Alerts.hasAlertsOn(p.getUniqueId())) return;

            p.spigot().sendMessage(msg);
        }
    }


}
