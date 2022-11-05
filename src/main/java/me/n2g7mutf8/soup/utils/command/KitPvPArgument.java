package me.n2g7mutf8.soup.utils.command;

import lombok.Getter;
import lombok.Setter;
import me.n2g7mutf8.soup.SoupPvP;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class KitPvPArgument {

    public SoupPvP plugin = SoupPvP.getInstance();

    @Getter
    @Setter
    public String name, description, permission;
    public String[] aliases;
    @Getter
    @Setter
    public boolean onlyplayers;

    public KitPvPArgument(String name) {
        this(name, null);
    }

    public KitPvPArgument(String name, String description) {
        this(name, description, null);
    }

    public KitPvPArgument(String name, String description, String permission) {
        this(name, description, permission, ArrayUtils.EMPTY_STRING_ARRAY);
    }

    public KitPvPArgument(String name, String description, String permission, String... aliases) {
        this(name, description, permission, aliases, false);
    }

    public KitPvPArgument(String name, String description, String permission, String[] aliases, boolean onlyplayers) {
        this.name = name;
        this.description = description;
        this.permission = permission;
        this.aliases = Arrays.copyOf(aliases, aliases.length);
        this.onlyplayers = onlyplayers;
    }

    public abstract String getUsage(String label);

    public abstract void onExecute(CommandSender sender, String label, String[] args);

    public List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        return Collections.emptyList();
    }
}