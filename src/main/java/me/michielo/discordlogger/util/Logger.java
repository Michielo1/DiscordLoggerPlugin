package me.michielo.discordlogger.util;

import me.michielo.discordlogger.DiscordLogger;
import me.michielo.discordlogger.webhook.DiscordWebhook;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    /*
        Using the webhook to send an embed
        There is one check we need to do before sending the embed, some events conflict.
        To avoid this from happening we wait 1 second before checking if any other embed logs conflict to avoid sending them
     */

    private static HashMap<Player, List<String>> outgoingEmbeds = new HashMap<>();

    public static void logEmbed(String message, Player player) {
        // getting stored messages
        List<String> currentList = new ArrayList<>();
        if (outgoingEmbeds.get(player) != null) currentList = outgoingEmbeds.get(player);
        // adding message
        currentList.add(message);
        outgoingEmbeds.put(player, currentList);

        // wait 1 second
        new BukkitRunnable() {
            @Override
            public void run() {
                // getting stored messages
                List<String> currentList = new ArrayList<>();
                if (outgoingEmbeds.get(player) != null) currentList = outgoingEmbeds.get(player);

                // making sure there is still content in the list
                if (currentList.isEmpty()) return;

                /*
                    Checking for unnecessary messages and removing them
                 */

                // taking out from creative inv & dropping item = dropping item
                boolean foundA = false;
                boolean foundB = false;
                for (String str : currentList) {
                    if (str.contains("from their creative inventory")) foundA = true;
                    if (str.contains("dropped")) foundB = true;
                }
                if (foundA && foundB) {
                    // taking out item from inv is unnecessary IF it's the same item, run conflict algorithm
                    findConflict(player);
                }

                /*
                    log all messages and clear list
                 */

                for (String str : outgoingEmbeds.get(player)) {
                    logEmbed(str);
                }
                outgoingEmbeds.put(player, null);
            }
        }.runTaskLater(DiscordLogger.getInstance(), 20 * 2);
    }

    public static void logEmbed(String message) {
        logInfo("Sending a webhook alert...");
        DiscordWebhook.log(message);
    }

    /*
        Util method for logEmbed to remove unnecessary messages
     */

    private static void findConflict(Player p) {
        List<String> list = outgoingEmbeds.get(p);
        boolean removed;
        do {
            removed = false;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).contains("from their creative inventory")) {
                    for (int j = 0; j < list.size(); j++) {
                        if (i != j && list.get(j).contains("dropped")) {
                            list.remove(i);
                            removed = true;
                            break;
                        }
                    }
                }
                if (removed) {
                    break;
                }
            }
        } while (removed);
        outgoingEmbeds.put(p, list);
    }

}
