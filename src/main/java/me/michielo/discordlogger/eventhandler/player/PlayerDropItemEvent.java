package me.michielo.discordlogger.eventhandler.player;

import me.michielo.discordlogger.util.Logger;

public class PlayerDropItemEvent {

    public static void handle(Object event) {
        org.bukkit.event.player.PlayerDropItemEvent e = (org.bukkit.event.player.PlayerDropItemEvent) event;

        String str = e.getPlayer().getName() + " dropped " + e.getItemDrop().getItemStack().getAmount() + " " + e.getItemDrop().getItemStack().getType().name();

        Logger.logEmbed(str);
    }

}
