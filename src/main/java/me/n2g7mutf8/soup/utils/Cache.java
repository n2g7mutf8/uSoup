package me.n2g7mutf8.soup.utils;

import me.n2g7mutf8.soup.SoupPvP;
import me.n2g7mutf8.soup.commands.*;
import me.n2g7mutf8.soup.utils.command.KitPvPCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;

public class Cache {

    private final JavaPlugin javaPlugin;
    private CommandMap commandMap;

    public Cache() {
        javaPlugin = SoupPvP.getInstance();

        registerCommand(new KitCommand());
        registerCommand(new StatsCommand());
        registerCommand(new BountyCommand());
        registerCommand(new SpawnCommand());
        registerCommand(new SetCuboCommand(), SoupUtils.STAFF_PERMISSION + ".setcuboid");
        registerCommand(new SetSpawnCommand(), SoupUtils.STAFF_PERMISSION + ".setspawn");
        registerCommand(new DebugCommand());
        registerCommand(new PayCommand());
    }

    private void registerCommand(KitPvPCommand axisCommand) {
        registerCommand(axisCommand, null);
    }

    private void registerCommand(KitPvPCommand axisCommand, String permission) {
        PluginCommand command = getCommand(axisCommand.getName(), javaPlugin);

        command.setPermissionMessage(SoupPvP.getInstance().getMessageDB().getMessages().get("insufficientPermission"));

        if (permission != null) {
            command.setPermission(permission.toLowerCase());
        }

        if (axisCommand.getDescription() != null) {
            command.setDescription(axisCommand.getDescription());
        }

        command.setAliases(Arrays.asList(axisCommand.getAliases()));

        command.setExecutor(axisCommand);
        command.setTabCompleter(axisCommand);

        if (!getCommandMap().register(axisCommand.getName(), command)) {
            command.unregister(getCommandMap());
            getCommandMap().register(axisCommand.getName(), command);
        }
    }

    private CommandMap getCommandMap() {
        if (commandMap != null) {
            return commandMap;
        }

        try {
            Field field = SimplePluginManager.class.getDeclaredField("commandMap");
            field.setAccessible(true);

            commandMap = (CommandMap) field.get(Bukkit.getPluginManager());
        } catch (Exception ignored) {
        }

        return commandMap;
    }

    private PluginCommand getCommand(String name, Plugin owner) {
        PluginCommand command = null;

        try {
            Constructor<PluginCommand> constructor = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
            constructor.setAccessible(true);

            command = constructor.newInstance(name, owner);
        } catch (Exception ignored) {
        }

        return command;
    }
}