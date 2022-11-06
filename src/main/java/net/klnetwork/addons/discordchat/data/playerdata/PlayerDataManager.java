package net.klnetwork.addons.discordchat.data.playerdata;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerDataManager {
    private final static Map<UUID, PlayerData> dataStorage = new HashMap<>();

    public static PlayerData get(Player player) {
        return get(player.getUniqueId());
    }

    public static PlayerData get(UUID uuid) {
        return dataStorage.get(uuid);
    }

    public static Map<UUID, PlayerData> get() {
        return dataStorage;
    }

    public static void put(UUID uuid, PlayerData data) {
        dataStorage.put(uuid, data);
    }

    public static void remove(UUID uuid) {
        dataStorage.remove(uuid);
    }
}