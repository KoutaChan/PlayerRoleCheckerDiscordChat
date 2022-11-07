package net.klnetwork.addons.discordchat.hook;

import net.klnetwork.addons.discordchat.hook.legacy.VanishLegacy;
import net.klnetwork.addons.discordchat.hook.plugins.EssentialsXListener;
import net.klnetwork.addons.discordchat.hook.plugins.SuperVanishListener;
import net.klnetwork.addons.discordchat.hook.plugins.VanishNoPacketListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class HookHandler {
    private static boolean hookedEssentialsX, hookedSuperVanish, hookedVanishNoPacket;

    public static void start(final Plugin plugin, YamlConfiguration config) {
        hookedEssentialsX = Bukkit.getPluginManager().isPluginEnabled("Essentials") && config.getBoolean("hook-plugin.hook-essentials-x");
        if (hookedEssentialsX) {
            Bukkit.getPluginManager().registerEvents(new EssentialsXListener(), plugin);
        }

        hookedSuperVanish = (Bukkit.getPluginManager().isPluginEnabled("SuperVanish") || Bukkit.getPluginManager().isPluginEnabled("PremiumVanish"))
                && config.getBoolean("hook-plugin.hook-super-vanish");
        if (hookedSuperVanish) {
            Bukkit.getPluginManager().registerEvents(new SuperVanishListener(), plugin);
        }

        hookedVanishNoPacket = Bukkit.getPluginManager().isPluginEnabled("VanishNoPacket") && config.getBoolean("hook-plugin.hook-vanish-no-packet");
        if (hookedVanishNoPacket) {
            Bukkit.getPluginManager().registerEvents(new VanishNoPacketListener(), plugin);
        }

        if (config.getBoolean("legacy-vanish")) {
            Bukkit.getPluginManager().registerEvents(new VanishLegacy(), plugin);
        }
    }

    public static boolean isHookedEssentialsX() {
        return hookedEssentialsX;
    }

    public static boolean isHookedSuperVanish() {
        return hookedSuperVanish;
    }

    public static boolean isHookedVanishNoPacket() {
        return hookedVanishNoPacket;
    }
}