package me.n2g7mutf8.soup.utils.chat;

import org.bukkit.ChatColor;

import java.util.List;

public class ColorText {

    public static String CHAT_BAR = translate("&7&m----------------------");

    public static String translate(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static List<String> translate(List<String> message) {
        for (int i = 0; i < message.size(); i++) {
            message.set(i, translate(message.get(i)));
        }
        return message;
    }
}