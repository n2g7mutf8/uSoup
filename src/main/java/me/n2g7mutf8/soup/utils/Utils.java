package me.n2g7mutf8.soup.utils;

import org.bukkit.ChatColor;

import java.text.DecimalFormat;

public class Utils {

    private static final String[] suffix = new String[]{"", "K", "M", "B", "T"};

    public static String getFormat(double number) {
        String format = new DecimalFormat("##0E0").format(number);
        format = format.replaceAll("E[0-9]", suffix[Character.getNumericValue(format.charAt(format.length() - 1)) / 3]);
        while (format.length() > 4 || format.matches("[0-9]+\\.[a-z]")) {
            format = format.substring(0, format.length() - 2) + format.substring(format.length() - 1);
        }
        return format;
    }

    public static ChatColor getColorPrefix(String prefix) {
        if (prefix.isEmpty()) {
            return ChatColor.WHITE;
        }
        char code = 'f';
        for (String string : prefix.split("&")) {
            if ((!string.isEmpty()) && (ChatColor.getByChar(string.toCharArray()[0]) != null)) {
                if (!isMagic(string.toCharArray()[0])) {
                    code = string.toCharArray()[0];
                }
            }
        }
        return ChatColor.getByChar(code);
    }

    private static boolean isMagic(char string) {
        return string == 'o' || string == 'l' || string == 'k' || string == 'n' || string == 'm';
    }
}