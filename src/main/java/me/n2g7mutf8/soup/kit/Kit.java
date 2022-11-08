package me.n2g7mutf8.soup.kit;

import lombok.Getter;
import me.n2g7mutf8.soup.SoupPvP;
import me.n2g7mutf8.soup.ability.Ability;
import me.n2g7mutf8.soup.ability.AbilityManager;
import me.n2g7mutf8.soup.user.Profile;
import me.n2g7mutf8.soup.user.ProfileManager;
import me.n2g7mutf8.soup.utils.chat.ColorText;
import me.n2g7mutf8.soup.utils.configuration.Config;
import me.n2g7mutf8.soup.utils.item.ItemMaker;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
public class Kit {

    private final Config config = SoupPvP.getInstance().getKits();

    private final String name;
    private final String permission;
    private final int price;
    private final ItemStack originalLogo;
    private final ItemStack[] items;
    private final ItemStack[] armor;
    private final Collection<PotionEffect> effects;
    List<String> abilitiesWritten;
    ArrayList<Ability> interactionAbilities;
    ArrayList<Ability> attackAbilities;
    ArrayList<Ability> attackReceiveAbilities;
    ArrayList<Ability> damageAbilities;
    ArrayList<Ability> projectileAbilities;
    ArrayList<Ability> entityInteractionAbilities;
    ArrayList<Ability> otherAbilities;
    private ItemStack logo;
    private ItemStack shopLogo;

    public Kit(String name, int price, List<String> abilitiesWritten, ItemStack originalLogo, ItemStack[] items, ItemStack[] armor, Collection<PotionEffect> effects) {
        this.name = name;
        this.price = price;
        this.abilitiesWritten = abilitiesWritten;
        this.originalLogo = originalLogo;
        this.items = items;
        this.armor = armor;
        this.effects = effects;
        this.permission = "usoup.kit." + name.toLowerCase().replace(" ", "_");

        loadAbilities();
        generateLogo();
    }

    public void equipKit(Player player) {
        Profile profile = ProfileManager.getProfile(player);

        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.getInventory().setContents(this.items);
        player.getInventory().setArmorContents(this.armor);
        player.addPotionEffects(this.effects);
        giveSoups(player);

        player.updateInventory();
        profile.setCurrentKit(this);
        profile.setLastKit(this);
        player.sendMessage(ColorText.translate("&7You have chosen the &a" + this.getName() + "&7 kit."));
    }

    public void loadAbilities() {
        this.interactionAbilities = new ArrayList<>();
        this.attackAbilities = new ArrayList<>();
        this.attackReceiveAbilities = new ArrayList<>();
        this.damageAbilities = new ArrayList<>();
        this.projectileAbilities = new ArrayList<>();
        this.entityInteractionAbilities = new ArrayList<>();
        this.otherAbilities = new ArrayList<>();
        if (!this.abilitiesWritten.isEmpty()) {

            for (String var2 : this.abilitiesWritten) {
                if (AbilityManager.getInstance().abilities.containsKey(var2)) {
                    Ability var3 = AbilityManager.getInstance().abilities.get(var2);
                    if (var3.getMaterial() != null) {
                        this.interactionAbilities.add(var3);
                    }

                    if (var3.isDamageActivated()) {
                        this.damageAbilities.add(var3);
                    }

                    if (var3.isAttackActivated()) {
                        this.attackAbilities.add(var3);
                    }

                    if (var3.isAttackReceiveActivated()) {
                        this.attackReceiveAbilities.add(var3);
                    }

                    if (var3.getProjectile() != null) {
                        this.projectileAbilities.add(var3);
                    }

                    if (var3.isEntityInteractionActivated()) {
                        this.entityInteractionAbilities.add(var3);
                    }

                    if (!this.interactionAbilities.contains(var3) && !this.attackAbilities.contains(var3) && !this.attackReceiveAbilities.contains(var3) && !this.damageAbilities.contains(var3) && !this.projectileAbilities.contains(var3) && !this.entityInteractionAbilities.contains(var3)) {
                        this.otherAbilities.add(var3);
                    }
                }
            }
        }

    }

    private void giveSoups(Player player) {
        final ItemStack soup = new ItemStack(Material.MUSHROOM_SOUP);
        for (ItemStack inv : player.getInventory().getContents()) {
            if (inv == null) {
                player.getInventory().addItem(soup);
            }
        }
    }

    private void generateLogo() {
        logo = new ItemMaker(originalLogo).addLore(" ", "&a&nClick to select!").create();
        shopLogo = new ItemMaker(originalLogo).addLore(" ", "&7Cost: &a$" + price).create();
    }
}
