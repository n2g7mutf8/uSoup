package me.n2g7mutf8.soup.user;

import lombok.Getter;
import org.bukkit.OfflinePlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ProfileManager {

    @Getter
    private static final Map<UUID, Profile> profileMap = new HashMap<>();

    public static Profile getProfile(OfflinePlayer player) {
        return getProfile(player.getUniqueId());
    }

    public static Profile getProfile(UUID uuid) {
        Profile profile = profileMap.get(uuid);
        if (profile == null) {
            profile = new Profile(uuid);
        }
        return profile;
    }

    public static void loadProfile(Profile profile) {
        if (!profileMap.containsKey(profile.getUniqueId())) {
            profileMap.put(profile.getUniqueId(), profile);
            System.out.println("Profile loaded '" + profile.getUniqueId() + "'.");
        }
    }

    public static void saveProfile(Profile profile, boolean remove) {
        profile.saveProfile();
        if (remove) {
            profileMap.remove(profile.getUniqueId());
        }
    }
}
