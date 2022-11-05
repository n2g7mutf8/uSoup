package me.n2g7mutf8.soup.sidebar.scoreboard;

import lombok.Getter;
import me.n2g7mutf8.soup.utils.KitPvPUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class Aridi {

    private final List<AridiEntry> entries = new ArrayList<>();
    private final List<String> strings = new ArrayList<>();
    private final UUID id;
    private final AridiManager aridiManager;
    private Scoreboard scoreboard;
    private Objective objective;

    public Aridi(Player player, AridiManager aridiManager) {
        this.id = player.getUniqueId();
        this.aridiManager = aridiManager;
        setUp(player);
    }

    private static String getRandomColor() {
        return ChatColor.values()[KitPvPUtils.getRandomNumber(ChatColor.values().length)].toString();
    }

    public void setUp(Player player) {
        if (player.getScoreboard() != Bukkit.getScoreboardManager().getMainScoreboard()) {
            scoreboard = player.getScoreboard();
        } else {
            scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        }

        objective = scoreboard.registerNewObjective("AxisPlugin", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(aridiManager.getAdapter().getTitle(player));

        player.setScoreboard(scoreboard);
    }

    String getUniqueString() {
        String string = getRandomColor();
        while (strings.contains(string)) {
            string = string + getRandomColor();
        }

        if (string.length() > 16) {
            return getUniqueString();
        }

        strings.add(string);
        return string;
    }

    AridiEntry getEntryAtPosition(int position) {
        if (position >= entries.size()) {
            return null;
        }
        return entries.get(position);
    }
}