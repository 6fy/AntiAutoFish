package me.esmee.antiautofish;

import me.esmee.antiautofish.commands.AutoFishCommand;
import me.esmee.antiautofish.config.Config;
import me.esmee.antiautofish.events.PlayerListener;
import me.esmee.antiautofish.fishing.Fishers;
import me.esmee.antiautofish.fishing.Hooked;
import me.esmee.antiautofish.gui.events.GUIClickListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class AntiAutoFish extends JavaPlugin {

    public FileConfiguration config;

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.config = this.getConfig();
        this.setConfig();

        // Register events
        PluginManager manager = this.getServer().getPluginManager();

        manager.registerEvents(new PlayerListener(), this);
        manager.registerEvents(new GUIClickListener(), this);

        // Register commands
        this.getCommand("antiautofish").setExecutor(new AutoFishCommand());

        // Log confirmation telling the server this plugin has been enabled
        this.getLogger().info("AntiAutoFish has been enabled!");
    }

    private void setConfig() {
        if (this.config.contains("style.prefix")) {
            Config.prefix = this.config.getString("style.prefix");
        }

        if (this.config.contains("checks.suspiciousReactionTime")) {
            Config.suspiciousReactionTime = this.config.getInt("checks.suspiciousReactionTime");
        }

        if (this.config.contains("staff.alertFromProbability")) {
            Config.alertFromProbability = this.config.getInt("staff.alertFromProbability");
        }

        if (this.config.contains("staff.alertMessage")) {
            Config.alertMessage = this.config.getString("staff.alertMessage");
        }

        if (this.config.contains("staff.permission")) {
            Config.permission = this.config.getString("staff.permission");
        }
    }

    @Override
    public void onDisable() {
        // Log confirmation telling the server this plugin has been disabled
        this.getLogger().info("AntiAutoFish has been disabled!");

        super.onDisable();
    }
}
