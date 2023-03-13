package me.michielo.discordlogger.bukkit.listener;

import me.michielo.discordlogger.util.Logger;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.player.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class EventListener implements Listener {

    /*
        This class registers all the events we listen to.
        If an event has been triggered, we call the handle method which will automatically find and trigger
        the associated method in the associated class.
     */

    private void handle(Object event, String name) {
        // player events
        Logger.logInfo(name);

        try {
            String packagename = "me.michielo.discordlogger.eventhandler.player." + name;
            Class<?> clazz = Class.forName(packagename);

            for (Method m : clazz.getDeclaredMethods()) {
                if (m.toString().contains("handle")) {
                    // using a null obj as the method is always static
                    m.invoke(null, event);
                    return;
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException | InvocationTargetException e) {}

        // inventory events
        try {
            String packagename = "me.michielo.discordlogger.eventhandler.inventory." + name;
            Class<?> clazz = Class.forName(packagename);

            for (Method m : clazz.getDeclaredMethods()) {
                if (m.toString().contains("handle")) {
                    // using a null obj as the method is always static
                    m.invoke(null, event);
                    return;
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException | InvocationTargetException e) {}


        // couldn't find the associated class, this should not happen if version control does its job
        Logger.logError("Error in the plugin code! Please contact the author!");
    }

    /*
        Registering players listeners
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
    public void onChat(AsyncPlayerChatEvent event) {
        handle(event, event.getEventName());
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent event) {
        handle(event, event.getEventName());
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        handle(event, event.getEventName());
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        handle(event, event.getEventName());
    }

    @EventHandler
    public void onGamemodeChange(PlayerGameModeChangeEvent event) {
        handle(event, event.getEventName());
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        handle(event, event.getEventName());
    }

    @EventHandler
    public void onKick(PlayerKickEvent event) {
        handle(event, event.getEventName());
    }

    @EventHandler
    public void onBook(PlayerEditBookEvent event) {
        handle(event, event.getEventName());
    }

    /*
        Inventory listeners
     */

    @EventHandler
    public void onCreativeInv(InventoryCreativeEvent event) {
        handle(event, event.getEventName());
    }

}
