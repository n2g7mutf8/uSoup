package me.n2g7mutf8.soup.utils.player;

import me.n2g7mutf8.soup.ServerData;
import me.n2g7mutf8.soup.SoupPvP;
import me.n2g7mutf8.soup.enums.SpawnItem;
import me.n2g7mutf8.soup.kit.Kit;
import me.n2g7mutf8.soup.user.Profile;
import me.n2g7mutf8.soup.user.ProfileManager;
import me.n2g7mutf8.soup.utils.chat.ColorText;
import me.n2g7mutf8.soup.utils.item.ItemMaker;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerUtils {

    private static final ServerData serverData = SoupPvP.getInstance().getServerData();

    public static void resetPlayer(Player player) {
        Profile profile = ProfileManager.getProfile(player);

        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }

        player.setMaxHealth(20);
        player.setHealth(player.getMaxHealth());
        player.setFoodLevel(20);
        player.setFireTicks(0);
        player.setSaturation(20);
        player.setExp(0);
        player.setLevel(0);
        player.setGameMode(GameMode.SURVIVAL);
        player.setAllowFlight(false);
        player.setFlying(false);

        Bukkit.getOnlinePlayers().forEach(online -> online.showPlayer(player));

        PlayerInventory inventory = player.getInventory();

        inventory.setHeldItemSlot(0);

        inventory.clear();
        inventory.setArmorContents(null);

        if (profile.getLastKit() != null) {
            ItemStack stack = new ItemMaker(SpawnItem.PREVIOUS_KIT.getItem()).setInteractRight(data -> {
                Kit kit = profile.getLastKit();
                if (kit == null) {
                    inventory.setItem(1, new ItemStack(Material.AIR));
                    player.sendMessage(ColorText.translate("&cKit not found."));
                } else {
                    if (!profile.getUnlockedKits().contains(kit) && !player.hasPermission(kit.getPermission())) {
                        player.sendMessage(ColorText.translate("&7You do not have access to &c" + kit.getName() + "&7!"));
                    } else {
                        kit.equipKit(player);
                    }
                }
                player.updateInventory();
            }).create();
            ItemMeta meta = stack.getItemMeta();
            meta.setDisplayName(meta.getDisplayName().replace("%NAME%", profile.getLastKit().getName().toUpperCase()));
            stack.setItemMeta(meta);
            inventory.setItem(1, stack);
        }

        profile.setCurrentKit(null);

        player.teleport(serverData.getSpawnLocation());

        inventory.setItem(0, SpawnItem.KIT_SELECTOR.getItem());
        inventory.setItem(4, SpawnItem.KIT_SHOP.getItem());
        inventory.setItem(8, SpawnItem.STATS.getItem());

        player.updateInventory();
    }

    public static void managePlayerMovement(Player player, boolean allowed) {
        player.setWalkSpeed((allowed ? 0.2F : 0.0F));
        player.setFlySpeed((allowed ? 0.1F : 0.0F));
        player.setFoodLevel((allowed ? 20 : 0));
        player.setSprinting(allowed);
        if (allowed) {
            player.removePotionEffect(PotionEffectType.JUMP);
        } else {
            player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 200));
        }
    }
}