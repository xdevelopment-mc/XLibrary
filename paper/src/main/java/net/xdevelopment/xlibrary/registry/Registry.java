package net.xdevelopment.xlibrary.registry;

import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Anyachkaa
 */
public interface Registry<T> extends org.bukkit.Registry<Registry.@NotNull Holder<T>> {

    @Nullable
    NamespacedKey getKey(T value);

    record Holder<T>(T value, NamespacedKey key) implements Keyed {

        @Override
        @NotNull
        public NamespacedKey getKey() {
            return key;
        }
    }
}
