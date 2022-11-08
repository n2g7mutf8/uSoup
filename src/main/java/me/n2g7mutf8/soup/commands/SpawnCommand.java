package me.n2g7mutf8.soup.commands;

import me.n2g7mutf8.soup.SoupPvP;
import me.n2g7mutf8.soup.utils.SoupUtils;
import me.n2g7mutf8.soup.utils.command.KitPvPCommand;
import me.n2g7mutf8.soup.utils.cooldown.Cooldown;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand extends KitPvPCommand {

    public SpawnCommand() {
        super("spawn", "Reset your kit!", "resetkit");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(SoupUtils.ONLY_PLAYERS);
            return false;
        }

        Cooldown cooldown = SoupPvP.getCooldown("Spawn");
        cooldown.setCooldown(((Player) sender).getPlayer(), true);
        return true;
    }
}
