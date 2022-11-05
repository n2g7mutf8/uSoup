package me.n2g7mutf8.soup.listener.list;

import me.n2g7mutf8.soup.user.Profile;
import me.n2g7mutf8.soup.user.ProfileManager;
import me.n2g7mutf8.soup.utils.KitPvPUtils;
import me.n2g7mutf8.soup.utils.chat.ColorText;
import me.n2g7mutf8.soup.utils.player.PlayerUtils;
import me.n2g7mutf8.soup.utils.task.TaskUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class DeathListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Player killer = player.getKiller();

        Profile profile = ProfileManager.getProfile(player);
        Profile killerProfile = ProfileManager.getProfile(killer);

        if (profile.getCurrentKillstreak() >= 5) {
            Bukkit.broadcastMessage(ColorText.translate("&a" + player.getName() + "'s &7killstreak of &a" + profile.getCurrentKillstreak() + " &7was ended by &c" + killer.getName() + "&7!"));
        }
        int credits = KitPvPUtils.getRandomNumber(25) + 1;

        profile.setDeaths(profile.getDeaths() + 1);
        profile.setCurrentKillstreak(0);

        killerProfile.setKills(killerProfile.getKills() + 1);
        killerProfile.setCurrentKillstreak(killerProfile.getCurrentKillstreak() + 1);
        killerProfile.setCredits(killerProfile.getCredits() + credits);
        if (killerProfile.getCurrentKillstreak() > killerProfile.getHighestKillstreak()) {
            killerProfile.setHighestKillstreak(killerProfile.getCurrentKillstreak());
            killer.sendMessage(ColorText.translate("&7Your new highest streak is &b" + killerProfile.getHighestKillstreak()));
        }
        if (profile.getBounty() >= 1) {
            Bukkit.broadcastMessage(ColorText.translate("&a" + killer.getName() + " &7has claimed a &b$" + profile.getBounty() + " &7bounty on &c" + player.getName() + "&7! "));
            killerProfile.setCredits(killerProfile.getCredits() + profile.getBounty());
            profile.setBounty(0);
        }

        killer.sendMessage(ColorText.translate("&7You killed &c" + player.getName() + " &7for &$" + credits + "&7!"));

        TaskUtil.runTaskLater(new BukkitRunnable() {
            @Override
            public void run() {
                PlayerUtils.resetPlayer(player);
            }
        }, 5);
    }
}
