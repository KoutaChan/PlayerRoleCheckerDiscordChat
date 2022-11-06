package net.klnetwork.addons.discordchat.hook.essentialsX;

import de.myzelyam.api.vanish.PlayerVanishStateChangeEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class SuperVanishListener implements Listener {
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerVanishStateChangeEvent(PlayerVanishStateChangeEvent event) {

    }
}
