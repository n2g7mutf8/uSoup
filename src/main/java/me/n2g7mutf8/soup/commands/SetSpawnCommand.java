package me.n2g7mutf8.soup.commands;

import me.n2g7mutf8.soup.ServerData;
import me.n2g7mutf8.soup.SoupPvP;
import me.n2g7mutf8.soup.utils.SoupUtils;
import me.n2g7mutf8.soup.utils.chat.ColorText;
import me.n2g7mutf8.soup.utils.command.KitPvPCommand;
import me.n2g7mutf8.soup.utils.location.LocationUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawnCommand extends KitPvPCommand {

    public SetSpawnCommand() {
        super("setspawn", "Set the spawn location of players and bosses");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(SoupUtils.ONLY_PLAYERS);
            return false;
        }
        Player player = (Player) sender;
        ServerData serverData = SoupPvP.getInstance().getServerData();
        if (args.length < 1) {
            player.sendMessage(ColorText.translate("&cUsage: /" + label + " <main|boss>"));
        } else {
            String stringLocation = LocationUtils.getString(player.getLocation());
            if (args[0].equalsIgnoreCase("main")) {
                serverData.setSpawn(stringLocation);
            } else if (args[0].equalsIgnoreCase("boss")) {
                serverData.setSpawnBoss(stringLocation);
            } else {
                player.sendMessage(ColorText.translate("&cUsage: /" + label + " <main|boss>"));
                return false;
            }
            player.sendMessage(ColorText.translate("&7Successfully setting the &alocation&7."));
        }
        return true;
    }
}
