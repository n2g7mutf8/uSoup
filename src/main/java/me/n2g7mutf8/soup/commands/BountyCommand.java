package me.n2g7mutf8.soup.commands;

import com.google.common.primitives.Ints;
import me.n2g7mutf8.soup.user.Profile;
import me.n2g7mutf8.soup.user.ProfileManager;
import me.n2g7mutf8.soup.utils.SoupUtils;
import me.n2g7mutf8.soup.utils.chat.ColorText;
import me.n2g7mutf8.soup.utils.command.KitPvPCommand;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BountyCommand extends KitPvPCommand {

    public BountyCommand() {
        super("bounty", "Place a bounty on a player head!", "bountyhunter");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(SoupUtils.ONLY_PLAYERS);
            return false;
        }
        Player player = (Player) sender;
        Profile profile = ProfileManager.getProfile(player);

        if (args.length < 2) {
            sender.sendMessage(ColorText.translate("&cUsage: /" + label + " <playerName> <amount>"));
        } else {
            OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
            Profile targetProfile = ProfileManager.getProfile(target);
            if ((!target.hasPlayedBefore()) && (!target.isOnline())) {
                player.sendMessage(ColorText.translate("&c" + args[1] + " &7has never played before."));
                return false;
            }
            Integer integer = Ints.tryParse(args[1]);
            if (integer == null || integer <= 0) {
                player.sendMessage(ColorText.translate("&cInvalid amount!"));
                return false;
            }

            if (profile.getCredits() < integer) {
                player.sendMessage(ColorText.translate("&7You don't have &c$" + integer + " &7to place that bounty."));
                return false;
            }

            profile.setCredits(profile.getCredits() - integer);
            targetProfile.setBounty(targetProfile.getBounty() + integer);

            Bukkit.broadcastMessage(ColorText.translate("&a" + player.getName() + " &7placed a bounty of &b$" + integer + " &7on &c" + target.getName() + "&7!"));
        }

        return true;
    }
}
