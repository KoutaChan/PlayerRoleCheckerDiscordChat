package net.klnetwork.addons.discordchat.util;

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

    public static ReplaceText of(String regex, String replacement) {
        return new ReplaceText(regex, replacement);
    }

    public ReplaceText[] defaultText() {
        return null;
    }
}
