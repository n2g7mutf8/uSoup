package me.n2g7mutf8.soup.utils;

import me.n2g7mutf8.soup.utils.item.ItemMaker;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class WoolUtils {

    private static final Map<ChatColor, DyeColor> colorHashMap = new HashMap<>();

    static {
        colorHashMap.put(ChatColor.WHITE, DyeColor.WHITE);
        colorHashMap.put(ChatColor.RED, DyeColor.RED);
        colorHashMap.put(ChatColor.BLACK, DyeColor.BLACK);
        colorHashMap.put(ChatColor.BLUE, DyeColor.LIGHT_BLUE);
        colorHashMap.put(ChatColor.LIGHT_PURPLE, DyeColor.PINK);
        colorHashMap.put(ChatColor.AQUA, DyeColor.CYAN);
        colorHashMap.put(ChatColor.DARK_AQUA, DyeColor.CYAN);
        colorHashMap.put(ChatColor.DARK_BLUE, DyeColor.BLUE);
        colorHashMap.put(ChatColor.GREEN, DyeColor.LIME);
        colorHashMap.put(ChatColor.DARK_GREEN, DyeColor.GREEN);
        colorHashMap.put(ChatColor.DARK_RED, DyeColor.RED);
        colorHashMap.put(ChatColor.GRAY, DyeColor.GRAY);
        colorHashMap.put(ChatColor.DARK_GRAY, DyeColor.GRAY);
        colorHashMap.put(ChatColor.YELLOW, DyeColor.YELLOW);
        colorHashMap.put(ChatColor.DARK_PURPLE, DyeColor.PURPLE);
        colorHashMap.put(ChatColor.GOLD, DyeColor.ORANGE);
    }

    public static ItemStack getWool(ChatColor chatColor) {
        return new ItemMaker(Material.WOOL).setDurability(colorHashMap.getOrDefault(chatColor, DyeColor.WHITE).getData()).create();
    }
}