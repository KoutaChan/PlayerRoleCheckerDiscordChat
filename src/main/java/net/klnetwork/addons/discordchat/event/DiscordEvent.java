package net.klnetwork.addons.discordchat.event;

import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.klnetwork.addons.discordchat.DiscordChat;
import net.klnetwork.addons.discordchat.data.discord.DiscordManager;
import net.klnetwork.addons.discordchat.util.ColorUtils;
import net.klnetwork.addons.discordchat.util.ConfigManager;
import net.klnetwork.addons.discordchat.util.ReplaceText;
import net.klnetwork.playerrolechecker.api.data.common.PlayerData;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DiscordEvent extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (!event.isWebhookMessage() && !event.getAuthor().isBot() && event.isFromType(ChannelType.TEXT)) {
            String message = event.getMessage().getContentRaw();

            new Thread(() -> DiscordManager.get().stream()
                    .filter(data -> data.getTextChannelId() == event.getChannel().getIdLong())
                    .forEach(data -> {
                        if (data.isConsoleCommand()) {
                            Bukkit.getScheduler().runTask(DiscordChat.getInstance(), () -> {
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), message);
                            });
                        }
                        if (data.isChat()) {
                            List<ReplaceText> texts = new ArrayList<ReplaceText>() {{
                                add(new ReplaceText("%name%", event.getAuthor().getName()));
                                add(new ReplaceText("%display_name%", event.getMember().getEffectiveName()));
                                add(new ReplaceText("%full_name%", event.getMember().getUser().getAsTag()));
                                add(new ReplaceText("%msg%", message));
                                final Role top = event.getMember().getRoles().isEmpty() ? null : event.getMember().getRoles().get(0);
                                add(new ReplaceText("%top_level_role%", top != null
                                        ? top.getName()
                                        : ConfigManager.getYaml("non-role")));
                                add(new ReplaceText("%color%", top != null
                                        ? ColorUtils.toHex(ColorUtils.colorToHex(top.getColorRaw()))
                                        : ""));
                            }};

                            PlayerData playerData = DiscordChat.getInstance().getConfig().getBoolean("global-settings.get-playerdata") ?
                                    DiscordChat.getConnectedHook().getPlayerData().getUUID(event.getMember().getId()) : null;

                            if (playerData == null) {
                                Bukkit.broadcastMessage(ConfigManager.getYaml("chat-message", texts.toArray(new ReplaceText[0])));
                            } else {
                                Bukkit.broadcastMessage(ConfigManager.getYaml("chat-message-registered", texts.toArray(new ReplaceText[0])));
                            }
                        }
                    })).start();
        }
    }
}