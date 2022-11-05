package me.n2g7mutf8.soup.listener.list;

import me.n2g7mutf8.soup.enums.PlayerState;
import me.n2g7mutf8.soup.user.Profile;
import me.n2g7mutf8.soup.user.ProfileManager;
import me.n2g7mutf8.soup.utils.player.PlayerUtils;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.*;

public class CoreListener implements Listener {

    @EventHandler
    public void onAsyncPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
        Profile profile = new Profile(event.getUniqueId());

        ProfileManager.loadProfile(profile);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);

        Player player = event.getPlayer();

        player.setFoodLevel(20);

        player.setGameMode(GameMode.SURVIVAL);

        PlayerUtils.resetPlayer(player);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);

        Player player = event.getPlayer();
        Profile profile = ProfileManager.getProfile(player);

        ProfileManager.saveProfile(profile, true);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode() == GameMode.CREATIVE && player.isOp()) {
            return;
        }
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode() == GameMode.CREATIVE && player.isOp()) {
            return;
        }
        event.setCancelled(true);
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        Profile profile = ProfileManager.getProfile(event.getPlayer());

        if (profile.getPlayerState() == PlayerState.SPAWN) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent event) {
        Profile profile = ProfileManager.getProfile(event.getPlayer());

        if (profile.getPlayerState() == PlayerState.SPAWN) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            Profile profile = ProfileManager.getProfile(player);

            if (profile.getPlayerState() == PlayerState.SPAWN) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            Profile profile = ProfileManager.getProfile((Player) event.getEntity());
            if (profile.getPlayerState() == PlayerState.SPAWN) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (event.getSpawnReason() != CreatureSpawnEvent.SpawnReason.CUSTOM) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.hasItem() && event.getAction().name().startsWith("RIGHT")) {

            if (player.getGameMode() == GameMode.CREATIVE || player.getHealth() >= player.getMaxHealth()) {
                return;
            }

            if (player.getItemInHand().getType() == Material.MUSHROOM_SOUP) {
                int v = (int) (player.getHealth() + 7);
                player.setHealth((v >= player.getMaxHealth() ? player.getMaxHealth() : v));
                player.getItemInHand().setType(Material.BOWL);
            }
        }
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent event) {
        if (event.getReason().startsWith("Flying is not enabled")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void noUproot(PlayerInteractEvent event) {
        if (event.getAction() == Action.PHYSICAL && event.getClickedBlock().getType() == Material.SOIL)
            event.setCancelled(true);
    }

}
