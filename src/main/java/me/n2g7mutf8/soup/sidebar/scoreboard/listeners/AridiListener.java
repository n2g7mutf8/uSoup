package me.n2g7mutf8.soup.sidebar.scoreboard.listeners;

import me.n2g7mutf8.soup.SoupPvP;
import me.n2g7mutf8.soup.sidebar.scoreboard.Aridi;
import me.n2g7mutf8.soup.sidebar.scoreboard.AridiManager;
import me.n2g7mutf8.soup.sidebar.scoreboard.events.PlayerLoadScoreboardEvent;
import me.n2g7mutf8.soup.sidebar.scoreboard.events.PlayerUnloadScoreboardEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class AridiListener implements Listener {

    private final SoupPvP plugin = SoupPvP.getInstance();
    private final AridiManager aridiManager = plugin.getAridiManager();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (aridiManager != null) {
            Aridi aridi = new Aridi(player, aridiManager);
            aridiManager.getAridiMap().put(player.getUniqueId(), aridi);
            new PlayerLoadScoreboardEvent(player, aridi).call();
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (aridiManager != null && aridiManager.getAridiMap().containsKey(player.getUniqueId())) {
            new PlayerUnloadScoreboardEvent(player, aridiManager.getAridiMap().get(player.getUniqueId())).call();
            aridiManager.getAridiMap().remove(player.getUniqueId());
        }
    }
}