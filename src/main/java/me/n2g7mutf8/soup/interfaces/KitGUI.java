package me.n2g7mutf8.soup.interfaces;

import mc.obliviate.inventory.Gui;
import mc.obliviate.inventory.Icon;
import mc.obliviate.inventory.pagination.PaginationManager;
import me.n2g7mutf8.soup.enums.SmartSlot;
import me.n2g7mutf8.soup.kit.Kit;
import me.n2g7mutf8.soup.kit.KitHandler;
import me.n2g7mutf8.soup.user.Profile;
import me.n2g7mutf8.soup.user.ProfileManager;
import me.n2g7mutf8.soup.utils.item.ItemMaker;
import me.n2g7mutf8.soup.utils.item.XMaterial;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryOpenEvent;

import java.util.Arrays;

public class KitGUI extends Gui {

    private final PaginationManager pagination = new PaginationManager(this);

    public KitGUI(Player player) {
        super(player, "kit-gui", "Kits", 6);
    }

    @Override
    public void onOpen(InventoryOpenEvent event) {
        Profile profile = ProfileManager.getProfile(player);
        pagination.registerPageSlots(SmartSlot.FILL_6.getSlots());

        fillGui(new Icon(new ItemMaker(XMaterial.GRAY_STAINED_GLASS_PANE.parseMaterial()).setDisplayname(" ").create()), Arrays.asList(SmartSlot.FILL_6.getSlots()));

        for (Kit kit : KitHandler.getKitList()) {
            if (player.hasPermission(kit.getPermission()) || profile.getUnlockedKits().contains(kit)) {
                Icon icon = new Icon(kit.getLogo());
                icon.hideFlags();
                icon.onClick(clickEvent -> {
                    kit.equipKit(player);
                });

                pagination.addItem(icon);
            } else {
                Icon icon = new Icon(kit.getShopLogo());
                icon.hideFlags();
                icon.onClick(clickEvent -> {
                    if (profile.getCredits() >= kit.getPrice()) {
                        new ConfirmGUI(player, kit).open();
                    }
                });

                pagination.addItem(icon);
            }
        }
        pagination.update();

        if (pagination.isFirstPage()) {
            Icon icon = new Icon(
                    new ItemMaker(XMaterial.IRON_BARS.parseMaterial())
                            .setDisplayname("&a&nNext Page")
                            .create()
            );
            icon.onClick(click -> {
                pagination.goNextPage();
                pagination.update();
                player.playSound(player.getLocation(), Sound.NOTE_STICKS, 1F, 1F);
            });

            addItem(26, icon);
            addItem(35, icon);
        } else if (pagination.isLastPage()) {
            Icon icon = new Icon(
                    new ItemMaker(XMaterial.IRON_BARS.parseMaterial())
                            .setDisplayname("&c&nPrevious Page")
                            .create()
            );
            icon.onClick(click -> {
                pagination.goPreviousPage();
                pagination.update();
                player.playSound(player.getLocation(), Sound.NOTE_STICKS, 1F, 1F);
            });

            addItem(18, icon);
            addItem(27, icon);
        }
    }
}
