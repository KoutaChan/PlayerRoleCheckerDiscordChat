package net.klnetwork.addons.discordchat.event;

import net.klnetwork.addons.discordchat.DiscordChat;
import net.klnetwork.addons.discordchat.data.discord.DiscordData;
import net.klnetwork.addons.discordchat.data.discord.DiscordManager;
import net.klnetwork.addons.discordchat.data.playerdata.PlayerData;
import net.klnetwork.addons.discordchat.data.playerdata.PlayerDataManager;
import net.klnetwork.addons.discordchat.util.PlayerUtils;
import net.klnetwork.playerrolechecker.api.utils.CommonUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.concurrent.CompletableFuture;

public class EventListener implements Listener {
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event) {

    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoinEvent(PlayerJoinEvent event) {
        if (DiscordChat.getInstance().getConfig().getBoolean("global-settings.cache-playerdata")) {
            CompletableFuture.supplyAsync(() -> DiscordChat.getConnectedHook().getPlayerData()
                            .getDiscordId(event.getPlayer().getUniqueId(), CommonUtils.isFloodgateUser(event.getPlayer().getUniqueId())))
                    .thenAccept(playerData -> {
                        PlayerDataManager.put(event.getPlayer().getUniqueId(), new PlayerData(event.getPlayer(), playerData));
                    });
        }
        DiscordManager.get().stream()
                .filter(DiscordData::isJoinLog)
                .forEachOrdered(v -> {

                });

        System.out.println("CALLED!");
        System.out.println(PlayerUtils.getSkinUrl(event.getPlayer()).toURL());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuitEvent(PlayerQuitEvent event) {
        if (DiscordChat.getInstance().getConfig().getBoolean("global-settings.cache-playerdata")) {
            PlayerDataManager.remove(event.getPlayer().getUniqueId());
        }
        DiscordManager.get().stream()
                .filter(DiscordData::isLeftLog)
                .forEachOrdered(v -> {

                });
    }
}