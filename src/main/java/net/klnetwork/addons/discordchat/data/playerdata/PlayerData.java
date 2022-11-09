package net.klnetwork.addons.discordchat.data.playerdata;

import net.klnetwork.addons.discordchat.DiscordChat;
import net.klnetwork.addons.discordchat.util.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class PlayerData {
    private final Player player;
    private final net.klnetwork.playerrolechecker.api.data.common.PlayerData data;
    private final PlayerUtils.Property property;
    private byte[] head;

    public PlayerData(Player player, net.klnetwork.playerrolechecker.api.data.common.PlayerData data) {
        this.player = player;
        this.property = PlayerUtils.getProperty(player);
        this.data = data;

        Bukkit.getScheduler().runTaskAsynchronously(DiscordChat.getInstance(), () -> {
            head = property.getHeadSkin();
        });
    }

    public Player getPlayer() {
        return player;
    }

    public PlayerUtils.Property getProperty() {
        return property;
    }

    public byte[] getSkin() {
        return head;
    }

    @Nullable
    public net.klnetwork.playerrolechecker.api.data.common.PlayerData getPlayerData() {
        return data;
    }
}