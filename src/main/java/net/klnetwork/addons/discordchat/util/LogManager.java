package net.klnetwork.addons.discordchat.util;

import net.klnetwork.addons.discordchat.DiscordChat;
import org.bukkit.Bukkit;

import java.util.logging.Logger;

public class LogManager {
    public static void log(String log) {
        getLogger().info(log);
    }
    public static void logYaml(String yaml, ReplaceText... texts) {
        Bukkit.getConsoleSender().sendMessage("[DiscordChat] " + ConfigManager.getYaml(yaml, texts));
    }

    public static Logger getLogger() {
        return DiscordChat.getInstance().getLogger();
    }
}