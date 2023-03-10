package me.michielo.discordlogger.bukkit.listener;

import me.michielo.discordlogger.util.Logger;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class EventListener implements Listener {

    /*
        This class registers all the events we listen to.
        If an event has been triggered, we call the handle method which will automatically find and trigger
        the associated method in the associated class.
     */

    private void handle(Object event, String name) {
        try {
            name = "me.michielo.discordlogger.eventhandler." + name;
            Class<?> clazz = Class.forName(name);

            for (Method m : clazz.getDeclaredMethods()) {
                if (m.toString().contains("handle")) {
                    // using a null obj as the method is always static
                    m.invoke(null, event);
                }
            }
        } catch (ClassNotFoundException e) {
            // couldn't find the associated class, this should not happen if version control does its job
            Logger.logError("Error in the plugin code! Please contact the author!");
        } catch (IllegalAccessException |
                 InvocationTargetException e) {
            Logger.logError("Encountered an error:");
            e.printStackTrace();
        }
    }

    /*
        Registering listeners
     */

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        handle(event, event.getEventName());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        handle(event, event.getEventName());
    }

    @EventHandler
    public void onCreativeInv(InventoryCreativeEvent event) {
        handle(event, event.getEventName());
    }

}
