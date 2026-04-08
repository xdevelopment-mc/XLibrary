package net.xdevelopment.xlibrary.command;

import net.xdevelopment.xlibrary.utility.ColorUtility;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * @author Anyachkaa
 */
public record CommandContext(CommandSender sender,
                             String name,
                             int argumentOffset,
                             String[] arguments) implements ArgumentProvider {

    public CommandContext(
            @NotNull CommandSender sender,
            @NotNull String name,
            int argumentOffset,
            @NotNull String[] arguments
    ) {
        this.sender = sender;
        this.name = name;
        this.argumentOffset = argumentOffset;
        this.arguments = arguments;
    }

    CommandContext(
            @NotNull CommandSender sender,
            @NotNull String name,
            @NotNull String[] arguments
    ) {
        this(sender, name, 0, arguments);
    }

    public boolean isPlayer() {
        return sender instanceof Player;
    }

    @NotNull
    public Player player() {
        if (!(sender instanceof Player player)) {
            throw new IllegalStateException("Sender is not a player");
        }
        return player;
    }

    @Override
    public String argument(int i) {
        return arguments[i + argumentOffset];
    }

    @Override
    public int argumentCount() {
        return arguments.length - argumentOffset;
    }

    public void sendMessage(@NotNull String message) {
        sender.sendMessage(ColorUtility.colorize(message));
    }

    public void sendMessage(@NotNull String message, @NotNull Map<String, Object> replacements) {
        sender.sendMessage(ColorUtility.colorize(message, replacements));
    }
}
