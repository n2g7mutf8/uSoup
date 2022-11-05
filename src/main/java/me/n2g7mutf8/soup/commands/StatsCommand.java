package me.n2g7mutf8.soup.commands;

import me.n2g7mutf8.soup.user.Profile;
import me.n2g7mutf8.soup.user.ProfileManager;
import me.n2g7mutf8.soup.utils.KitPvPUtils;
import me.n2g7mutf8.soup.utils.chat.ColorText;
import me.n2g7mutf8.soup.utils.command.KitPvPCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;

public class StatsCommand extends KitPvPCommand {

    public StatsCommand() {
        super("stats", "Show player their stats", "stat", "info");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(KitPvPUtils.ONLY_PLAYERS);
            return false;
        }

        Player player = (Player) sender;
        Profile profile = ProfileManager.getProfile(player);

        player.sendMessage(ColorText.translate("&7&m-----------------------------------"));
        player.sendMessage(ColorText.translate("&3&lYour Stats"));
        player.sendMessage(ColorText.translate("&3* &7Kills: &b" + profile.getKills()));
        player.sendMessage(ColorText.translate("&3* &7KillStreak: &b" + profile.getCurrentKillstreak()));
        player.sendMessage(ColorText.translate("&3* &7HighestStreak: &b" + profile.getHighestKillstreak()));
        player.sendMessage(ColorText.translate("&3* &7Deaths: &b" + profile.getDeaths()));
        player.sendMessage(ColorText.translate("&3* &7K/D: &b" + (profile.getKills() == 0 && profile.getDeaths() == 0 ? "N/A" : new DecimalFormat("#.##").format(profile.getKills() / profile.getDeaths()))));
        player.sendMessage(ColorText.translate("&3* &7Balance: &b$" + profile.getCredits()));
        player.sendMessage(ColorText.translate("&3* &7Bounty: &b" + profile.getBounty()));
        player.sendMessage(ColorText.translate("&3* &7Scoreboard: &b" + (profile.isScoreboardEnabled() ? "&aON" : "&cOFF")));
        player.sendMessage(ColorText.translate("&7&m-----------------------------------"));
        return true;
    }
}
