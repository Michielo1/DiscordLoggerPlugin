package me.michielo.discordlogger.webhook;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;
import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import me.michielo.discordlogger.DiscordLogger;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class DiscordWebhook {

    private static DiscordLogger pluginInstance;
    private static WebhookClient client;
    private static Date lastEmbed;
    private static List<String> toSend;
    private static boolean awaitingLog;

    public DiscordWebhook(DiscordLogger plugin) {
        // instance
        pluginInstance = plugin;

        // init vars
        lastEmbed = new Date(System.currentTimeMillis());
        toSend = new ArrayList<>();
        awaitingLog = false;

        // init client
        WebhookClientBuilder builder = new WebhookClientBuilder(plugin.getConfig().getString("URL"));
        builder.setThreadFactory((job) -> {
            Thread thread = new Thread(job);
            thread.setName("DiscordLogger");
            thread.setDaemon(true);
            return thread;
        });
        builder.setWait(true);
        client = builder.build();
    }

    public static void log(String str) {

        // checking if we've send a discord message in the last second
        Date buffer = new Date(System.currentTimeMillis() - 1000);
        if (lastEmbed.after(buffer) || awaitingLog) {
            // too soon!
            if (!awaitingLog) {
                scheduleLog();
                awaitingLog = true;
            }
            toSend.add(str);
            return;
        }

        // send embed
        WebhookEmbed embed = new WebhookEmbedBuilder()
                .setColor(0xCF9FFF)
                .setDescription(
                        ">> DiscordLogger << \n \n" + str
                )
                .build();

        client.send(embed);
        // update last discord message
        lastEmbed = new Date(System.currentTimeMillis());
    }

    private static void scheduleLog() {
        // second delay
        new BukkitRunnable() {
            @Override
            public void run() {
                /*
                    If this method has been triggered there are too many messages in the last second
                    Here we decrease the amount of lines if possible and send all pending messages in one message
                 */

                StringBuilder str = new StringBuilder();

                /*
                    Attempting to decrease the amount of lines by getting the sum of certain messages

                    For example:
                        We might receive 2 messages that someone took 1 item.
                        Here we change that to one message saying the player took 2 items
                        Note that this only happens if the messages are identical
                 */

                Map<String, Integer> countMap = new HashMap<>();
                for (String string : toSend) {
                    if (string.matches(".*\\d+.*")) {
                        if (countMap.get(string) == null) countMap.put(string, 0);
                        countMap.put(string, countMap.get(string) + 1);
                    }
                }

                Map<String, Boolean> replacedValue = new HashMap<>();
                for (String string : toSend) {
                    if (countMap.get(string) != null) {
                        if (replacedValue.get(string) == null) {
                            System.out.println(string);
                            System.out.println("replaced with " + countMap.get(string));
                            String newStr = string.replaceAll("\\b\\d+\\b", String.valueOf(countMap.get(string)));
                            System.out.println(newStr);
                            str.append("\n" + newStr);
                            replacedValue.put(string, true);
                        }
                        continue;
                    }
                    str.append("\n" + string);
                }

                /*
                    Sending the actual discord message
                 */

                WebhookEmbed embed = new WebhookEmbedBuilder()
                        .setColor(0xCF9FFF)
                        .setDescription(
                                ">> DiscordLogger << \n " + str
                        )
                        .build();

                client.send(embed);
                toSend.clear();
                awaitingLog = false;
                lastEmbed = new Date(System.currentTimeMillis());
            }
        }.runTaskLater(pluginInstance, 20);
    }

}
