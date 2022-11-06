package net.klnetwork.addons.discordchat.event;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.filter.AbstractFilter;
import org.apache.logging.log4j.core.layout.PatternLayout;

public class ConsoleWatcher extends AbstractAppender {
    public static ConsoleWatcher currentWatcher = null;

    protected ConsoleWatcher() {
        super("[PRCAddons] -> ConsoleWatcher"
                , new AbstractFilter() {}
                , PatternLayout.newBuilder()
                        .withPattern("[%d{HH:mm:ss} %level]: %msg")
                        .build()
                , true
                , null);
        start();
    }

    @Override
    public void append(LogEvent event) {
        //event.getMessage().getFormattedMessage();
    }

    public static void onStart() {
        getLogger().addAppender(currentWatcher = new ConsoleWatcher());
    }

    public static void onStop() {
        if (currentWatcher != null) {
            getLogger().removeAppender(currentWatcher);
        }
    }

    public static Logger getLogger() {
        return (Logger) LogManager.getRootLogger();
    }
}