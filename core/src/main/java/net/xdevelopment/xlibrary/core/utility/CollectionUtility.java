// Collection methods adapted and improved from: https://github.com/nulli0n/nightcore-spigot
package net.xdevelopment.xlibrary.core.utility;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@UtilityClass
public class CollectionUtility {

    @NotNull
    public <T, R> Set<R> modify(@NotNull Set<T> set, @NotNull Function<T, R> function) {
        return set.stream().map(function).collect(Collectors.toCollection(HashSet::new));
    }

    @NotNull
    public <T, R> List<R> modify(@NotNull List<T> list, @NotNull Function<T, R> function) {
        return list.stream().map(function).collect(Collectors.toCollection(ArrayList::new));
    }

    @NotNull
    public static <T> List<List<T>> split(@NotNull List<T> list, int targetSize) {
        List<List<T>> lists = new ArrayList<>();
        if (targetSize <= 0) return lists;

        for (int index = 0; index < list.size(); index += targetSize) {
            lists.add(list.subList(index, Math.min(index + targetSize, list.size())));
        }
        return lists;
    }

    @NotNull
    public List<String> replace(@NotNull List<String> origin, @NotNull String var, String... with) {
        return replace(origin, var, Arrays.asList(with));
    }

    @NotNull
    public List<String> replace(@NotNull List<String> origin, @NotNull String var, @NotNull List<String> with) {
        List<String> replaced = new ArrayList<>();
        for (String line : origin) {
            if (line.equalsIgnoreCase(var)) {
                replaced.addAll(with);
            } else {
                replaced.add(line);
            }
        }
        return replaced;
    }

    @NotNull
    public List<String> getSequentialMatches(@NotNull List<String> results, @NotNull String input) {
        if (input.isBlank()) return results;

        char[] inputChars = input.toCharArray();
        List<String> matches = new ArrayList<>();

        searchWord:
        for (String resultItem : results) {
            int itemLength = resultItem.length();
            if (input.length() > itemLength) continue;

            int lastIndex = -1;
            for (char letter : inputChars) {
                int nextIndex = lastIndex < 0 ? 0 : lastIndex < itemLength - 1 ? lastIndex + 1 : lastIndex;

                letter = Character.toLowerCase(letter);
                int index = resultItem.toLowerCase(Locale.ROOT).indexOf(letter, nextIndex);
                if (index <= lastIndex) {
                    continue searchWord;
                }

                lastIndex = index;
            }
            matches.add(resultItem);
        }
        return matches;
    }

    @NotNull
    public <K, V extends Comparable<? super V>> Map<K, V> sortAscent(@NotNull Map<K, V> map) {
        return sort(map, Map.Entry.comparingByValue());
    }

    @NotNull
    public <K, V extends Comparable<? super V>> Map<K, V> sortDescent(@NotNull Map<K, V> map) {
        return sort(map, Collections.reverseOrder(Map.Entry.comparingByValue()));
    }

    @NotNull
    public <K, V extends Comparable<? super V>> Map<K, V> sort(@NotNull Map<K, V> map, @NotNull Comparator<Map.Entry<K, V>> comparator) {
        return new LinkedList<>(map.entrySet()).stream().sorted(comparator)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (old, nev) -> nev, LinkedHashMap::new));
    }
}
