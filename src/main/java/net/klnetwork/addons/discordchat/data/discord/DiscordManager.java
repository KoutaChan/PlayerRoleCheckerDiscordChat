package net.klnetwork.addons.discordchat.data.discord;

import java.util.ArrayList;
import java.util.List;

public class DiscordManager {
    private static final List<DiscordData> dataStorage = new ArrayList<>();

    public static void add(DiscordData data)  {
        dataStorage.add(data);
    }

    public static List<DiscordData> get() {
        return dataStorage;
    }
}
