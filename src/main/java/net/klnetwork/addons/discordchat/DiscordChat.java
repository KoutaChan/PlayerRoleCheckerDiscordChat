package net.klnetwork.addons.discordchat;

import net.klnetwork.addons.discordchat.api.ExtendedAPI;
import net.klnetwork.addons.discordchat.data.discord.DiscordData;
import net.klnetwork.addons.discordchat.data.discord.DiscordManager;
import net.klnetwork.addons.discordchat.event.ConsoleWatcher;
import net.klnetwork.addons.discordchat.util.LogManager;
import net.klnetwork.addons.discordchat.util.ReplaceText;
import net.klnetwork.playerrolechecker.api.PlayerRoleCheckerAPI;
import net.klnetwork.playerrolechecker.api.data.JoinManager;
import net.klnetwork.playerrolechecker.api.data.connector.ConnectorAPIHook;
import net.klnetwork.playerrolechecker.api.enums.HookedAPIType;
import net.klnetwork.playerrolechecker.api.utils.Metrics;
import net.klnetwork.playerrolechecker.api.utils.updater.UpdateAlert;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class DiscordChat extends JavaPlugin implements ExtendedAPI {
    private static DiscordChat INSTANCE;
    private static ConnectorAPIHook connectedHook;

    private final Metrics metrics = new Metrics(this, 16812);

    @Override
    public void onLoad() {
        INSTANCE = this;
        saveDefaultConfig();
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        if (connectHook()) {
            ConfigurationSection section = getConfig().getConfigurationSection("log");

            section.getKeys(false).forEach(key -> {
                ConfigurationSection values = section.getConfigurationSection(key);

                DiscordManager.add(new DiscordData(
                        values.getLong("channelId"),
                        values.getBoolean("advanced-settings.console-log"),
                        values.getBoolean("advanced-settings.command-log"),
                        values.getBoolean("advanced-settings.join-log"),
                        values.getBoolean("advanced-settings.left-log"),
                        values.getBoolean("advanced-settings.chat-log")
                ));
                LogManager.logYaml(Level.INFO, "success-register", new ReplaceText("%name%", key));
            });

            if (getConfig().getBoolean("global-settings.console-log")) {
                ConsoleWatcher.onStart();
            }

            Runtime.getRuntime().addShutdownHook(new Thread(ConsoleWatcher::onStop));
        } else {
            LogManager.logYaml(Level.SEVERE, "cannot-find-hook");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        /* */
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

    @Override
    public Metrics getMetrics() {
        return metrics;
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

    @Override
    public boolean isConnected() {
        return connectedHook != null;
    }
}
