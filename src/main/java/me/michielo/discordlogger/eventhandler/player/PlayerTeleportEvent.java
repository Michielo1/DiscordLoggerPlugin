package me.michielo.discordlogger.eventhandler.player;

import me.michielo.discordlogger.util.Logger;
import org.bukkit.Location;

public class PlayerTeleportEvent {

    public static void handle(Object event) {
        org.bukkit.event.player.PlayerTeleportEvent e = (org.bukkit.event.player.PlayerTeleportEvent) event;

        Location from = e.getFrom();
        Location to = e.getTo();

        String str = e.getPlayer().getName() + " teleported to " + to.getX() + " " + to.getY() + " " + to.getZ() + " from " +
                from.getX() + " " + from.getY() + " " + from.getZ();

        Logger.logEmbed(str, e.getPlayer());
    }

}
