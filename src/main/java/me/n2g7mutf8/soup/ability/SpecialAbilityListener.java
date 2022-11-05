package me.n2g7mutf8.soup.ability;

import me.n2g7mutf8.soup.SoupPvP;
import me.n2g7mutf8.soup.enums.PlayerState;
import me.n2g7mutf8.soup.event.PlayerUseAbilityEvent;
import me.n2g7mutf8.soup.user.Profile;
import me.n2g7mutf8.soup.user.ProfileManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerFishEvent;

public class SpecialAbilityListener implements Listener {
    private final SoupPvP plugin;

    public SpecialAbilityListener(SoupPvP var1) {
        this.plugin = var1;
    }

    @EventHandler
    public void AbilityFishEvent(PlayerFishEvent var1) {
        Player var2 = var1.getPlayer();
        if (var1.getState().equals(PlayerFishEvent.State.CAUGHT_ENTITY) && me.wazup.kitbattle.abilities.AbilityManager.getInstance().hasSpecialAbility(var2, "Fisherman")) {
            Profile var3 = ProfileManager.getProfile(var2);
            if (!var1.getCaught().getType().equals(EntityType.PLAYER) || ProfileManager.getProfile(var2).getCurrentKit() != null
                    && ProfileManager.getProfile((Player) var1.getCaught()).getPlayerState() != PlayerState.SPAWN) {
                boolean var4 = AbilityManager.getInstance().getAbility("Fisherman").execute(var2, var3, var1);
                if (var4) {
                    PlayerUseAbilityEvent var5 = new PlayerUseAbilityEvent(var2, "Fisherman");
                    Bukkit.getPluginManager().callEvent(var5);
                }

            } else {
                var2.sendMessage("&7Your ability don't affect that &cplayer&7!");
            }
        }
    }

    @EventHandler
    public void AbilityShootArrowEvent(ProjectileLaunchEvent var1) {
        if (var1.getEntity().getType() == EntityType.ARROW) {
            Arrow var2 = (Arrow) var1.getEntity();
            if (var2.getShooter() instanceof Player) {
                Player var3 = (Player) var2.getShooter();
                if (me.wazup.kitbattle.abilities.AbilityManager.getInstance().hasSpecialAbility(var3, "Climber")) {
                    var1.setCancelled(true);
                    Profile var4 = ProfileManager.getProfile(var3);
                    if (var4.getPlayerState() == PlayerState.SPAWN) {
                        var2.sendMessage("&7Your ability don't affect that &cplayer&7!");
                        var1.setCancelled(true);
                        return;
                    }

                    boolean var5 = AbilityManager.getInstance().getAbility("Climber").execute(var3, var4, var1);
                    if (var5) {
                        PlayerUseAbilityEvent var6 = new PlayerUseAbilityEvent(var3, "Climber");
                        Bukkit.getPluginManager().callEvent(var6);
                    }
                }

            }
        }
    }
}
