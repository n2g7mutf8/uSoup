package me.n2g7mutf8.soup.commands;

import me.n2g7mutf8.soup.interfaces.KitGUI;
import me.n2g7mutf8.soup.utils.KitPvPUtils;
import me.n2g7mutf8.soup.utils.command.KitPvPCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KitCommand extends KitPvPCommand {

    public KitCommand() {
        super("kit", "Open kit gui for player", "kits", "selector");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(KitPvPUtils.ONLY_PLAYERS);
            return false;
        }

        new KitGUI((Player) sender).open();
        return true;
    }
}
