package me.michielo.discordlogger;

import me.michielo.discordlogger.bukkit.command.CommandClass;
import me.michielo.discordlogger.bukkit.listener.EventListener;
import me.michielo.discordlogger.util.Logger;
import me.michielo.discordlogger.webhook.DiscordWebhook;
import org.bukkit.plugin.java.JavaPlugin;

public class DiscordLogger extends JavaPlugin {

    private boolean disabled;

    @Override
    public void onEnable() {

        Logger.logInfo("Starting...");

        /*
            Init vars
         */

        disabled = false;

        /*
            Load files
         */

        this.saveDefaultConfig();
        if (getConfig().getString("URL").isEmpty()) {
            shutdown(true);
            return;
        }

        /*
            Registering classes
         */

        getCommand("discordlogger").setExecutor(new CommandClass());
        getServer().getPluginManager().registerEvents(new EventListener(), this);

        /*
            Initialising some classes
         */

        new DiscordWebhook(this);


        Logger.logInfo("Started!");
        DiscordWebhook.log("DiscordLogger has been started!");
    }

    @Override
    public void onDisable() {
        shutdown(false);
    }

    private void shutdown(boolean failedStartup) {
        // if the startup failed we won't send an embed and disable the plugin
        // to prevent the onDisable to trigger this method again (if the startup failed) we use the disabled boolean
        if (failedStartup) {
            Logger.logError("No webhook URL has been set!");
            Logger.logError("DiscordLogger is being shut down!");
            getServer().getPluginManager().disablePlugin(this);
            disabled = true;
        } else if (!disabled) {
            DiscordWebhook.log("DiscordLogger is being shut down!");
        }
    }

}