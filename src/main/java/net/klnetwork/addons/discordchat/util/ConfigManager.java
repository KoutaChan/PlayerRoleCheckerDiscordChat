package net.klnetwork.addons.discordchat.util;

import net.klnetwork.addons.discordchat.DiscordChat;
import org.bukkit.ChatColor;

public class ConfigManager {
    public static String MESSAGE_PREFIX = "messages.";

    public static String getYaml(String yaml, ReplaceText... texts) {
        String text = DiscordChat.getInstance().getConfig().getString(MESSAGE_PREFIX + yaml);

        if (text == null) {
            return "N/A" + MESSAGE_PREFIX + yaml + "!";
        }

        text = ChatColor.translateAlternateColorCodes('&', text);

        for (ReplaceText replaceText : texts) {
            text = text.replace(replaceText.getRegex(), replaceText.getReplacement());
        }
        return text;
    }
}
