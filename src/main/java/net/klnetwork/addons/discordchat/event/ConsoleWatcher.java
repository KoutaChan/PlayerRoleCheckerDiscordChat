package net.klnetwork.addons.discordchat.event;

import net.dv8tion.jda.api.entities.TextChannel;
import net.klnetwork.addons.discordchat.data.discord.DiscordData;
import net.klnetwork.addons.discordchat.data.discord.DiscordManager;
import net.klnetwork.addons.discordchat.util.ConfigManager;
import net.klnetwork.addons.discordchat.util.ReplaceText;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.filter.AbstractFilter;
import org.apache.logging.log4j.core.layout.PatternLayout;

import java.text.SimpleDateFormat;
import java.util.Date;

import static net.klnetwork.addons.discordchat.util.LogManager.log;

public class ConsoleWatcher extends AbstractAppender {
    public static ConsoleWatcher currentWatcher = null;

    public ConsoleWatcher() {
        super("[PRCAddons] -> ConsoleWatcher"
                , new AbstractFilter() {}
                , PatternLayout.createDefaultLayout()
                , true
                , Property.EMPTY_ARRAY);
        start();
    }

    private final SimpleDateFormat simpleFormat = new SimpleDateFormat("HH:mm:ss");

    /* todo: can use EmbedMessage!*/
    @Override
    public void append(LogEvent event) {
        String formattedMessage = ConfigManager.getYaml("console-log"
                , new ReplaceText("%date%", simpleFormat.format(new Date()))
                , new ReplaceText("%level%", event.getLevel().name())
                , new ReplaceText("%msg%", event.getMessage().getFormattedMessage()));

        try {
            new Thread(() -> DiscordManager.get().stream()
                    .filter(DiscordData::isConsoleLog)
                    .forEachOrdered(data -> {
                        TextChannel textChannel = data.getTextChannel();
                        if (textChannel != null) {
                            textChannel.sendMessage(formattedMessage).queue();
                        }
                    }))
                    .start();
        } catch (Exception ex) {
            log("エラーがコンソールメッセージ受信で発生しました！ 停止します");
            onStop();
        }
    }

    public static void onStart() {
        getLogger().addAppender(currentWatcher = new ConsoleWatcher());
    }

    public static void onStop() {
        if (currentWatcher != null) {
            currentWatcher.stop();
            getLogger().removeAppender(currentWatcher);
        }
        currentWatcher = null;
    }

    public static Logger getLogger() {
        return (Logger) LogManager.getRootLogger();
    }
}