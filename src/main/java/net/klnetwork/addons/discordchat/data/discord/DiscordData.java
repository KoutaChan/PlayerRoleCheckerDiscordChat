package net.klnetwork.addons.discordchat.data.discord;

import net.dv8tion.jda.api.entities.TextChannel;
import net.klnetwork.addons.discordchat.DiscordChat;

public class DiscordData {
    private long textChannelId;
    private final boolean consoleLog, commandLog, joinLog, leftLog, chatLog;
    private TextChannel channel;

    public DiscordData(final long textChannelId, boolean consoleLog, boolean commandLog, boolean joinLog, boolean leftLog, boolean chatLog) {
        this.textChannelId = textChannelId;
        this.consoleLog = consoleLog;
        this.commandLog = commandLog;
        this.joinLog = joinLog;
        this.leftLog = leftLog;
        this.chatLog = chatLog;
    }

    public long getTextChannelId() {
        return textChannelId;
    }

    public void setTextChannelId(long textChannelId) {
        this.textChannelId = textChannelId;
    }

    public TextChannel getTextChannel() {
        if (channel == null) {
            channel = DiscordChat.getConnectedHook().getJDA().getTextChannelById(textChannelId);
        }
        return channel;
    }

    public boolean isConsoleLog() {
        return consoleLog;
    }

    public boolean isCommandLog() {
        return commandLog;
    }

    public boolean isJoinLog() {
        return joinLog;
    }

    public boolean isLeftLog() {
        return leftLog;
    }

    public boolean isChatLog() {
        return chatLog;
    }

    @Override
    public String toString() {
        return "DiscordData{" +
                "textChannelId=" + textChannelId +
                ", consoleLog=" + consoleLog +
                ", commandLog=" + commandLog +
                ", joinLog=" + joinLog +
                ", leftLog=" + leftLog +
                ", chatLog=" + chatLog +
                '}';
    }
}
