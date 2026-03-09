package net.xdevelopment.xlibrary.core.utility;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pair<K, V> {

    private K key;
    private V value;

    public Pair(@NotNull Pair<K, V> pair) {
        this.key = pair.getKey();
        this.value = pair.getValue();
    }
}