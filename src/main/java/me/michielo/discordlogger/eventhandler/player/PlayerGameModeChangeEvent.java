package me.michielo.discordlogger.eventhandler.player;

import me.michielo.discordlogger.util.Logger;

public class PlayerGameModeChangeEvent {

    public static void handle(Object event) {
        org.bukkit.event.player.PlayerGameModeChangeEvent e = (org.bukkit.event.player.PlayerGameModeChangeEvent) event;

        String str = e.getPlayer().getName() + " changed their gamemode to: " + e.getNewGameMode();

        Logger.logEmbed(str);
    }

}
