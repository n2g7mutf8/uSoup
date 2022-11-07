package me.n2g7mutf8.soup.kit;

import me.n2g7mutf8.soup.SoupPvP;
import me.n2g7mutf8.soup.utils.configuration.Config;
import me.n2g7mutf8.soup.utils.item.ItemMaker;
import me.n2g7mutf8.soup.utils.item.StringItem;
import me.n2g7mutf8.soup.utils.item.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class KitLoader {

    private static final SoupPvP plugin = SoupPvP.getInstance();
    List<String> armorParts = Arrays.asList("Boots", "Leggings", "Chestplate", "Helmet");

    public void loadKits() {
        Config config = plugin.getKits();
        if (config.getConfigurationSection("Kits") != null) {
            Iterator<String> var4 = config.getConfigurationSection("Kits").getKeys(false).iterator();

            String name;
            while (var4.hasNext()) {
                name = var4.next();
                ItemStack[] armor = new ItemStack[4];
                ItemStack[] items = new ItemStack[36];

                ItemMaker icon;
                String splitNode;
                String desc;
                Iterator<String> parseString;
                try {
                    String iconString = config.getString("Kits." + name + ".Item");
                    Optional<XMaterial> material = XMaterial.matchXMaterial(iconString);
                    if (!material.isPresent() && iconString.contains(":")) {
                        material = XMaterial.matchXMaterial(iconString.split(":")[0]);
                    }

                    icon = new ItemMaker(Objects.requireNonNull((material.get()).parseItem()));
                    if (iconString.contains(":")) {
                        splitNode = iconString.split(":")[0];
                        if ((splitNode.equals("POTION") || splitNode.equals("SPLASH_POTION") || splitNode.equals("LINGERING_POTION")) && iconString.split(":").length == 4) {
                            icon.setDurability(0);
                        } else {
                            icon.setDurability(Integer.parseInt(iconString.split(":")[1]));
                        }
                    }

                    icon.setDisplayname("&a" + name);
                    parseString = config.getStringList("Kits." + name + ".Description").iterator();

                    while (parseString.hasNext()) {
                        desc = parseString.next();
                        icon.addLore(desc);
                    }
                } catch (Exception e) {
                    Bukkit.getConsoleSender().sendMessage("[uSoup] Failed to create the logo for the kit: " + name + ", due to that, the whole kit wont load!, make sure you have the correct format!");
                    e.printStackTrace();
                    continue;
                }

                int counter = 0;

                for (String armorPart : this.armorParts) {
                    splitNode = armorPart;

                    try {
                        armor[counter] = config.getString("Kits." + name + ".Armor." + splitNode).isEmpty() ? new ItemStack(Material.AIR) : StringItem.getItemStack(config.getString("Kits." + name + ".Armor." + splitNode), true, true);
                        ++counter;
                    } catch (Exception var18) {
                        Bukkit.getConsoleSender().sendMessage("[uSoup] Failed to create a " + splitNode + " for the kit: " + name + ", due to that, the whole kit wont load!, make sure you have the correct format!");
                        var18.printStackTrace();
                    }
                }

                int itemCounter = 0;
                parseString = config.getStringList("Kits." + name + ".Items").iterator();

                while (parseString.hasNext()) {
                    desc = parseString.next();

                    try {
                        if (itemCounter == items.length) {
                            break;
                        }
                        if (desc.isEmpty()) {
                            assert XMaterial.AIR.parseMaterial() != null;
                            items[itemCounter++] = new ItemStack(XMaterial.AIR.parseMaterial());
                        } else {
                            items[itemCounter++] = StringItem.getItemStack(desc, false, true);
                        }
                    } catch (Exception var19) {
                        Bukkit.getConsoleSender().sendMessage("[uSoup] Failed to create this item: " + desc + " for the kit: " + name + ", due to that, the whole kit wont load!, make sure you have the correct format!");
                        var19.printStackTrace();
                    }
                }

                int price = config.getInt("Kits." + name + ".Price");
                ArrayList<PotionEffect> potionEffects = new ArrayList<>();

                for (String effect : config.getStringList("Kits." + name + ".Potion-Effects")) {
                    String[] effects = effect.split(" : ");
                    potionEffects.add(new PotionEffect(PotionEffectType.getByName(effects[0]), Integer.parseInt(effects[1]) * 20, Integer.parseInt(effects[2]) - 1));
                }

                ArrayList<String> writtenAbility = new ArrayList<>();

                for (String ability : config.getStringList("Kits." + name + ".Abilities")) {
                    writtenAbility.add(ability.toLowerCase());
                }

                try {
                    KitHandler.registerKits(new Kit(name, price, writtenAbility, icon.create(), items, armor, potionEffects));
                    Bukkit.getLogger().info("[uSoup] Successfully register " + name + "kit!");
                } catch (Exception exception) {
                    Bukkit.getLogger().severe("[uSoup] CANNOT REGISTER KIT!");
                }
            }
        }

        config.save();
    }
}
