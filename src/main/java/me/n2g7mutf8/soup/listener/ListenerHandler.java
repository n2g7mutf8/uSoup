package me.n2g7mutf8.soup.listener;

import me.n2g7mutf8.soup.SoupPvP;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public class ListenerHandler {

    public static void registerListeners(Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, SoupPvP.getInstance());
        }
    }
}
