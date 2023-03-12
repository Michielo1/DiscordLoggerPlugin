package me.michielo.discordlogger.eventhandler.player;

import me.michielo.discordlogger.util.Logger;

public class PlayerKickEvent {

    public static void handle(Object event) {
        org.bukkit.event.player.PlayerKickEvent e = (org.bukkit.event.player.PlayerKickEvent) event;
        Logger.logEmbed(e.getPlayer().getName() + " was kicked from the server due to: " + e.getReason(), e.getPlayer());
    }

}
