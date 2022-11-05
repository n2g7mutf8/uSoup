package me.n2g7mutf8.soup.sidebar;

import me.n2g7mutf8.soup.SoupPvP;
import me.n2g7mutf8.soup.sidebar.scoreboard.AridiAdapter;
import me.n2g7mutf8.soup.sidebar.scoreboard.AridiStyle;
import me.n2g7mutf8.soup.user.Profile;
import me.n2g7mutf8.soup.user.ProfileManager;
import me.n2g7mutf8.soup.utils.Utils;
import me.n2g7mutf8.soup.utils.chat.ColorText;
import me.n2g7mutf8.soup.utils.cooldown.Cooldown;
import me.n2g7mutf8.soup.utils.player.DurationFormatter;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class SideBar implements AridiAdapter, Listener {

    @Override
    public String getTitle(Player player) {
        return "&3&lSoup &7[&6Beta&7]";
    }

    @Override
    public List<String> getLines(Player player) {
        Profile profile = ProfileManager.getProfile(player);

        if (!profile.isScoreboardEnabled()) {
            return null;
        }

        List<String> scoreboard = new ArrayList<>();

        scoreboard.add(ColorText.CHAT_BAR);
        if (SoupPvP.getCooldownByPlayerID(player.getUniqueId()) != null) {
            for (Cooldown cooldown : Cooldown.getCooldownMap().values()) {
                if (cooldown.isOnCooldown(player) && !cooldown.getName().contains("LFF")) {
                    scoreboard.add(ColorText.translate("&3&l*") + cooldown.getDisplayName() + "&f: " + DurationFormatter.getRemaining(cooldown.getDuration(player), true));
                }
            }
            scoreboard.add(" ");
        }
        scoreboard.add(ColorText.translate("&3&lYour Stats"));
        scoreboard.add(ColorText.translate("&3&l* &7Kills: &b") + profile.getKills());
        if (profile.getCurrentKillstreak() > 1) {
            scoreboard.add(ColorText.translate("&3&l* &7KillStreak: &b") + profile.getCurrentKillstreak());
        }
        scoreboard.add(ColorText.translate("&3&l* &7Deaths: &b") + profile.getDeaths());
        scoreboard.add(ColorText.translate("&3&l* &7K/D: &b") + (profile.getKills() == 0 && profile.getDeaths() == 0 ? "N/A" : new DecimalFormat("#.##").format(profile.getKills() / profile.getDeaths())));
        scoreboard.add(ColorText.translate("&3&l* &7Balance: &b$") + Utils.getFormat(profile.getCredits()));
        scoreboard.add(" ");
        scoreboard.add("&7kitlands.tk");
        scoreboard.add(ColorText.CHAT_BAR);

        return scoreboard;
    }

    @Override
    public AridiStyle getAridiStyle(Player player) {
        return AridiStyle.MODERN;
    }
}