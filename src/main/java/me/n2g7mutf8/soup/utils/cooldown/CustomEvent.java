package me.n2g7mutf8.soup.utils.cooldown;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CustomEvent extends Event {

    @Getter
    private static final HandlerList handlerList = new HandlerList();

    @Getter
    private boolean cancelled;

    public CustomEvent setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
        return this;
    }

    public boolean call() {
        if (!this.isCancelled()) {
            Bukkit.getPluginManager().callEvent(this);
        }
        return !this.isCancelled();
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
