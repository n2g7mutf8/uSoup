package me.n2g7mutf8.soup.listener.list;

import me.n2g7mutf8.soup.ServerData;
import me.n2g7mutf8.soup.SoupPvP;
import me.n2g7mutf8.soup.enums.PlayerState;
import me.n2g7mutf8.soup.kit.Kit;
import me.n2g7mutf8.soup.kit.KitHandler;
import me.n2g7mutf8.soup.user.Profile;
import me.n2g7mutf8.soup.user.ProfileManager;
import me.n2g7mutf8.soup.utils.chat.ColorText;
import me.n2g7mutf8.soup.utils.cooldown.Cooldown;
import me.n2g7mutf8.soup.utils.item.ItemMaker;
import me.n2g7mutf8.soup.utils.item.XMaterial;
import me.n2g7mutf8.soup.utils.task.TaskUtil;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.potion.Potion;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class GameListener implements Listener {

    private final SoupPvP plugin = SoupPvP.getInstance();
    private final ServerData serverData = plugin.getServerData();

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        Profile profile = ProfileManager.getProfile(player);
        if (profile.getPlayerState() == PlayerState.FIGHTING) {
            if (event.getItemDrop().getItemStack().getType() == Material.BOWL) {
                event.getItemDrop().setPickupDelay(10000);
                TaskUtil.runTaskLater(new BukkitRunnable() {
                    @Override
                    public void run() {
                        event.getItemDrop().remove();
                    }
                }, 2 * 20L);
            } else {

                if (player.getGameMode() == GameMode.CREATIVE && player.isOp()) {
                    return;
                }

                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Location from = event.getFrom();
        Location to = event.getTo();
        if (from.getBlockX() == to.getBlockX() && from.getBlockY() == to.getBlockY() && from.getBlockZ() == to.getBlockZ()) {
            return;
        }
        Player player = event.getPlayer();
        Profile profile = ProfileManager.getProfile(player);

        SoupPvP.getCooldown("Spawn").remove(player);
        if (profile.getPlayerState() == PlayerState.SPAWN) {
            if (event.getTo().getBlock().getType().name().endsWith("PLATE")) {
                player.setVelocity(player.getLocation().getDirection().multiply(4.5));
                player.setVelocity(new Vector(player.getVelocity().getX(), 1.0, player.getVelocity().getZ()));
                player.playSound(player.getLocation(), Sound.PISTON_EXTEND, 10.0f, 1.0f);
            }
        }
        if (to.getBlock().getRelative(BlockFace.DOWN).getType() == Material.SPONGE) {
            try {
                double v = 0;
                for (int i = 0; i < 10; i++) {
                    if (to.getBlock().getRelative(BlockFace.DOWN).getLocation().clone().add(0, -i, 0).getBlock().getType() == Material.SPONGE) {
                        v += 1;
                    }
                }
                player.setVelocity(new Vector(0, v, 0));
            } catch (Exception e) {
                Bukkit.getServer().getLogger().severe(ColorText.translate("&8(&3Anti-Spam&8) &cBug incoming anti-spam is &aON&c!"));
            }
        }
        if (serverData.getSpawnCuboID() != null) {
            if (profile.getPlayerState() == PlayerState.SPAWN) {
                if (!serverData.getSpawnCuboID().contains(event.getTo())) {
                    profile.setPlayerState(PlayerState.FIGHTING);
                    if (profile.getCurrentKit() == null) {
                        Kit kit = (profile.getLastKit() != null ? profile.getLastKit() : KitHandler.getByName(plugin.getSettings().getString("General.Kit-To-Equip")));
                        assert kit != null;
                        if (kit.getPermission() != null && !player.hasPermission(kit.getPermission())) {
                            kit = KitHandler.getByName(plugin.getSettings().getString("General.Kit-To-Equip"));
                        }
                        if (kit != null) {
                            kit.equipKit(player);
                        }
                    }
                    player.sendMessage(ColorText.translate("&7You are no longer protected."));
                }
            } else if (profile.getPlayerState() == PlayerState.FIGHTING) {
                if (serverData.getSpawnCuboID().contains(event.getTo())) {
                    Cooldown combat = SoupPvP.getCooldown("Combat");
                    if (combat.isOnCooldown(player)) {
                        if (player.isOp()) {
                            combat.remove(player);
                        } else {
                            event.setTo(event.getFrom());
                        }
                        return;
                    }
                    profile.setPlayerState(PlayerState.SPAWN);
                    player.sendMessage(ColorText.translate("&aYou are now protected."));
                }
            }
        }
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        onPlayerMove(event);
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Player attacker = getFinalAttacker(event);
        Entity entity;
        Cooldown combat = SoupPvP.getCooldown("Combat");

        if ((attacker != null) && ((entity = event.getEntity()) instanceof Player)) {
            Player damaged = (Player) entity;

            Profile damagedProfile = ProfileManager.getProfile(damaged);
            Profile attackerProfile = ProfileManager.getProfile(attacker);

            if (attackerProfile.getPlayerState() != PlayerState.FIGHTING || damagedProfile.getPlayerState() != PlayerState.FIGHTING) {
                return;
            }

            if (!combat.isOnCooldown(damaged)) {
                event.getEntity().sendMessage(ColorText.translate("&7You are now in &ccombat&7."));
            }

            if (!combat.isOnCooldown(attacker)) {
                event.getDamager().sendMessage(ColorText.translate("&7You are now in &ccombat&7."));
            }

            combat.setCooldown(attacker, true);
            combat.setCooldown(damaged, true);
        }
    }

    private Player getFinalAttacker(EntityDamageEvent ede) {
        Player attacker = null;
        if (ede instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent event = (EntityDamageByEntityEvent) ede;
            Entity damager = event.getDamager();
            if (event.getDamager() instanceof Player) {
                attacker = (Player) damager;
            } else if (event.getDamager() instanceof Projectile) {
                Projectile projectile = (Projectile) damager;
                ProjectileSource shooter = projectile.getShooter();
                if (shooter instanceof Player) {
                    attacker = (Player) shooter;
                }
            }
            if (attacker != null && event.getEntity().equals(attacker)) {
                attacker = null;
            }
        }
        return attacker;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Profile profile = ProfileManager.getProfile(player);
        if (profile.getPlayerState() == PlayerState.SPAWN && event.getAction().name().startsWith("RIGHT") && event.hasItem()) {
            if (player.getItemInHand().getType() == Material.ENDER_PEARL) {
                event.setCancelled(true);
                player.sendMessage(ColorText.translate("&cYou cannot throw enderpearls while protected."));
            } else if (player.getItemInHand().getType() == Material.POTION) {
                Potion potion = Potion.fromItemStack(player.getItemInHand());
                if (potion != null && potion.isSplash()) {
                    event.setCancelled(true);
                    if (profile.getCurrentKit() != null) {
                        player.sendMessage(ColorText.translate("&cYou cannot interact with potions while protected."));
                    }
                }
            } else {
                return;
            }
            player.updateInventory();
            return;
        }
        if (event.getAction() == Action.PHYSICAL) {
            if (player.getGameMode() == GameMode.CREATIVE && player.isOp()) {
                return;
            }
            if (profile.getPlayerState() != PlayerState.FIGHTING) {
                event.setCancelled(true);
            }
        } else if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block block = event.getClickedBlock();
            if (block.getState() instanceof Sign) {
                Sign sign = (Sign) block.getState();
                if (sign.getLine(1).contains("Free") && sign.getLine(2).contains("Refill")) {
                    Inventory inventory = Bukkit.createInventory(null, 4 * 9, ColorText.translate("&3Soup Refill"));
                    for (int i = 0; i < 36; i++) {
                        inventory.addItem(new ItemMaker(XMaterial.MUSHROOM_STEW.parseMaterial()).create());
                    }
                    player.openInventory(inventory);
                }
            } else {
                if (player.getGameMode() == GameMode.CREATIVE && player.isOp()) {
                    return;
                }
                if (profile.getPlayerState() == PlayerState.SPAWN) {
                    event.setCancelled(true);
                    player.updateInventory();
                }
            }
        }
    }
}
