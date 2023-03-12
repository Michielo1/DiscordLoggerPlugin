package me.michielo.discordlogger.eventhandler.player;

import me.michielo.discordlogger.util.Logger;

public class AsyncPlayerChatEvent {

    public static void handle(Object event) {
        org.bukkit.event.player.AsyncPlayerChatEvent e = (org.bukkit.event.player.AsyncPlayerChatEvent) event;

        String str = e.getPlayer().getName() + " send the messge: " + e.getMessage();

        Logger.logEmbed(str);
    }

}
