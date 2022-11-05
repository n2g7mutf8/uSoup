package me.n2g7mutf8.soup.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerUseAbilityEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    String a;
    Player p;

    public PlayerUseAbilityEvent(Player var1, String var2) {
        this.p = var1;
        this.a = var2;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public Player getPlayer() {
        return this.p;
    }

    public String getAbility() {
        return this.a;
    }
}
