package me.n2g7mutf8.soup.listener.list;

import me.n2g7mutf8.soup.SoupPvP;
import me.n2g7mutf8.soup.enums.PlayerState;
import me.n2g7mutf8.soup.user.Profile;
import me.n2g7mutf8.soup.user.ProfileManager;
import me.n2g7mutf8.soup.utils.cooldown.CooldownExpiredEvent;
import me.n2g7mutf8.soup.utils.location.LocationUtils;
import me.n2g7mutf8.soup.utils.player.PlayerUtils;
import me.n2g7mutf8.soup.utils.task.TaskUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class CooldownListener implements Listener {

    @EventHandler
    public void onCooldownExpired(CooldownExpiredEvent event) {
        if (event.isForced()) {
            event.getPlayer().setLevel(0);
            return;
        }
        Player player = event.getPlayer();
        if (event.getCooldown() == SoupPvP.getCooldown("Spawn")) {
            TaskUtil.runTask(() -> PlayerUtils.resetPlayer(player, false, true));
        }
        if (event.getCooldown() == SoupPvP.getCooldown("FlyExpire")) {
            player.setAllowFlight(false);
            player.setFlying(false);
        }
    }
}
