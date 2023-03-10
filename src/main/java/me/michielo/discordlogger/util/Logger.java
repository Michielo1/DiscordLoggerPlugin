package me.michielo.discordlogger.util;

import me.michielo.discordlogger.webhook.DiscordWebhook;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Logger {

    /*
        A pretty default Logger util to automatically include the prefix
     */

    public static void logInfo(String message) {
        Bukkit.getLogger().info("DiscordLogger >> " + message);
    }

    public static void logError(String message) {
        Bukkit.getLogger().severe(ChatColor.RED + "DiscordLogger >> " + message);
    }

    public static void logEmbed(String message) {
        logInfo("Sending a webhook alert...");
        DiscordWebhook.log(message);
    }

}
