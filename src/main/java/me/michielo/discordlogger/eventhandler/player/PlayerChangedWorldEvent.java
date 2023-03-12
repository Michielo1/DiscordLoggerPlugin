package me.michielo.discordlogger.eventhandler.player;

import me.michielo.discordlogger.util.Logger;

public class PlayerChangedWorldEvent {

    public static void handle(Object event) {
        org.bukkit.event.player.PlayerChangedWorldEvent e = (org.bukkit.event.player.PlayerChangedWorldEvent) event;

        String str = e.getPlayer().getName() + " moved to world: " + e.getPlayer().getWorld().getName() + " from: " + e.getFrom().getName();

        Logger.logEmbed(str);
    }


}
