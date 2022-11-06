package net.klnetwork.addons.discordchat.log;

public class ReplaceText {
    private final String regex, replacement;

    public ReplaceText(String regex, String replacement) {
        this.regex = regex;
        this.replacement = replacement;
    }

    public String getRegex() {
        return regex;
    }

    public String getReplacement() {
        return replacement;
    }

    public ReplaceText[] defaultText() {
        return null;
    }
}
