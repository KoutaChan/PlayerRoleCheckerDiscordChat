package net.klnetwork.addons.discordchat.hook.essentialsX;

import net.ess3.api.events.VanishStatusChangeEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class EssentialsXListener implements Listener {
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onVanishStatusChangeEvent(VanishStatusChangeEvent event) {
        if (event.getValue()) {

            event.getAffected();
        } else {

        }
    }
}
