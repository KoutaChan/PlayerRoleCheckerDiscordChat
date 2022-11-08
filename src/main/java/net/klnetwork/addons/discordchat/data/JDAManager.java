package net.klnetwork.addons.discordchat.data;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import net.klnetwork.addons.discordchat.DiscordChat;
import org.bukkit.plugin.Plugin;

public class JDAManager {
    private boolean isLocalJDA;
    private JDA localJDA;

    public JDAManager(Plugin plugin) {
        String token = plugin.getConfig().getString("discord-token");
        if (token != null && token.isEmpty()) {
            try {
                localJDA = JDABuilder.createDefault(token, GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MESSAGE_REACTIONS, GatewayIntent.DIRECT_MESSAGE_REACTIONS)
                        .disableCache(CacheFlag.VOICE_STATE, CacheFlag.EMOJI, CacheFlag.STICKER)
                        .setStatus(OnlineStatus.ONLINE)
                        .build();

                isLocalJDA = true;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void stop() {
        if (localJDA != null) {
            localJDA.shutdown();
        }
    }

    public JDA getJDA() {
        return isLocalJDA ? localJDA : DiscordChat.getInstance().getJDA();
    }

    public JDA getLocalJDA() {
        return localJDA;
    }

    public boolean isLocalJDA() {
        return isLocalJDA;
    }
}

