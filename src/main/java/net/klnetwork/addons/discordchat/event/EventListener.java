package net.klnetwork.addons.discordchat.event;

import net.dv8tion.jda.internal.utils.tuple.Pair;
import net.klnetwork.addons.discordchat.DiscordChat;
import net.klnetwork.addons.discordchat.api.GenerateType;
import net.klnetwork.addons.discordchat.data.discord.DiscordData;
import net.klnetwork.addons.discordchat.data.discord.DiscordManager;
import net.klnetwork.addons.discordchat.data.playerdata.PlayerData;
import net.klnetwork.addons.discordchat.data.playerdata.PlayerDataManager;
import net.klnetwork.addons.discordchat.util.ConfigManager;
import net.klnetwork.addons.discordchat.util.DiscordUtils;
import net.klnetwork.addons.discordchat.util.PlayerUtils;
import net.klnetwork.addons.discordchat.util.ReplaceText;
import net.klnetwork.playerrolechecker.api.utils.CommonUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventListener implements Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event) {
        DiscordManager.get().stream()
                .filter(data -> data.isChat() && (!event.isCancelled()));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        if (DiscordChat.getInstance().getConfig().getBoolean("global-settings.cache-playerdata")) {
            PlayerDataManager.put(event.getPlayer().getUniqueId(), new PlayerData(event.getPlayer(), DiscordChat.getConnectedHook().getPlayerData().getDiscordId(event.getPlayer().getUniqueId(), CommonUtils.isFloodgateUser(event.getPlayer().getUniqueId()))));
        }
        Pair<Object, Boolean> message = ConfigManager.getDiscordYaml("join-message",
                new ReplaceText("%avatar%", DiscordChat.getInstance().getGenerateType() == GenerateType.LINK
                        ? CommonUtils.isFloodgateUser(event.getPlayer().getUniqueId())
                        ? ConfigManager.getYamlGlobal("global-settings.bedrock-editions-avatar-link")
                        : ConfigManager.getYamlGlobal("global-settings.java-editions-avatar-link")
                        : "attachment://avatar.png"),
                new ReplaceText("%name%", event.getPlayer().getName()),
                new ReplaceText("%uuid%", event.getPlayer().getUniqueId().toString()));

        DiscordManager.get().stream()
                .filter(DiscordData::isJoinLog)
                .forEachOrdered(data -> {
                    DiscordUtils.sendMessage(data.getTextChannel(), message.getLeft(),
                            message.getRight() ? PlayerUtils.getHeadSkin(event.getPlayer()) : null);
                });
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        Bukkit.getScheduler().runTaskAsynchronously(DiscordChat.getInstance(), () -> {
            Pair<Object, Boolean> message = ConfigManager.getDiscordYaml("left-message",
                    new ReplaceText("%avatar%", DiscordChat.getInstance().getGenerateType() == GenerateType.LINK
                            ? CommonUtils.isFloodgateUser(event.getPlayer().getUniqueId())
                            ? ConfigManager.getYamlGlobal("global-settings.bedrock-editions-avatar-link")
                            : ConfigManager.getYamlGlobal("global-settings.java-editions-avatar-link")
                            : "attachment://avatar.png"),
                    new ReplaceText("%name%", event.getPlayer().getName()),
                    new ReplaceText("%uuid%", event.getPlayer().getUniqueId().toString()));

            DiscordManager.get().stream()
                    .filter(DiscordData::isLeftLog)
                    .forEachOrdered(data -> {
                        DiscordUtils.sendMessage(data.getTextChannel(), message.getLeft(),
                                message.getRight() ? PlayerUtils.getHeadSkin(event.getPlayer()) : null);
                    });

            if (DiscordChat.getInstance().getConfig().getBoolean("global-settings.cache-playerdata")) {
                PlayerDataManager.remove(event.getPlayer().getUniqueId());
            }
        });
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent event) {
        Bukkit.getScheduler().runTaskAsynchronously(DiscordChat.getInstance(), () -> {
            Pair<Object, Boolean> message = ConfigManager.getDiscordYaml("command-message",
                    new ReplaceText("%avatar%", DiscordChat.getInstance().getGenerateType() == GenerateType.LINK
                            ? CommonUtils.isFloodgateUser(event.getPlayer().getUniqueId())
                            ? ConfigManager.getYamlGlobal("global-settings.bedrock-editions-avatar-link")
                            : ConfigManager.getYamlGlobal("global-settings.java-editions-avatar-link")
                            : "attachment://avatar.png"),
                    new ReplaceText("%name%", event.getPlayer().getName()),
                    new ReplaceText("%uuid%", event.getPlayer().getUniqueId().toString()),
                    new ReplaceText("%msg%", event.getMessage()));

            DiscordManager.get().stream()
                    .filter(DiscordData::isCommandLog)
                    .forEachOrdered(data -> {
                        DiscordUtils.sendMessage(data.getTextChannel(), message.getLeft(),
                                message.getRight() ? PlayerUtils.getHeadSkin(event.getPlayer()) : null);
                    });
        });
    }
}