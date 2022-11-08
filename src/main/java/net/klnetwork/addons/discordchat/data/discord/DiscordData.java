package net.klnetwork.addons.discordchat.data.discord;

import net.dv8tion.jda.api.entities.TextChannel;
import net.klnetwork.addons.discordchat.DiscordChat;

public class DiscordData {
    private long textChannelId;
    private final boolean consoleLog, consoleCommand, commandLog, joinLog, leftLog, chatLog, chat;
    private TextChannel channel;

    public DiscordData(final long textChannelId, boolean consoleLog, boolean consoleCommand, boolean commandLog, boolean joinLog, boolean leftLog, boolean chatLog, boolean chat) {
        this.textChannelId = textChannelId;
        this.consoleLog = consoleLog;
        this.consoleCommand = consoleCommand;
        this.commandLog = commandLog;
        this.joinLog = joinLog;
        this.leftLog = leftLog;
        this.chatLog = chatLog;
        this.chat = chat;
    }

    public long getTextChannelId() {
        return textChannelId;
    }

    public void setTextChannelId(long textChannelId) {
        this.textChannelId = textChannelId;
    }

    public TextChannel getTextChannel() {
        if (channel == null) {
            channel = DiscordChat.getInstance().getJDA().getTextChannelById(textChannelId);
        }
        return channel;
    }

    public boolean isConsoleLog() {
        return consoleLog;
    }

    public boolean isConsoleCommand() {
        return consoleCommand;
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

    public boolean isChat() {
        return chat;
    }

    @Override
    public String toString() {
        return "DiscordData{" +
                "textChannelId=" + textChannelId +
                ", consoleLog=" + consoleLog +
                ", consoleCommand=" + consoleCommand +
                ", commandLog=" + commandLog +
                ", joinLog=" + joinLog +
                ", leftLog=" + leftLog +
                ", chatLog=" + chatLog +
                ", chat=" + chat +
                ", channel=" + channel +
                '}';
    }
}
