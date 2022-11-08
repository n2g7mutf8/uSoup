package me.n2g7mutf8.soup.utils.item;

import me.n2g7mutf8.soup.utils.chat.ColorText;
import org.bukkit.Color;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class StringItem {

    public static ItemStack getItemStack(String var0, boolean isMultiple, boolean hasItemMeta) {
        String[] var3 = var0.split(" : ");
        String materialValue = var3[0].toUpperCase();
        ItemMaker var5;
        String materialString;

        Optional<XMaterial> material = XMaterial.matchXMaterial(materialValue);
        if (!material.isPresent() && materialValue.contains(":")) {
            material = XMaterial.matchXMaterial(materialValue.split(":")[0]);
        }

        var5 = material.map(xMaterial -> new ItemMaker(xMaterial.parseMaterial())).orElseGet(() -> new ItemMaker(XMaterial.MUSHROOM_STEW.parseMaterial()));
        if (var3[0].contains(":")) {
            materialString = materialValue.split(":")[0];
            if ((materialString.contains("POTION") || materialString.equals("TIPPED_ARROW")) && materialValue.split(":").length == 4) {
                var5.setDisplayname("Not Supported!");
            } else {
                var5.setDurability(Integer.parseInt(materialValue.split(":")[1]));
            }
        }

        if (isMultiple) {
            var5.setAmount(Integer.parseInt(var3[1]));
        }

        if (hasItemMeta) {
            for (int var9 = isMultiple ? 2 : 1; var9 < var3.length; ++var9) {
                materialString = var3[var9].split(":")[0].toLowerCase();
                if (materialString.equals("enchant")) {
                    var5.setEnchant(Enchantment.getByName(var3[var9].split(":")[1]), Integer.parseInt(var3[var9].split(":")[2]));
                } else if (materialString.equals("name")) {
                    var5.setDisplayname(var3[var9].split(":")[1]);
                } else if (materialString.equals("lore")) {
                    var5.addLore(new String[]{ColorText.translate(var3[var9].split(":")[1])});
                } else if (materialString.equals("dye")) {
                    var5.setColor(getColor(var3[var9].split(":")[1]));
                } else if (materialString.equals("tag") && var3[var9].split(":")[1].equalsIgnoreCase("unbreakable")) {
                    var5.setUnbreakable(true);
                }
            }
        }

        return var5.create();
    }

    public static Color getColor(String var0) {
        if (var0.equalsIgnoreCase("AQUA")) {
            return Color.AQUA;
        } else if (var0.equalsIgnoreCase("BLUE")) {
            return Color.BLUE;
        } else if (var0.equalsIgnoreCase("FUCHSIA")) {
            return Color.FUCHSIA;
        } else if (var0.equalsIgnoreCase("GRAY")) {
            return Color.GRAY;
        } else if (var0.equalsIgnoreCase("GREEN")) {
            return Color.GREEN;
        } else if (var0.equalsIgnoreCase("LIME")) {
            return Color.LIME;
        } else if (var0.equalsIgnoreCase("MAROON")) {
            return Color.MAROON;
        } else if (var0.equalsIgnoreCase("NAVY")) {
            return Color.NAVY;
        } else if (var0.equalsIgnoreCase("OLIVE")) {
            return Color.OLIVE;
        } else if (var0.equalsIgnoreCase("ORANGE")) {
            return Color.ORANGE;
        } else if (var0.equalsIgnoreCase("PURPLE")) {
            return Color.PURPLE;
        } else if (var0.equalsIgnoreCase("RED")) {
            return Color.RED;
        } else if (var0.equalsIgnoreCase("SILVER")) {
            return Color.SILVER;
        } else if (var0.equalsIgnoreCase("TEAL")) {
            return Color.TEAL;
        } else if (var0.equalsIgnoreCase("WHITE")) {
            return Color.WHITE;
        } else {
            return var0.equalsIgnoreCase("YELLOW") ? Color.YELLOW : Color.BLACK;
        }
    }

}
