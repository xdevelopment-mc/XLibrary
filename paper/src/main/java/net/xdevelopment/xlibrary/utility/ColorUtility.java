package net.xdevelopment.xlibrary.utility;

import java.util.List;
import java.util.Map;

import org.intellij.lang.annotations.Subst;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

@UtilityClass
public class ColorUtility {

    private final MiniMessage MM = MiniMessage.miniMessage();

    @NotNull
    @Contract(pure = true)
    public static Component colorize(@NotNull String message) {
        return MM.deserialize(message).decoration(TextDecoration.ITALIC, false);
    }

    @NotNull
    @Contract(pure = true)
    public static Component colorize(@NotNull List<String> list) {
        return Component.join(
                JoinConfiguration.separator(Component.newline()),
                list.stream()
                        .map(MM::deserialize)
                        .toList()
        );
    }

    @NotNull
    @Contract(pure = true)
    public static Component colorize(@NotNull List<String> list,
                                     @NotNull Map<String, Object> placeholders) {
        final TagResolver[] resolvers = placeholders.entrySet().stream()
                .map(entry -> toPlaceholder(entry.getKey(), entry.getValue()))
                .toArray(TagResolver[]::new);

        return Component.join(
                JoinConfiguration.separator(Component.newline()),
                list.stream()
                        .map(line -> MM.deserialize(line, resolvers))
                        .toList()
        );
    }

    @NotNull
    @Contract(pure = true)
    public static Component colorize(@NotNull String message,
                                     @NotNull Map<String, Object> placeholders) {
        final TagResolver[] resolvers = placeholders.entrySet().stream()
                .map(entry -> toPlaceholder(entry.getKey(), entry.getValue()))
                .toArray(TagResolver[]::new);

        return MM.deserialize(message, resolvers);
    }

    @NotNull
    @Contract(pure = true)
    private TagResolver toPlaceholder(@Subst("") @NotNull String key, @NotNull Object value) {
        if (value instanceof Component component) {
            return Placeholder.component(key, component);
        }
        if (value instanceof String str) {
            return Placeholder.parsed(key, str);
        }
        return Placeholder.unparsed(key, String.valueOf(value));
    }
}
