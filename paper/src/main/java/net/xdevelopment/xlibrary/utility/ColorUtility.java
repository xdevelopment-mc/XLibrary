package net.xdevelopment.xlibrary.utility;

import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.intellij.lang.annotations.Subst;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

@UtilityClass
public class ColorUtility {
    private final MiniMessage MM = MiniMessage.miniMessage();

    public Component colorize(@NotNull String message) {
        return MM.deserialize(message);
    }

    public Component colorize(@NotNull List<String> list) {
        return Component.join(
                JoinConfiguration.separator(Component.newline()),
                list.stream()
                        .map(MM::deserialize)
                        .toList()
        );
    }

    public Component colorize(@NotNull List<String> list,
                              Map<String, Object> placeholders) {
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

    public Component colorize(@NotNull String message,
                              @NotNull Map<String, Object> placeholders) {
        final TagResolver[] resolvers = placeholders.entrySet().stream()
                .map(entry -> toPlaceholder(entry.getKey(), entry.getValue()))
                .toArray(TagResolver[]::new);

        return MM.deserialize(message, resolvers);
    }

    private TagResolver toPlaceholder(@Subst("") String key, Object value) {
        if (value instanceof Component component) {
            return Placeholder.component(key, component);
        }
        if (value instanceof String str) {
            return Placeholder.parsed(key, str);
        }
        return Placeholder.unparsed(key, String.valueOf(value));
    }
}
