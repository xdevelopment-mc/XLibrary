package net.xdevelopment.xlibrary.command;

import net.xdevelopment.xlibrary.command.annotation.OnlyPlayer;
import net.xdevelopment.xlibrary.core.utility.CollectionUtility;

import net.kyori.adventure.text.Component;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
final class BukkitCommandAdapter extends org.bukkit.command.Command {

    Command command;
    boolean onlyPlayer;

    BukkitCommandAdapter(@NotNull Command command) {
        super(
                command.getName(),
                "",
                "/" + command.getName(),
                command.getAliases()
        );
        this.command = command;
        this.onlyPlayer = command.getClass().isAnnotationPresent(OnlyPlayer.class);

        if (command.getPermission() != null) {
            setPermission(command.getPermission());
        }
    }

    @Override
    public boolean execute(
            @NotNull CommandSender sender,
            @NotNull String commandLabel,
            @NotNull String @NotNull [] args
    ) {
        if (args.length > 0) {
            ArgumentCommand current = command.findArgumentCommand(args[0]);
            int depth = 1;

            while (current != null && depth < args.length) {
                ArgumentCommand nested = current.findArgumentCommand(args[depth]);
                if (nested == null) break;
                current = nested;
                depth++;
            }

            if (current != null) {
                if (current.getClass().isAnnotationPresent(OnlyPlayer.class) && !(sender instanceof Player)) {
                    sender.sendMessage(Component.text("This command is only for players."));
                    return true;
                }

                var permission = current.getPermission();
                if (permission != null && !sender.hasPermission(permission)) {
                    sender.sendMessage(Component.text("You don't have permission to use this command."));
                    return true;
                }

                current.execute(new CommandContext(sender, args[depth - 1], depth, args));
                return true;
            }
        }

        if (onlyPlayer && !(sender instanceof Player)) {
            sender.sendMessage(Component.text("This command is only for players."));
            return true;
        }

        command.execute(new CommandContext(sender, commandLabel, args));
        return true;
    }

    @Override
    @NotNull
    public List<String> tabComplete(
            @NotNull CommandSender sender,
            @NotNull String alias,
            @NotNull String @NotNull [] args
    ) {
        Map<String, ArgumentCommand> currentArgs = command.getArgumentCommands();
        ArgumentCommand resolved = null;
        int depth = 0;

        while (depth < args.length - 1) {
            ArgumentCommand next = currentArgs.get(args[depth].toLowerCase());
            if (next == null) break;
            resolved = next;
            currentArgs = next.getArgumentCommands();
            depth++;
        }

        String input = args[args.length - 1];
        var suggestions = new ArrayList<String>();

        suggestions.addAll(CollectionUtility.getSequentialMatches(
                new ArrayList<>(currentArgs.keySet()), input
        ));

        List<String> contextSuggestions;
        if (resolved != null) {
            contextSuggestions = resolved.tabComplete(new CommandContext(sender, args[depth - 1], depth, args));
        } else {
            contextSuggestions = command.tabComplete(new CommandContext(sender, alias, args));
        }

        suggestions.addAll(CollectionUtility.getSequentialMatches(contextSuggestions, input));
        return suggestions;
    }
}
