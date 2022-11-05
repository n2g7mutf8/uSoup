package me.n2g7mutf8.soup.ability;

import me.n2g7mutf8.soup.SoupPvP;
import me.n2g7mutf8.soup.ability.list.*;
import me.n2g7mutf8.soup.kit.Kit;
import me.n2g7mutf8.soup.kit.KitHandler;
import me.n2g7mutf8.soup.user.ProfileManager;
import me.n2g7mutf8.soup.utils.configuration.Config;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class AbilityManager {

    private static AbilityManager instance;
    public HashMap<String, Ability> abilities;

    public AbilityManager() {
        instance = this;
        this.abilities = new HashMap<>();
        registerAbility(new ThorAbility());
        registerAbility(new KangarooAbility());
        Bukkit.getPluginManager().registerEvents(new AbilityListener(SoupPvP.getInstance()), SoupPvP.getInstance());
        Bukkit.getPluginManager().registerEvents(new SpecialAbilityListener(SoupPvP.getInstance()), SoupPvP.getInstance());
    }

    public static AbilityManager getInstance() {
        return instance;
    }

    public void registerAbility(Ability var1) {
        this.abilities.put(var1.getName().toLowerCase(), var1);
    }

    public void loadAbilityConfig(Ability var1) {
        var1.load(SoupPvP.getInstance().getAbilities());
    }

    public void loadAbilitiesConfig() {
        Config var1 = SoupPvP.getInstance().getAbilities();
        for (Ability var3 : this.abilities.values()) {
            var3.load(var1);
        }

    }

    public boolean hasInteractionAbility(Player var1, String var2) {
        Kit var3 = ProfileManager.getProfile(var1).getCurrentKit();
        if (var3 != null) {

            for (Ability var5 : var3.getInteractionAbilities()) {
                if (var5.getName().equals(var2)) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean hasSpecialAbility(Player var1, String var2) {
        Kit var3 = ProfileManager.getProfile(var1).getCurrentKit();
        if (var3 != null) {

            for (Ability var5 : var3.getOtherAbilities()) {
                if (var5.getName().equals(var2)) {
                    return true;
                }
            }
        }

        return false;
    }

    public Ability getAbility(String var1) {
        return this.abilities.get(var1.toLowerCase());
    }

    public void updateKitAbilities() {
        for (Kit var2 : KitHandler.getKitList()) {
            var2.loadAbilities();
        }
    }
}
