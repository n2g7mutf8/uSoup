package me.n2g7mutf8.soup.commands;

import me.n2g7mutf8.soup.interfaces.ShopGUI;
import me.n2g7mutf8.soup.utils.KitPvPUtils;
import me.n2g7mutf8.soup.utils.command.KitPvPCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KitShopCommand extends KitPvPCommand {

    public KitShopCommand() {
        super("shop", "Open shop gui for player", "kitshop", "kitsshop");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(KitPvPUtils.ONLY_PLAYERS);
            return false;
        }

        new ShopGUI((Player) sender).open();
        return true;
    }
}
