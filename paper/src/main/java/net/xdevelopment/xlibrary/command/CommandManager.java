package net.xdevelopment.xlibrary.command;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;

/**
 * @author Anyachkaa
 */
public interface CommandManager {

    @Nullable
    Command findCommand(@NotNull String commandName);

    boolean register(@NotNull Command command);

    boolean unregister(@NotNull Command command);

    boolean unregister(@NotNull String commandName);

    @Unmodifiable
    @NotNull
    List<@NotNull Command> getCommands();
}
