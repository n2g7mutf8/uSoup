package me.n2g7mutf8.soup.ability.list;

import me.n2g7mutf8.soup.SoupPvP;
import me.n2g7mutf8.soup.ability.Ability;
import me.n2g7mutf8.soup.enums.PlayerState;
import me.n2g7mutf8.soup.user.Profile;
import me.n2g7mutf8.soup.utils.configuration.Config;
import me.n2g7mutf8.soup.utils.cooldown.Cooldown;
import me.n2g7mutf8.soup.utils.item.XMaterial;
import me.n2g7mutf8.soup.utils.time.TimeUtils;
import org.bukkit.Material;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.List;

public class ThorAbility extends Ability {

    String cooldown, displayName, cooldownExpire, useDeny;
    int damage;
    int maxRange;
    Material activationMaterial;

    public ThorAbility() {
        this.activationMaterial = XMaterial.WOODEN_AXE.parseMaterial();
    }

    public String getName() {
        return "Thor";
    }

    public void load(Config var1) {
        this.cooldown = var1.getString("Abilities.Thor.Cooldown");
        this.displayName = var1.getString("Abilities.Thor.Display-Name");
        this.cooldownExpire = var1.getString("Abilities.Thor.Cooldown-Expire");
        this.useDeny = var1.getString("Abilities.Thor.Use-Deny");
        this.damage = var1.getInt("Abilities.Thor.Lightning-Damage") * 2;
        this.maxRange = var1.getInt("Abilities.Thor.Strike-Radius");
        this.activationMaterial = (XMaterial.matchXMaterial(var1.getString("Abilities." + this.getName() + ".Activation-Material")).get()).parseMaterial();

        new Cooldown(getName(), TimeUtils.parse(cooldown), displayName, cooldownExpire);
    }

    public Material getMaterial() {
        return this.activationMaterial;
    }

    public EntityType getProjectile() {
        return null;
    }

    public boolean isAttackActivated() {
        return false;
    }

    public boolean isAttackReceiveActivated() {
        return false;
    }

    public boolean isDamageActivated() {
        return false;
    }

    public boolean isEntityInteractionActivated() {
        return false;
    }

    public boolean execute(Player var1, Profile var2, Event var3) {
        Cooldown cooldown = SoupPvP.getCooldown("Thor");

        if (cooldown.isOnCooldown(var1)) {
            var1.sendMessage(useDeny);
            return false;
        } else {
            cooldown.setCooldown(var1, true);
            List<Entity> var5 = var1.getNearbyEntities(this.maxRange, this.maxRange, this.maxRange);

            for (Entity entity : var5) {
                if (entity instanceof Player && (var2.getCurrentKit() != null || var2.getPlayerState() != PlayerState.SPAWN)) {
                    var1.getWorld().strikeLightningEffect(entity.getLocation());
                    ((Damageable) entity).damage(this.damage, var1);
                }
            }

            return true;
        }
    }
}
