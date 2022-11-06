package net.klnetwork.addons.discordchat;

import net.klnetwork.addons.discordchat.event.ConsoleWatcher;
import net.klnetwork.addons.discordchat.log.LogManager;
import net.klnetwork.playerrolechecker.api.PlayerRoleCheckerAPI;
import net.klnetwork.playerrolechecker.api.data.APIHook;
import net.klnetwork.playerrolechecker.api.data.JoinManager;
import net.klnetwork.playerrolechecker.api.data.connector.ConnectorAPIHook;
import net.klnetwork.playerrolechecker.api.enums.HookedAPIType;
import net.klnetwork.playerrolechecker.api.utils.Metrics;
import net.klnetwork.playerrolechecker.api.utils.updater.UpdateAlert;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class DiscordChat extends JavaPlugin implements APIHook {
    private static DiscordChat INSTANCE;
    private static ConnectorAPIHook connectedHook;

    @Override
    public void onLoad() {
        INSTANCE = this;

        if (getConfig().getBoolean("global-settings.console-log")) {
            ConsoleWatcher.onStart();
        }
    }

    @Override
    public void onEnable() {
        if (connectHook()) {
            ConfigurationSection section = getConfig().getConfigurationSection("log");

            section.getKeys(false).forEach(key -> {
                ConfigurationSection values = section.getConfigurationSection(key);
            });
        }
        // Plugin startup logic
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        ConsoleWatcher.onStop();
    }

    public static DiscordChat getInstance() {
        return INSTANCE;
    }

    public static boolean connectHook() {
        ConnectorAPIHook hook = PlayerRoleCheckerAPI.getConnectorAPI();
        final boolean connected = hook != null;

        if (connected) {
            connectedHook = hook;
            LogManager.logYaml("success-hook");
        }
        return connected;
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

    public boolean isConnected() {
        return connectedHook != null;
    }
}
