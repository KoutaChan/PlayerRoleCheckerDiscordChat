package net.klnetwork.addons.discordchat.util;

import net.klnetwork.addons.discordchat.DiscordChat;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LogManager {
    public static void log(String log) {
        getLogger().info(log);
    }

    public static void logYaml(String yaml, ReplaceText... texts) {
        logYaml(Level.INFO, yaml, texts);
    }

    public static void logYaml(Level level, String yaml, ReplaceText... texts) {
        getLogger().log(level, ConfigManager.getYaml(yaml, texts));
    }

    public static Logger getLogger() {
        return DiscordChat.getInstance().getLogger();
    }
}