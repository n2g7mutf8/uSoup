package me.n2g7mutf8.soup.utils.cooldown;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;

@AllArgsConstructor
@Getter
public class PlayerBase extends CustomEvent {

    private Player player;
}
