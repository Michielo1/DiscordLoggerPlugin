package me.michielo.discordlogger.eventhandler;

import me.michielo.discordlogger.util.Logger;

public class PlayerJoinEvent {

    public static void handle(Object event) {
        org.bukkit.event.player.PlayerJoinEvent e = (org.bukkit.event.player.PlayerJoinEvent) event;

        StringBuilder builder = new StringBuilder();

        if (!e.getPlayer().hasPlayedBefore()) {
            builder.append(e.getPlayer().getName() + " joined the server for the first time!");
        } else {
            builder.append(e.getPlayer().getName() + " joined the server!");
        }

        Logger.logEmbed(builder.toString());
    }

}
