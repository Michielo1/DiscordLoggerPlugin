package me.michielo.discordlogger.eventhandler.player;

import me.michielo.discordlogger.util.Logger;

public class PlayerCommandPreprocessEvent {

    public static void handle(Object event) {
        org.bukkit.event.player.PlayerCommandPreprocessEvent e = (org.bukkit.event.player.PlayerCommandPreprocessEvent) event;

        String str = e.getPlayer() + " ran the command: " + e.getMessage();

        Logger.logEmbed(str);
    }

}
