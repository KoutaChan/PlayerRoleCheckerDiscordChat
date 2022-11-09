package net.klnetwork.addons.discordchat.util;

import com.google.common.base.Preconditions;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.internal.utils.tuple.Pair;
import net.klnetwork.addons.discordchat.DiscordChat;
import net.klnetwork.playerrolechecker.api.utils.CommonUtils;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;

import java.awt.*;
import java.time.OffsetDateTime;

public class ConfigManager {
    public static String MESSAGE_PREFIX = "messages.";

    public static String getYaml(String yaml, ReplaceText... texts) {
        return getYamlGlobal(MESSAGE_PREFIX + yaml, texts);
    }

    public static String getYamlGlobal(String yaml, ReplaceText... texts) {
        String text = DiscordChat.getInstance().getConfig().getString(yaml);

        if (text == null) {
            return "N/A: " + yaml + "!";
        }

        return toText(ChatColor.translateAlternateColorCodes('&', text), texts);
    }

    private static String toText(String text, ReplaceText... texts) {
        for (ReplaceText replaceText : texts) {
            text = text.replace(replaceText.getRegex(), replaceText.getReplacement());
        }
        return text;
     }

    public static Pair<Object, Boolean> getDiscordYaml(String yaml, ReplaceText... texts) {
        if (!DiscordChat.getInstance().getConfig().isConfigurationSection(MESSAGE_PREFIX + yaml)) {
            return Pair.of(getYaml(yaml, texts), false);
        } else {
            ConfigurationSection section = DiscordChat.getInstance().getConfig().getConfigurationSection(MESSAGE_PREFIX + yaml);

            return Pair.of(new EmbedBuilder() {{
                setColor(getColor(section.getString("color")));
                setTitle(getString(section, "title", texts));
                setDescription(getString(section, "description", texts));
                setThumbnail(getString(section, "description", texts));
                setImage(getString(section, "image", texts));
                setTimestamp(section.getBoolean("timestamp") ? OffsetDateTime.now() : null);
                setAuthor(getString(section, "author", texts));
                setFooter(getString(section, "footer", texts));

                for (String message : section.getStringList("message")) {
                    String[] strings = message.split("\\|", 3);
                    Preconditions.checkArgument(strings.length == 3);
                    addField(strings[0], strings[1], Boolean.parseBoolean(strings[2]));
                }
            }}.build(), section.getBoolean("generate"));
        }
    }

    public static Color getColor(String color) {
        return color != null ? CommonUtils.getColor(color) : null;
    }

    public static String getString(ConfigurationSection section, String yamlKey, ReplaceText... text) {
        if (section.isString(yamlKey)) {
            return toText(section.getString(yamlKey), text);
        } else if (section.isList(yamlKey)) {
            return toText(String.join("\n", section.getStringList(yamlKey)), text);
        }
        return null;
    }
}