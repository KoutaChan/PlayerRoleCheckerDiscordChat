package net.klnetwork.addons.discordchat.data.discord;

import net.klnetwork.addons.discordchat.util.LogManager;

import java.util.ArrayList;
import java.util.List;

public class DiscordManager {
    private static final List<DiscordData> dataStorage = new ArrayList<>();

    public static void add(DiscordData data)  {
        if (data.getTextChannelId() == 0) {
            LogManager.logYaml("text-channel-id-zero");
            throw new IllegalStateException("Received Zero");
        }

        dataStorage.add(data);
    }

    public static List<DiscordData> get() {
        return dataStorage;
    }
}
