package net.klnetwork.addons.discordchat.util;

import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.utils.FileUpload;

public class DiscordUtils {
    public static void sendMessage(TextChannel channel, Object object) {
        if (object instanceof MessageEmbed) {
            channel.sendMessageEmbeds((MessageEmbed) object).queue();
        } else if (object instanceof String) {
            channel.sendMessage((String) object).queue();
        }
    }

    public static void sendMessage(TextChannel channel, Object object, byte[] avatar) {
        if (avatar == null || object instanceof String) {
            sendMessage(channel, object);
        } else if (object instanceof MessageEmbed) {
            channel.sendMessageEmbeds((MessageEmbed) object).addFiles(FileUpload.fromData(avatar, "avatar.png")).queue();
        }
    }
}
