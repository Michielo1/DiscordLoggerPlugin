package me.michielo.discordlogger.eventhandler.player;

import me.michielo.discordlogger.util.Logger;

public class PlayerQuitEvent {

    public static void handle(Object event) {
        org.bukkit.event.player.PlayerQuitEvent e = (org.bukkit.event.player.PlayerQuitEvent) event;
        Logger.logEmbed(e.getPlayer().getName() + " left the server!", e.getPlayer());
    }

}
