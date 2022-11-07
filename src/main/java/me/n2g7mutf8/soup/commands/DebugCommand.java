package me.n2g7mutf8.soup.commands;

import me.n2g7mutf8.soup.interfaces.KitGUI;
import me.n2g7mutf8.soup.kit.Kit;
import me.n2g7mutf8.soup.kit.KitHandler;
import me.n2g7mutf8.soup.utils.KitPvPUtils;
import me.n2g7mutf8.soup.utils.chat.ColorText;
import me.n2g7mutf8.soup.utils.command.KitPvPCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DebugCommand extends KitPvPCommand {

    public DebugCommand() {
        super("debug");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        int i = 0;
        if (KitHandler.getKitList().isEmpty()) {
            sender.sendMessage(ColorText.translate("Kit list is empty! Fix ur config!"));
            return false;
        }
        for (Kit kit : KitHandler.getKitList()) {
            sender.sendMessage("#" + i + " " + kit.getName());
            i++;
        }

        return true;
    }
}
