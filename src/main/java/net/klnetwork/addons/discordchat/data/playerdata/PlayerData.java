package net.klnetwork.addons.discordchat.data.playerdata;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class PlayerData {
    private final Player player;
    private final net.klnetwork.playerrolechecker.api.data.common.PlayerData data;

    public PlayerData(Player player, net.klnetwork.playerrolechecker.api.data.common.PlayerData data) {
        this.player = player;
        this.data = data;
    }

    public Player getPlayer() {
        return player;
    }

    @Nullable
    public net.klnetwork.playerrolechecker.api.data.common.PlayerData getPlayerData() {
        return data;
    }
}
