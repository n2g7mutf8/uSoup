package me.n2g7mutf8.soup.utils.cooldown;

import lombok.Getter;
import org.bukkit.entity.Player;

public class CooldownStartedEvent extends PlayerBase {

    @Getter
    private final Cooldown cooldown;

    public CooldownStartedEvent(Player player, Cooldown cooldown) {
        super(player);
        this.cooldown = cooldown;
    }
}
