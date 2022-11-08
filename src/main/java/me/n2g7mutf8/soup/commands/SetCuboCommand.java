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

public class SetCuboCommand extends KitPvPCommand {

    public SetCuboCommand() {
        super("setcubo", "Set the first and second position of the spawn cuboid.", "setcuboid");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(SoupUtils.ONLY_PLAYERS);
            return false;
        }
        Player player = (Player) sender;
        if (args.length < 1) {
            player.sendMessage(ColorText.translate("&cUsage: /" + label + " <first|second>"));
        } else {
            ServerData serverData = SoupPvP.getInstance().getServerData();
            if (args[0].equalsIgnoreCase("first")) {
                serverData.setFirstCI(LocationUtils.getString(player.getLocation()));
                player.sendMessage(ColorText.translate("&7Successfully setting the &alocation&7."));
            } else if (args[0].equalsIgnoreCase("second")) {
                serverData.setSecondCI(LocationUtils.getString(player.getLocation()));
                player.sendMessage(ColorText.translate("&7Successfully setting the &alocation&7."));
            } else {
                player.sendMessage(ColorText.translate("&cUsage: /" + label + " <first|second>"));
                return false;
            }
            serverData.saveServer();
        }
        return true;
    }
}
