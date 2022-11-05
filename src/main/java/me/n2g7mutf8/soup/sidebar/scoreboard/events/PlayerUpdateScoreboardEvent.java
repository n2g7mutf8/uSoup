package me.n2g7mutf8.soup.sidebar.scoreboard.events;

import lombok.Getter;
import me.n2g7mutf8.soup.sidebar.scoreboard.Aridi;
import me.n2g7mutf8.soup.utils.cooldown.PlayerBase;
import org.bukkit.entity.Player;

@Getter
public class PlayerUpdateScoreboardEvent extends PlayerBase {

    private final Aridi scoreboard;

    public PlayerUpdateScoreboardEvent(Player player, Aridi scoreboard) {
        super(player);
        this.scoreboard = scoreboard;
    }
}