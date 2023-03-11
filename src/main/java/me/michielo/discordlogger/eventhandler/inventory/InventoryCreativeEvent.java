package me.michielo.discordlogger.eventhandler.inventory;

import me.michielo.discordlogger.util.Logger;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class InventoryCreativeEvent {

    public static void handle(Object event) {
        org.bukkit.event.inventory.InventoryCreativeEvent e = (org.bukkit.event.inventory.InventoryCreativeEvent) event;

        StringBuilder builder = new StringBuilder();

        ItemStack cursorItem = e.getCursor();
        Player player = (Player) e.getWhoClicked();

        if (cursorItem.getType().isAir()) {
            builder.append(player.getName() + " removed " + e.getCurrentItem().getAmount() + " " + e.getCurrentItem().getType() + " from their creative inventory");
        } else {
            builder.append(player.getName() + " took out " + cursorItem.getAmount() + " " + cursorItem.getType() + " from their creative inventory");
        }

        Logger.logEmbed(builder.toString());
    }


}
