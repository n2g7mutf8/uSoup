package me.n2g7mutf8.soup.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.n2g7mutf8.soup.utils.item.ItemMaker;
import me.n2g7mutf8.soup.utils.item.XMaterial;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
@Getter
public enum SpawnItem {
    KIT_SELECTOR(new ItemMaker(XMaterial.BOOK.parseMaterial()).setDisplayname("&3&lKit &7(Right Click)").addLore("&4Bound").setInteractRight(player -> player.performCommand("kits")).create()),
    KIT_SHOP(new ItemMaker(XMaterial.NETHER_STAR.parseMaterial()).setDisplayname("&b&lKit Shop &7(Right Click)").addLore("&4Bound").setInteractRight(player -> player.performCommand("shop")).create()),
    PREVIOUS_KIT(new ItemMaker(XMaterial.CLOCK.parseMaterial()).setDisplayname("&b%NAME% &7(Right Click)").addLore("&4Bound").create()),
    STATS(new ItemMaker(XMaterial.PLAYER_HEAD.parseMaterial()).setDisplayname("&aYour Stats &7(Right Click)").addLore("&4Bound").setInteractRight(player -> player.performCommand("stats")).create());

    private ItemStack item;
}
