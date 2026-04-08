package net.xdevelopment.xlibrary.command;

import net.xdevelopment.xlibrary.command.annotation.CommandName;
import net.xdevelopment.xlibrary.command.annotation.CommandAliases;
import net.xdevelopment.xlibrary.command.annotation.CommandPermission;

import lombok.Getter;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.xdevelopment.xlibrary.core.Identifiable;

import java.util.UUID;

/**
 * @author Anyachkaa
 */
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public abstract class Command implements CommandExecutor, Identifiable {

    UUID uniqueId = generateUniqueId();
    String name;

    @Unmodifiable
    List<String> aliases;

    @Nullable
    String permission;

    Map<String, ArgumentCommand> argumentCommands = new HashMap<>();

    protected Command() {
        var clazz = getClass();

        var nameAnnotation = clazz.getAnnotation(CommandName.class);
        if (nameAnnotation == null) {
            throw new IllegalStateException(clazz.getSimpleName() + " must be annotated with @CommandName");
        }
        this.name = nameAnnotation.value();

        var aliasesAnnotation = clazz.getAnnotation(CommandAliases.class);
        this.aliases = aliasesAnnotation != null ? List.of(aliasesAnnotation.value()) : List.of();

        var permissionAnnotation = clazz.getAnnotation(CommandPermission.class);
        this.permission = permissionAnnotation != null ? permissionAnnotation.value() : null;
    }

    @NotNull
    public Command argument(@NotNull ArgumentCommand argumentCommand) {
        argumentCommands.put(argumentCommand.getName().toLowerCase(), argumentCommand);
        return this;
    }

    @Override
    public @NotNull UUID uniqueId() {
        return uniqueId;
    }

    @Override
    public @NotNull String name() {
        return name;
    }

    @Nullable
    public ArgumentCommand findArgumentCommand(@NotNull String name) {
        return argumentCommands.get(name.toLowerCase());
    }

    @NotNull
    public List<String> tabComplete(@NotNull CommandContext context) {
        return List.of();
    }
}
