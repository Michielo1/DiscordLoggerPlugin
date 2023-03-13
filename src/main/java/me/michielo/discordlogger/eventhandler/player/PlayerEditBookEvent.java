package me.michielo.discordlogger.eventhandler.player;

import me.michielo.discordlogger.DiscordLogger;
import me.michielo.discordlogger.util.Logger;
import me.michielo.discordlogger.util.PastebinUtil;
import org.bukkit.Bukkit;

public class PlayerEditBookEvent {

    public static void handle(Object event) {
        org.bukkit.event.player.PlayerEditBookEvent e = (org.bukkit.event.player.PlayerEditBookEvent) event;

        if (e.getNewBookMeta().getPageCount() > 1) {
            // if there is more than one page, we'll create a pastebin to make sure it stays organized
            // to do this we'll go on a new thread to make sure we're not blocking the main thread
            Bukkit.getServer().getScheduler().runTaskAsynchronously(DiscordLogger.getInstance(), () -> {
                StringBuilder bookcontent = new StringBuilder();

                for (String str : e.getNewBookMeta().getPages()) {
                    bookcontent.append("\n" + str);
                }

                String link = PastebinUtil.createPastebin(bookcontent.toString(), e.getNewBookMeta().getTitle());

                String str = e.getPlayer().getName() + " edited a book called: " + e.getNewBookMeta().getTitle() +
                        "! \nYou can find the new text here: " + link;
                Logger.logEmbed(str, e.getPlayer());
            });
        } else {
            // the book just has one page, we'll log that
            String str = e.getPlayer().getName() + " edited a book called: " + e.getNewBookMeta().getTitle() +
                    "\nThe new text:\n";
            for (String text : e.getNewBookMeta().getPages()) str = str + text;
            Logger.logEmbed(str, e.getPlayer());
        }
    }

}
