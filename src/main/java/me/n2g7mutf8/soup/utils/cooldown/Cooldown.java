package me.n2g7mutf8.soup.utils.cooldown;

import lombok.Getter;
import me.n2g7mutf8.soup.utils.chat.ColorText;
import me.n2g7mutf8.soup.utils.task.TaskUtil;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class Cooldown {

    @Getter
    private static final Map<String, Cooldown> cooldownMap = new HashMap<>();
    private final Map<UUID, Long> longMap;
    private final String name;
    private final String displayName;
    private final long duration;
    private String expiredMessage;

    public Cooldown(String name, long duration) {
        this(name, duration, null, null);
    }

    public Cooldown(String name, long duration, String displayName, String expiredMessage) {
        this.longMap = new HashMap<>();
        this.name = name;
        this.duration = duration;
        this.displayName = ((displayName == null) ? name : displayName);
        if (expiredMessage != null) {
            this.expiredMessage = expiredMessage;
        }
        cooldownMap.put(name, this);
    }

    public void setCooldown(Player player) {
        this.setCooldown(player, false);
    }

    public void setCooldown(Player player, boolean announce) {
        CooldownStartingEvent event = new CooldownStartingEvent(player, this);
        if (!event.call()) {
            if (event.getReason() != null) {
                player.sendMessage(ColorText.translate(event.getReason()));
            }
            return;
        }
        this.longMap.put(player.getUniqueId(), System.currentTimeMillis() + this.duration);
        if (new CooldownStartedEvent(player, this).call()) {
            if (this.expiredMessage != null && announce) {
                TaskUtil.runTaskLater(() -> {
                    if (player.isOnline() && isOnCooldown(player)) {
                        for (String s : expiredMessage.split("\n")) {
                            player.sendMessage(ColorText.translate(s));
                        }
                        new CooldownExpiredEvent(player, this).call();
                    }
                }, (int) this.duration / 1000 * 20L);
            }
        }
    }

    public long getDuration(Player player) {
        long toReturn;
        if (this.longMap.containsKey(player.getUniqueId()) && (toReturn = this.longMap.get(player.getUniqueId()) - System.currentTimeMillis()) > 0L) {
            return toReturn;
        }
        return 0L;
    }

    public boolean isOnCooldown(Player player) {
        return this.getDuration(player) > 0L;
    }

    public boolean remove(Player player) {
        if (isOnCooldown(player)) {
            this.longMap.remove(player.getUniqueId());
            new CooldownExpiredEvent(player, this).setForced(true).call();
        }
        return isOnCooldown(player);
    }
}
