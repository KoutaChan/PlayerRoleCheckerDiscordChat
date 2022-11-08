package net.klnetwork.addons.discordchat.api;

import net.dv8tion.jda.api.JDA;
import net.klnetwork.playerrolechecker.api.data.APIHook;

public interface ExtendedAPI extends APIHook {
    boolean isConnected();

    JDA getJDA();
}
