package me.n2g7mutf8.soup.ability;

import me.n2g7mutf8.soup.user.Profile;
import me.n2g7mutf8.soup.utils.configuration.Config;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public abstract class Ability {

    public Ability() {
    }

    public abstract String getName();

    public abstract Material getMaterial();

    public abstract EntityType getProjectile();

    public abstract void load(Config var1);

    public abstract boolean isAttackActivated();

    public abstract boolean isAttackReceiveActivated();

    public abstract boolean isDamageActivated();

    public abstract boolean isEntityInteractionActivated();

    public abstract boolean execute(Player var1, Profile var2, Event var3);
}
