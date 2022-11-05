package me.n2g7mutf8.soup.utils.item;

import me.n2g7mutf8.soup.utils.Callback;
import me.n2g7mutf8.soup.utils.chat.ColorText;
import me.n2g7mutf8.soup.utils.task.TaskUtil;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagString;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class ItemMaker {

    private final ItemStack itemStack;
    private final ItemMeta itemMeta;
    private final Map<Action, Callback<Player>> interactables = new HashMap<>();
    private final Map<Enchantment, Integer> enchantments = new HashMap<>();
    private Callback<Player> clicked;

    public ItemMaker(Material material) {
        this.itemStack = new ItemStack(material);
        this.itemMeta = itemStack.getItemMeta();
    }

    public ItemMaker(ItemStack itemStack) {
        this.itemStack = itemStack;
        this.itemMeta = itemStack.getItemMeta();
    }

    public ItemMaker setAmount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    public ItemMaker setDisplayname(String name) {
        itemMeta.setDisplayName(ColorText.translate(name));
        return this;
    }

    public ItemMaker setDurability(int durability) {
        itemStack.setDurability((short) durability);
        return this;
    }

    public ItemMaker addLore(String lore) {
        List<String> list = itemMeta.getLore();
        if (list == null) {
            list = new ArrayList<>();
        }
        list.add(ColorText.translate(lore));
        itemMeta.setLore(list);
        return this;
    }

    public ItemMaker addLore(List<String> lore) {
        itemMeta.setLore(ColorText.translate(lore));
        return this;
    }

    public ItemMaker addLore(String... lore) {
        List<String> strings = itemMeta.getLore();
        if (strings == null) {
            strings = new ArrayList<>();
        }
        for (String string : lore) {
            strings.add(ColorText.translate(string));
        }
        itemMeta.setLore(strings);
        return this;
    }

    public ItemMaker setEnchant(Enchantment enchantment, int level) {
        enchantments.put(enchantment, level);
        return this;
    }

    public ItemMaker setPlayerInteract(Callback<Player> callback) {
        for (Action action : Action.values()) {
            action(action, callback);
        }

        return this;
    }

    public ItemMaker setInteractRight(Callback<Player> callback) {
        for (Action action : Action.values()) {
            if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
                action(action, callback);
            }
        }

        return this;
    }

    public ItemMaker setInteractLeft(Callback<Player> callback) {
        for (Action action : Action.values()) {
            if (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK) {
                action(action, callback);
            }
        }

        return this;
    }

    private void action(Action action, Callback<Player> callback) {
        interactables.put(action, callback);
    }

    public ItemMaker action(Predicate<Action> pred, Callback<Player> callback) {
        for (Action action : Action.values()) {
            if (pred.test(action)) {
                action(action, callback);
            }
        }
        return this;
    }

    public ItemMaker setUnbreakable(boolean unbreakable) {
        itemMeta.spigot().setUnbreakable(unbreakable);
        return this;
    }

    public ItemMaker setColor(Color color) {
        if (itemStack.getType() != null && itemStack.getType().name().contains("LEATHER")) {
            LeatherArmorMeta armorMeta = (LeatherArmorMeta) itemMeta;
            armorMeta.setColor(color);
        }
        return this;
    }

    public ItemStack create() {
        if (itemMeta != null) {
            itemStack.setItemMeta(itemMeta);
        }

        enchantments.forEach((enchantment, integer) -> itemStack.addUnsafeEnchantment(enchantment, integer));

        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound nbt = nmsStack.getTag();

        if (nbt == null) {
            nbt = new NBTTagCompound();
        }

        for (Map.Entry<Action, Callback<Player>> entry : interactables.entrySet()) {
            nbt.set("interact_" + entry.getKey().name(), new ExecutableNBTTag(entry.getValue()));
        }

        if (clicked != null) {
            nbt.set("clicked", new ExecutableNBTTag(clicked));
        }

        nmsStack.setTag(nbt);
        return CraftItemStack.asBukkitCopy(nmsStack);
    }

    public static class ItemMakerListener implements Listener {

        @EventHandler
        public void onPlayerDropItem(PlayerDropItemEvent event) {
            ItemStack item = event.getItemDrop().getItemStack();

            if (item != null) {
                net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);

                if (nmsStack.hasTag()) {

                    for (Action action : Action.values()) {
                        String key = "interact_" + action.name();
                        if (nmsStack.getTag().hasKey(key)) {
                            NBTTagString e = (NBTTagString) nmsStack.getTag().get(key);

                            if (e instanceof ExecutableNBTTag) {
                                event.setCancelled(true);
                                return;
                            }
                        }
                    }
                }
            }
        }

        @EventHandler
        public void onPlayerInventoryClick(InventoryClickEvent event) {
            ItemStack item = event.getCurrentItem();

            if (item != null) {
                net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);

                if (nmsStack != null && nmsStack.hasTag()) {

                    String key = "clicked";
                    if (nmsStack.getTag().hasKey(key)) {
                        NBTTagString e = (NBTTagString) nmsStack.getTag().get(key);

                        if (e instanceof ExecutableNBTTag) {
                            event.setCancelled(true);
                            TaskUtil.runTask(() -> ((ExecutableNBTTag) e).execute((Player) event.getWhoClicked()));
                        }
                    }
                }
            }
        }

        @EventHandler
        public void onPlayerInteract(PlayerInteractEvent event) {
            Player player = event.getPlayer();
            ItemStack item = event.getItem();
            Action action = event.getAction();

            if (item != null) {
                net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);

                if (nmsStack.hasTag()) {

                    String key = "interact_" + action.name();
                    if (nmsStack.getTag().hasKey(key)) {
                        NBTTagString e = (NBTTagString) nmsStack.getTag().get(key);

                        if (e instanceof ExecutableNBTTag) {

                            ((ExecutableNBTTag) e).execute(player);
                            event.setCancelled(true);
                        }
                    }
                }
            }
        }
    }
}