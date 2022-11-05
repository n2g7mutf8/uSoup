package me.n2g7mutf8.soup;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import mc.obliviate.inventory.InventoryAPI;
import me.n2g7mutf8.soup.ability.AbilityManager;
import me.n2g7mutf8.soup.database.MongoBase;
import me.n2g7mutf8.soup.kit.KitLoader;
import me.n2g7mutf8.soup.listener.ListenerHandler;
import me.n2g7mutf8.soup.listener.list.*;
import me.n2g7mutf8.soup.sidebar.SideBar;
import me.n2g7mutf8.soup.sidebar.scoreboard.AridiManager;
import me.n2g7mutf8.soup.sidebar.scoreboard.listeners.AridiListener;
import me.n2g7mutf8.soup.user.Profile;
import me.n2g7mutf8.soup.user.ProfileManager;
import me.n2g7mutf8.soup.utils.KitPvPCache;
import me.n2g7mutf8.soup.utils.MessageDB;
import me.n2g7mutf8.soup.utils.chat.ColorText;
import me.n2g7mutf8.soup.utils.configuration.Config;
import me.n2g7mutf8.soup.utils.cooldown.Cooldown;
import me.n2g7mutf8.soup.utils.item.ItemMaker;
import me.n2g7mutf8.soup.utils.task.TaskUtil;
import me.n2g7mutf8.soup.utils.time.TimeUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
public class SoupPvP extends JavaPlugin {

    public static final Gson GSON = new Gson();
    public static final Type LIST_STRING_TYPE = new TypeToken<List<String>>() {
    }.getType();

    @Getter
    private static SoupPvP instance;
    private final InventoryAPI inventoryAPI = new InventoryAPI(this);
    private Config settings, abilities, kits, messages;
    private MongoBase PvPDB;
    private ServerData serverData;
    private MessageDB messageDB;
    @Setter
    private AridiManager aridiManager;

    public static Cooldown getCooldownByPlayerID(UUID uuid) {
        for (Map.Entry<String, Cooldown> cooldown : Cooldown.getCooldownMap().entrySet()) {
            if (cooldown.getValue().getLongMap().containsKey(uuid)) {
                return cooldown.getValue();
            }
        }
        return null;
    }

    public static Cooldown getCooldown(String name) {
        return Cooldown.getCooldownMap().get(name);
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        settings = new Config(this, "config.yml");
        messages = new Config(this, "messages.yml");
        abilities = new Config(this, "abilities.yml");
        kits = new Config(this, "kits.yml");
        load();

        inventoryAPI.init();
    }

    @Override
    public void onDisable() {
        for (Profile profile : ProfileManager.getProfileMap().values()) {
            ProfileManager.saveProfile(profile, false);
        }

        serverData.saveServer();
    }

    private void load() {
        PvPDB = new MongoBase(settings);
        serverData = new ServerData(settings);
        messageDB = new MessageDB(messages);
        setAridiManager(new AridiManager(new SideBar()));
        new KitPvPCache();
        new Cooldown("Spawn", TimeUtils.parse(settings.getInt("General.Spawn-Timer") + "s"), "&9Spawn", "&7You have been teleported to &bSpawn&7.\n&7(If you wish reset your kit, please use &3/resetkit&7)");
        new Cooldown("Combat", TimeUtils.parse(settings.getInt("General.Combat-Timer") + "s"), "&cCombat", "&7You are out of &acombat&7.");

        ListenerHandler.registerListeners(
                new CooldownListener(),
                new CoreListener(),
                new DeathListener(),
                new SignListener(),
                new WorldListener(),
                new GameListener()
        );

        Bukkit.getPluginManager().registerEvents(new ItemMaker.ItemMakerListener(), this);
        TaskUtil.runTaskLater(new BukkitRunnable() {
            @Override
            public void run() {
                if (aridiManager != null) {
                    Bukkit.getPluginManager().registerEvents(new AridiListener(), getInstance());
                }
            }
        }, 2 * 20L);

        TaskUtil.runTaskLater(new BukkitRunnable() {
            @Override
            public void run() {
                if (aridiManager != null) {
                    TaskUtil.runTaskTimer(aridiManager::sendScoreboard, 0L, 2L);
                }
            }
        }, 2 * 20L);

        TaskUtil.runTaskTimer(new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.broadcastMessage(ColorText.translate("&7Saving &3data &7this might cause &clag&7!"));

                try {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        ProfileManager.saveProfile(ProfileManager.getProfile(player), false);
                    }
                    Bukkit.broadcastMessage(ColorText.translate("&7Successfully saved &3data&7!"));
                } catch (Exception e) {
                    Bukkit.broadcastMessage(ColorText.translate("&cFailed to save &4data&c!"));
                }
            }
        }, 0, settings.getInt("General.Save-Timer") * 20L);

        new AbilityManager();
        new KitLoader().loadKits();

        for (World world : Bukkit.getWorlds()) {
            world.setStorm(false);
            world.setThundering(false);
            world.setTime(0);

            world.getEntities().forEach(entity -> {
                if (!(entity instanceof Player)) {
                    entity.remove();
                }
            });
        }
    }
}
