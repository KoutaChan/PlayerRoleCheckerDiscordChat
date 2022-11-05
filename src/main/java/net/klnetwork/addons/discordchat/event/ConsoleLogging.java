package net.klnetwork.addons.discordchat.event;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.bukkit.Bukkit;

import java.io.Serializable;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class ConsoleLogging extends AbstractAppender {
    protected ConsoleLogging() {
        super("[PRC] -> ConsoleLogging"
                , null
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

    public void startLogging() {
        Logger logger = (Logger) LogManager.getRootLogger();
        logger.addAppender(new ConsoleLogging());
    }
}