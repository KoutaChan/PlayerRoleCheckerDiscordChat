package net.klnetwork.addons.discordchat.log;

import net.klnetwork.addons.discordchat.DiscordChat;

public class LogManager {
    public static String MESSAGE_PREFIX = "messages.";

    public static void log(String log) {
        DiscordChat.getInstance().getLogger().info(log);
    }

    public static void logYaml(String yaml, ReplaceText... texts) {
        String text = DiscordChat.getInstance().getConfig().getString(MESSAGE_PREFIX + yaml);

        if (text == null) {
            return;
        }

        for (ReplaceText replaceText : texts) {
            text = text.replaceAll(replaceText.getRegex(), replaceText.getReplacement());
        }

        log(text);
    }
}