package net.klnetwork.addons.discordchat;

import net.klnetwork.playerrolechecker.api.PlayerRoleCheckerAPI;
import net.klnetwork.playerrolechecker.api.data.APIHook;
import net.klnetwork.playerrolechecker.api.data.JoinManager;
import net.klnetwork.playerrolechecker.api.data.codeapi.CodeAPIHook;
import net.klnetwork.playerrolechecker.api.data.connector.ConnectorAPIHook;
import net.klnetwork.playerrolechecker.api.enums.HookedAPIType;
import net.klnetwork.playerrolechecker.api.utils.Metrics;
import net.klnetwork.playerrolechecker.api.utils.updater.UpdateAlert;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class DiscordChat extends JavaPlugin implements APIHook {
    private static DiscordChat INSTANCE;
    private static ConnectorAPIHook connectedHook;

    @Override
    public void onLoad() {
        INSTANCE = this;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static DiscordChat getInstance() {
        return INSTANCE;
    }

    public static void connectHook() {
        ConnectorAPIHook hook = PlayerRoleCheckerAPI.getConnectorAPI();

        if (hook != null) {
            //Todo: Add Message!
            connectedHook = hook;
        }
    }

    public static ConnectorAPIHook getConnectedHook() {
        if (connectedHook == null) {
            connectHook();
        }

        return connectedHook;
    }

    @Override
    public Plugin getPlugin() {
        return this;
    }

    @Override //Todo Add Metrics
    public Metrics getMetrics() {
        return null;
    }

    @Override
    public JoinManager getJoinManager() {
        return null;
    }

    @Override //Todo Add UpdateAlerts
    public UpdateAlert getUpdateAlert() {
        return null;
    }

    @Override
    public HookedAPIType getType() {
        return HookedAPIType.CUSTOM;
    }
}
