package net.klnetwork.addons.discordchat.event.log;

import net.dv8tion.jda.api.entities.TextChannel;
import net.klnetwork.addons.discordchat.data.discord.DiscordData;
import net.klnetwork.addons.discordchat.data.discord.DiscordManager;
import net.klnetwork.addons.discordchat.util.ColorUtils;
import net.klnetwork.addons.discordchat.util.ConfigManager;
import net.klnetwork.addons.discordchat.util.ReplaceText;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.filter.AbstractFilter;
import org.apache.logging.log4j.core.layout.PatternLayout;

import java.text.SimpleDateFormat;
import java.util.Date;

import static net.klnetwork.addons.discordchat.util.LogManager.log;

@Plugin(name = "ConsoleWatcher", category = "Core", elementType = "appender", printObject = true)
public class ConsoleWatcher extends AbstractAppender {
    public static ConsoleWatcher currentWatcher = null;

    public ConsoleWatcher() {
        super("ConsoleWatcher"
                , new AbstractFilter() {}
                , PatternLayout.newBuilder().withPattern("[%d{HH:mm:ss} %level]: %msg").build()
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
                , new ReplaceText("%msg%", ColorUtils.removeConsoleColor(event.getMessage().getFormattedMessage()).replaceAll("\u007F", "\u00a7"))
                , new ReplaceText("%logger_name%", event.getLoggerName() == null ||
                        event.getLoggerName().startsWith("net.minecraft") || event.getLoggerName().isEmpty() || event.getLoggerName().equals("Minecraft")
                        ? "" : "[" + event.getLoggerName() + "]"));
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
            log("エラーがコンソールメッセージ受信で発生しました！");
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