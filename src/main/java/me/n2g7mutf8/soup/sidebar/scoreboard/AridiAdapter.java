package me.n2g7mutf8.soup.sidebar.scoreboard;

import org.bukkit.entity.Player;

import java.util.List;

public interface AridiAdapter {

    String getTitle(Player player);

    List<String> getLines(Player player);

    AridiStyle getAridiStyle(Player player);
}