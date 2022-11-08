package me.n2g7mutf8.soup.listener.list;

import me.n2g7mutf8.soup.SoupPvP;
import me.n2g7mutf8.soup.user.Profile;
import me.n2g7mutf8.soup.user.ProfileManager;
import me.n2g7mutf8.soup.utils.SoupUtils;
import me.n2g7mutf8.soup.utils.chat.ColorText;
import me.n2g7mutf8.soup.utils.item.XMaterial;
import me.n2g7mutf8.soup.utils.player.PlayerUtils;
import me.n2g7mutf8.soup.utils.task.TaskUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class DeathListener implements Listener {

    private final SoupPvP plugin = SoupPvP.getInstance();

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Player killer = player.getKiller();

        Profile profile = ProfileManager.getProfile(player);
        Profile killerProfile = ProfileManager.getProfile(killer);

        List<Item> removableStack = new ArrayList<>();

        for (ItemStack stack : event.getDrops()) {
            if (stack.getType() == XMaterial.MUSHROOM_STEW.parseMaterial()) {
                removableStack.add(player.getWorld().dropItemNaturally(player.getLocation(), stack));
            }

            TaskUtil.runTaskLater(new BukkitRunnable() {
                @Override
                public void run() {
                    removableStack.forEach(Entity::remove);
                }
            }, 60);
        }
        event.getDrops().clear();

        if (profile.getCurrentKillstreak() >= 5) {
            Bukkit.broadcastMessage(plugin.getMessageDB().killstreakMessage(player, killer, profile.getCurrentKillstreak(), true));
        }
        int credits = SoupUtils.getRandomNumber(25) + 1;

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
        if (killerProfile.getCurrentKillstreak() / 5 == 0) {
            Bukkit.broadcastMessage(plugin.getMessageDB().killstreakMessage(player, null, killerProfile.getCurrentKillstreak(), false));
        }

        killer.sendMessage(ColorText.translate("&7You killed &c" + player.getName() + " &7for &$" + credits + "&7!"));

        TaskUtil.runTaskLater(new BukkitRunnable() {
            @Override
            public void run() {
                PlayerUtils.resetPlayer(player, true, true);
            }
        }, 3);
    }
}
