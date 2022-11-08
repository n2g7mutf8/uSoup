package me.n2g7mutf8.soup.kit;

import lombok.Getter;
import me.n2g7mutf8.soup.user.Profile;
import me.n2g7mutf8.soup.user.ProfileManager;
import me.n2g7mutf8.soup.utils.SoupUtils;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KitHandler {

    @Getter
    private static final List<Kit> kitList = new ArrayList<>();

    public static void registerKits(Kit... kits) {
        Collections.addAll(kitList, kits);
    }

    public static Kit getByName(String name) {
        for (Kit kit : kitList) {
            if (kit.getName().toLowerCase().equalsIgnoreCase(name.toLowerCase().replace(" ", ""))) {
                return kit;
            }
        }
        return null;
    }

    public static Kit getRandomKit(Player player) {
        List<Kit> kits = new ArrayList<>();
        Profile profile = ProfileManager.getProfile(player);
        Kit kit = getKitList().get(SoupUtils.getRandomNumber(getKitList().size()));
        if (kit.getPermission() != null && !player.hasPermission(kit.getPermission()) && !profile.getUnlockedKits().contains(kit)) {
            kits.add(kit);
        }
        if (kits.isEmpty()) {
            return null;
        }
        return kits.get(SoupUtils.getRandomNumber(kits.size()));
    }
}
