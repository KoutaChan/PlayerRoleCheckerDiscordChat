package net.klnetwork.addons.discordchat.hook.legacy;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerHideEntityEvent;
import org.bukkit.metadata.MetadataValue;

public class VanishLegacy implements Listener {
    @EventHandler
    public void onPlayerHideEntityEvent(PlayerHideEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            for (MetadataValue value : event.getPlayer().getMetadata("vanished")) {
                if (value.asBoolean()) {


                    break;
                }
            }
        }
    }
}
