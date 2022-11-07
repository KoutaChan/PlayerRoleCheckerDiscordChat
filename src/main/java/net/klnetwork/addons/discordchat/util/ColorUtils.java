package net.klnetwork.addons.discordchat.util;

import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColorUtils {
    private final static Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
    private final static String COLOR_CHAR = "\u00A7";

    public static String toHex(String message) {
        Matcher matcher = pattern.matcher(message);

        while (matcher.find()) {
            String hexCode = matcher.group();

            message = message.replaceFirst(hexCode, COLOR_CHAR + "x"
                    + COLOR_CHAR + hexCode.charAt(1) + COLOR_CHAR + hexCode.charAt(2)
                    + COLOR_CHAR + hexCode.charAt(3) + COLOR_CHAR + hexCode.charAt(4)
                    + COLOR_CHAR + hexCode.charAt(5) + COLOR_CHAR + hexCode.charAt(6));
        }

        return message;
    }

    public static String colorToHex(Color color) {
        return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }

    public static String colorToHex(int raw) {
        return String.format("#%06x", raw & 0x00FFFFFF);
    }

    //CREDITS: https://github.com/Scarsz/JDAAppender/blob/3f1fb01f0e44dd9a4542863404845f35140dcd06/src/main/java/me/scarsz/jdaappender/LogItem.java#L114
    private static final Pattern colorPattern = Pattern.compile("\u001B(?:\\[0?m|\\[38;2(?:;\\d{1,3}){3}m|\\[([0-9]{1,2}[;m]?){3})");

    public static String removeConsoleColor(String message) {
        return colorPattern.matcher(message).replaceAll("");
    }

}