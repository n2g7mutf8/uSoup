package me.n2g7mutf8.soup.interfaces;

import mc.obliviate.inventory.Gui;
import mc.obliviate.inventory.Icon;
import me.n2g7mutf8.soup.kit.Kit;
import me.n2g7mutf8.soup.user.Profile;
import me.n2g7mutf8.soup.user.ProfileManager;
import me.n2g7mutf8.soup.utils.item.ItemMaker;
import me.n2g7mutf8.soup.utils.item.XMaterial;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.jetbrains.annotations.NotNull;

public class ConfirmGUI extends Gui {

    private final Kit kit;

    public ConfirmGUI(@NotNull Player player, Kit kit) {
        super(player, "confirm-gui", "Confirm Your Purchase", 1);
        this.kit = kit;
    }

    @Override
    public void onOpen(InventoryOpenEvent event) {
        Profile profile = ProfileManager.getProfile(player);

        for (int i = 0; i <= 3; i++) {
            Icon icon = new Icon(new ItemMaker(XMaterial.RED_STAINED_GLASS_PANE.parseMaterial()).setDisplayname(" ").create());
            icon.onClick(click -> {
                player.closeInventory();
            });
        }
        Icon icon = new Icon(kit.getShopLogo());
        addItem(icon);
        for (int i = 0; i <= 3; i++) {
            Icon glass = new Icon(new ItemMaker(XMaterial.RED_STAINED_GLASS_PANE.parseMaterial()).setDisplayname(" ").create());
            glass.onClick(click -> {
                profile.setCredits(profile.getCredits() - kit.getPrice());
                profile.getUnlockedKits().add(kit);
                player.closeInventory();
            });
        }
    }
}
