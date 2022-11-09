package net.klnetwork.addons.discordchat;

import com.sun.management.OperatingSystemMXBean;
import net.dv8tion.jda.api.JDA;
import net.klnetwork.addons.discordchat.api.ExtendedAPI;
import net.klnetwork.addons.discordchat.api.GenerateType;
import net.klnetwork.addons.discordchat.data.JDAManager;
import net.klnetwork.addons.discordchat.data.discord.DiscordData;
import net.klnetwork.addons.discordchat.data.discord.DiscordManager;
import net.klnetwork.addons.discordchat.event.DiscordEvent;
import net.klnetwork.addons.discordchat.event.EventListener;
import net.klnetwork.addons.discordchat.event.log.ConsoleWatcher;
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

import java.lang.management.ManagementFactory;

public final class DiscordChat extends JavaPlugin implements ExtendedAPI {
    private static DiscordChat INSTANCE;
    private static ConnectorAPIHook connectedHook;

    private final Metrics metrics = new Metrics(this, 16812);
    private final JDAManager jdaManager = new JDAManager(this);
    private final GenerateType generateType = GenerateType.valueOf(getConfig().getString("global-settings.currentSkinGenerate").toUpperCase());

    @Override
    public void onLoad() {
        INSTANCE = this;
        saveDefaultConfig();
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        if (getConfig().getBoolean("global-settings.dontConnectPlayerRoleChecker") || connectHook()) {
            if (getConfig().getBoolean("global-settings.console-log")) {
                ConsoleWatcher.onStart();
            }
            jdaManager.getJDA().addEventListener(new DiscordEvent());

            ConfigurationSection section = getConfig().getConfigurationSection("log");

            section.getKeys(false).forEach(key -> {
                ConfigurationSection values = section.getConfigurationSection(key);

                DiscordManager.add(new DiscordData(
                        values.getLong("channelId"),
                        values.getBoolean("advanced-settings.console-log"),
                        values.getBoolean("advanced-settings.console-command"),
                        values.getBoolean("advanced-settings.command-log"),
                        values.getBoolean("advanced-settings.join-log"),
                        values.getBoolean("advanced-settings.left-log"),
                        values.getBoolean("advanced-settings.chat-log"),
                        values.getBoolean("advanced-settings.chat")
                ));
                LogManager.logYaml("success-register", new ReplaceText("%name%", key));
            });

            Bukkit.getPluginManager().registerEvents(new EventListener(), this);
        } else {
            LogManager.logYaml("cannot-find-hook");
            Bukkit.getPluginManager().disablePlugin(this);
        }
        Bukkit.getScheduler().runTaskTimerAsynchronously(DiscordChat.getInstance(), () -> {
            OperatingSystemMXBean osMBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
            //oMethod(osMBean);
        }, 0, 40);
    }

    public void oMethod(OperatingSystemMXBean osMBean) {


        System.out.println("Cpu usage: "+ (osMBean.getSystemCpuLoad() * 100)+"%");
    }

    @Override
    public void onDisable() {
        ConsoleWatcher.onStop();

        if (jdaManager.isLocalJDA()) {
            jdaManager.stop();
        }
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

    public GenerateType getGenerateType() {
        return generateType;
    }

    @Override
    public HookedAPIType getType() {
        return HookedAPIType.CUSTOM;
    }

    @Override
    public JDA getJDA() {
        return jdaManager.getJDA();
    }

    @Override
    public boolean isConnected() {
        return connectedHook != null;
    }
}
