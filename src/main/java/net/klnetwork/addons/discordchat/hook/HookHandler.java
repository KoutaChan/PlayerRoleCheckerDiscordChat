package net.klnetwork.addons.discordchat.hook;

import net.klnetwork.addons.discordchat.hook.essentialsX.EssentialsXListener;
import net.klnetwork.addons.discordchat.hook.essentialsX.SuperVanishListener;
import net.klnetwork.addons.discordchat.hook.essentialsX.VanishNoPacketListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class HookHandler {
    public static void start(final Plugin plugin, YamlConfiguration config) {
        if (Bukkit.getPluginManager().isPluginEnabled("Essentials") && config.getBoolean("hook-plugin.hook-essentials-x")) {
            Bukkit.getPluginManager().registerEvents(new EssentialsXListener(), plugin);
        }

        if ((Bukkit.getPluginManager().isPluginEnabled("SuperVanish") || Bukkit.getPluginManager().isPluginEnabled("PremiumVanish"))
                && config.getBoolean("hook-plugin.hook-super-vanish")) {
            Bukkit.getPluginManager().registerEvents(new SuperVanishListener(), plugin);
        }

        if (Bukkit.getPluginManager().isPluginEnabled("VanishNoPacket") && config.getBoolean("hook-plugin.hook-vanish-no-packet")) {
            Bukkit.getPluginManager().registerEvents(new VanishNoPacketListener(), plugin);
        }

    }
}