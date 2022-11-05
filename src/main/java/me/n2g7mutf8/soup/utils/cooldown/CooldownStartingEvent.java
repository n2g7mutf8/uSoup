package me.n2g7mutf8.soup.utils.cooldown;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

@Getter
public class CooldownStartingEvent extends PlayerBase {

    private final Cooldown cooldown;
    @Setter
    private String reason;

    public CooldownStartingEvent(Player player, Cooldown cooldown) {
        super(player);
        this.cooldown = cooldown;
    }

}
