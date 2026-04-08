package net.xdevelopment.xlibrary.command;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Anyachkaa
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public final class SimpleCommandManager implements CommandManager {

    JavaPlugin plugin;

    Set<Command> commands = new HashSet<>();
    Map<String, Command> name2CommandMap = new HashMap<>();

    @Override
    @Nullable
    public Command findCommand(@NotNull String commandName) {
        return name2CommandMap.get(commandName.toLowerCase());
    }

    @Override
    public boolean register(@NotNull Command command) {
        if (!commands.add(command)) return false;

        name2CommandMap.put(command.getName().toLowerCase(), command);
        for (var alias : command.getAliases()) {
            name2CommandMap.put(alias.toLowerCase(), command);
        }

        Bukkit.getCommandMap().register(plugin.getName().toLowerCase(), new BukkitCommandAdapter(command));
        return true;
    }

    @Override
    public boolean unregister(@NotNull Command command) {
        if (!commands.remove(command)) return false;

        name2CommandMap.remove(command.getName().toLowerCase(), command);
        for (var alias : command.getAliases()) {
            name2CommandMap.remove(alias.toLowerCase(), command);
        }

        return true;
    }

    @Override
    public boolean unregister(@NotNull String commandName) {
        var command = name2CommandMap.get(commandName.toLowerCase());
        return command != null && unregister(command);
    }

    @Override
    @NotNull
    @Unmodifiable
    public List<@NotNull Command> commands() {
        return List.copyOf(commands);
    }
}
