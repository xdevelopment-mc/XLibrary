// String methods adapted and improved from: https://github.com/nulli0n/nightcore-spigot
package net.xdevelopment.xlibrary.core.utility;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Predicate;

@UtilityClass
public class StringUtility {

    @NotNull
    public Optional<String> varStyle(@NotNull String str) {
        return varStyle(str, StringUtility::isValidVariableChar);
    }

    @NotNull
    public Optional<String> varStyle(@NotNull String str, @NotNull Predicate<Character> predicate) {
        char[] chars = str.toLowerCase(Locale.ROOT).toCharArray();

        StringBuilder builder = new StringBuilder();
        for (char letter : chars) {
            if (Character.isWhitespace(letter)) {
                builder.append("_");
                continue;
            }
            if (!predicate.test(letter)) {
                continue;
            }
            builder.append(letter);
        }

        String result = builder.toString();
        return result.isBlank() ? Optional.empty() : Optional.of(result);
    }

    @NotNull
    public String toBase64(@NotNull String string) {
        byte[] bytes = string.getBytes(StandardCharsets.UTF_8);
        return Base64.getEncoder().encodeToString(bytes);
    }

    @NotNull
    public String fromBase64(@NotNull String encoded) {
        byte[] bytes = Base64.getDecoder().decode(encoded);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    private boolean isValidVariableChar(char c) {
        return Character.isLetterOrDigit(c) || c == '_' || c == '-';
    }
}
