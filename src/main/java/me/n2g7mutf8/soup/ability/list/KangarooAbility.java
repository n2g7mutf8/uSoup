package me.n2g7mutf8.soup.ability.list;

import me.n2g7mutf8.soup.SoupPvP;
import me.n2g7mutf8.soup.ability.Ability;
import me.n2g7mutf8.soup.user.Profile;
import me.n2g7mutf8.soup.utils.configuration.Config;
import me.n2g7mutf8.soup.utils.cooldown.Cooldown;
import me.n2g7mutf8.soup.utils.item.XMaterial;
import me.n2g7mutf8.soup.utils.time.TimeUtils;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.util.Vector;

public class KangarooAbility extends Ability {

    private final SoupPvP plugin = SoupPvP.getInstance();

    String cooldown, displayName;
    Material activationMaterial;

    public String getName() {
        return "Kangaroo";
    }

    public Material getMaterial() {
        return XMaterial.FIREWORK_ROCKET.parseMaterial();
    }

    public EntityType getProjectile() {
        return null;
    }

    public void load(Config config) {
        cooldown = config.getString("Abilities." + this.displayName + ".Cooldown");
        displayName = config.getString("Abilities." + this.displayName + ".Display-Name");
        activationMaterial = (XMaterial.matchXMaterial(config.getString("Abilities." + this.getName() + ".Activation-Material")).get()).parseMaterial();

        new Cooldown(getName(), TimeUtils.parse(cooldown), displayName, null);
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

    public boolean execute(Player player, Profile var2, Event event) {
        Cooldown cooldown = SoupPvP.getCooldown(getName());

        if (player.isOnGround()) {
            if (cooldown.isOnCooldown(player)) {
                player.sendMessage(plugin.getMessageDB().remainCooldownMessage(player, cooldown));
                return false;
            } else {
                cooldown.setCooldown(player);
                Vector var4 = player.getEyeLocation().getDirection();
                if (player.isSneaking()) {
                    var4.setY(0.2D);
                    var4.multiply(4);
                } else {
                    var4.setY(1.2D);
                }

                player.setVelocity(var4);
                return true;
            }
        } else {
            return false;
        }
    }
}
