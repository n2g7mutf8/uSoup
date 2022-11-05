package me.n2g7mutf8.soup.ability;

import me.n2g7mutf8.soup.SoupPvP;
import me.n2g7mutf8.soup.enums.PlayerState;
import me.n2g7mutf8.soup.event.PlayerUseAbilityEvent;
import me.n2g7mutf8.soup.kit.Kit;
import me.n2g7mutf8.soup.user.Profile;
import me.n2g7mutf8.soup.user.ProfileManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class AbilityListener implements Listener {
    private final SoupPvP plugin;

    public AbilityListener(SoupPvP var1) {
        this.plugin = var1;
    }

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent var1) {
        Player var2 = var1.getPlayer();
        if (!var1.getAction().equals(Action.PHYSICAL)) {
            Profile var3 = ProfileManager.getProfile(var2);
            if (var3.getPlayerState() == PlayerState.SPAWN) {
                var1.setCancelled(true);
                return;
            }

            Kit var4 = var3.getCurrentKit();
            if (var4 == null || var4.getInteractionAbilities().isEmpty()) {
                return;
            }

            Material var5 = var2.getItemInHand().getType();
            if (var5 == null) {
                return;
            }

            Material var6 = var2.getItemInHand().getType();
            for (Ability ability : var4.getInteractionAbilities()) {
                Ability var8 = ability;
                if (var8.getMaterial().equals(var6)) {
                    if (var3.getPlayerState() == PlayerState.SPAWN) {
                        var2.sendMessage("&7Your ability don't affect that &cplayer&7!");
                        var1.setCancelled(true);
                        return;
                    }

                    var1.setCancelled(true);
                    boolean var9 = var8.execute(var2, var3, var1);
                    if (var8.getProjectile() == null && var9) {
                        PlayerUseAbilityEvent var10 = new PlayerUseAbilityEvent(var2, var8.getName());
                        Bukkit.getPluginManager().callEvent(var10);
                    }
                    break;
                }
            }
        }
    }

    @EventHandler
    public void AbilityPlayerInteractEntityEvent(PlayerInteractEntityEvent var1) {
        if (!var1.isCancelled()) {
            if (var1.getRightClicked().getType().equals(EntityType.PLAYER)) {
                Player var2 = var1.getPlayer();
                Profile var3 = ProfileManager.getProfile(var2);
                Kit var4 = var3.getCurrentKit();
                if (var4 != null && var3.getPlayerState() != PlayerState.SPAWN) {
                    Player var5 = (Player) var1.getRightClicked();
                    Profile var6 = ProfileManager.getProfile(var5);
                    Kit var7 = var6.getCurrentKit();
                    if (var7 != null && var3.getPlayerState() != PlayerState.SPAWN) {
                        if (!var4.getEntityInteractionAbilities().isEmpty()) {
                            for (Ability var9 : var4.getEntityInteractionAbilities()) {
                                boolean var10 = var9.execute(var2, var3, var1);
                                if (var10) {
                                    PlayerUseAbilityEvent var11 = new PlayerUseAbilityEvent(var2, var9.getName());
                                    Bukkit.getPluginManager().callEvent(var11);
                                }
                            }
                        }

                    }
                }
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent var1) {
        if (var1.getEntityType().equals(EntityType.PLAYER)) {
            Player var2 = (Player) var1.getEntity();
            Profile var3 = ProfileManager.getProfile(var2);
            if (var3.getPlayerState() == PlayerState.SPAWN) {
                var1.setCancelled(true);
                return;
            }

            Kit var4 = var3.getCurrentKit();
            if (var4 == null) {
                return;
            }

            if (!var4.getDamageAbilities().isEmpty()) {

                for (Ability var6 : var4.getDamageAbilities()) {
                    boolean var7 = var6.execute(var2, var3, var1);
                    if (var7) {
                        PlayerUseAbilityEvent var8 = new PlayerUseAbilityEvent(var2, var6.getName());
                        Bukkit.getPluginManager().callEvent(var8);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent var1) {
        if (!var1.isCancelled()) {
            if (var1.getEntityType().equals(EntityType.PLAYER)) {
                Player var2 = (Player) var1.getEntity();
                Profile var3 = ProfileManager.getProfile(var2);
                Kit var4 = var3.getCurrentKit();
                if (var4 == null || var3.getPlayerState() == PlayerState.SPAWN) {
                    return;
                }

                if (!var4.getAttackReceiveAbilities().isEmpty()) {

                    for (Ability var6 : var4.getAttackReceiveAbilities()) {
                        boolean var7 = var6.execute(var2, var3, var1);
                        if (var7) {
                            PlayerUseAbilityEvent var8 = new PlayerUseAbilityEvent(var2, var6.getName());
                            Bukkit.getPluginManager().callEvent(var8);
                        }
                    }
                }

                Entity var14 = var1.getDamager();
                if (var14.getType().equals(EntityType.PLAYER)) {
                    Player var15 = (Player) var14;
                    Profile var17 = ProfileManager.getProfile(var15);
                    Kit var19 = var17.getCurrentKit();
                    if (var19 != null && !var19.getAttackAbilities().isEmpty()) {

                        for (Ability var10 : var19.getAttackAbilities()) {
                            boolean var11 = var10.execute(var15, var17, var1);
                            if (var11) {
                                PlayerUseAbilityEvent var12 = new PlayerUseAbilityEvent(var2, var10.getName());
                                Bukkit.getPluginManager().callEvent(var12);
                            }
                        }
                    }
                } else if (var14 instanceof Projectile) {
                    Projectile var16 = (Projectile) var14;
                    if (var16.getShooter() instanceof Player) {
                        Player var18 = (Player) var16.getShooter();
                        Profile var20 = ProfileManager.getProfile(var18);
                        Kit var21 = var20.getCurrentKit();
                        if (var21 != null && !var21.getProjectileAbilities().isEmpty()) {

                            for (Ability var23 : var21.getProjectileAbilities()) {
                                if (var23.getProjectile().equals(var14.getType())) {
                                    boolean var24 = var23.execute(var18, var20, var1);
                                    if (var24) {
                                        PlayerUseAbilityEvent var13 = new PlayerUseAbilityEvent(var2, var23.getName());
                                        Bukkit.getPluginManager().callEvent(var13);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
