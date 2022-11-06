package net.klnetwork.addons.discordchat.hook.plugins;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.kitteh.vanish.event.VanishStatusChangeEvent;

public class VanishNoPacketListener implements Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    public void onVanishStatusChangeEvent(VanishStatusChangeEvent event) {

    }
}