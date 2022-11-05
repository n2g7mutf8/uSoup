package me.n2g7mutf8.soup.ability.list;

import me.n2g7mutf8.soup.ability.Ability;
import me.n2g7mutf8.soup.user.Profile;
import me.n2g7mutf8.soup.utils.configuration.Config;
import me.n2g7mutf8.soup.utils.item.XMaterial;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class KangarooAbility extends Ability {

    String cooldown, displayName, cooldownExpire, useDeny;
    Material activationMaterial;

    public KangarooAbility() {
        activationMaterial = XMaterial.FIREWORK_ROCKET.parseMaterial();
    }

    @Override
    public String getName() {
        return "KangarooAbility";
    }

    @Override
    public Material getMaterial() {
        return activationMaterial;
    }

    @Override
    public EntityType getProjectile() {
        return null;
    }

    @Override
    public void load(Config var1) {

    }

    @Override
    public boolean isAttackActivated() {
        return false;
    }

    @Override
    public boolean isAttackReceiveActivated() {
        return false;
    }

    @Override
    public boolean isDamageActivated() {
        return false;
    }

    @Override
    public boolean isEntityInteractionActivated() {
        return false;
    }

    @Override
    public boolean execute(Player var1, Profile var2, Event var3) {
        return false;
    }
}
