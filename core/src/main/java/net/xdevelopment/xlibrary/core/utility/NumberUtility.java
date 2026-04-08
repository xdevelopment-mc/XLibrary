// Parsing methods adapted and improved from: https://github.com/nulli0n/nightcore-spigot/blob/master/utils/src/main/java/su/nightexpress/nightcore/util/Numbers.java
package net.xdevelopment.xlibrary.core.utility;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Function;

@UtilityClass
public class NumberUtility {

    private final ThreadLocal<NumberFormat> FORMAT = ThreadLocal.withInitial(() -> {
        final NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
        nf.setMaximumFractionDigits(2);
        return nf;
    });

    @NotNull
    public String format(int amount) {
        return FORMAT.get().format(amount);
    }

    @NotNull
    public String format(double amount) {
        return FORMAT.get().format(amount);
    }

    @NotNull
    public String format(long amount) {
        return FORMAT.get().format(amount);
    }

    @NotNull
    public String formatCompact(long amount, @NotNull String[] suffixes) {
        if (amount < 1000) {
            return String.valueOf(amount);
        }
        int index = 0;
        double value = amount;
        while (value >= 1000 && index < suffixes.length - 1) {
            value /= 1000;
            index++;
        }
        return FORMAT.get().format(value) + suffixes[index];
    }

    @NotNull
    public Optional<Integer> parseInteger(@NotNull String input) {
        return parseNumber(input, Integer::parseInt);
    }

    @Contract(pure = true)
    public int getInteger(@NotNull String input, int defaultValue) {
        return parseInteger(input).orElse(defaultValue);
    }

    @Contract(pure = true)
    public int getIntegerAbs(@NotNull String input, int defaultValue) {
        return Math.abs(getInteger(input, defaultValue));
    }

    @NotNull
    public Optional<Long> parseLong(@NotNull String input) {
        return parseNumber(input, Long::parseLong);
    }

    @Contract(pure = true)
    public long getLong(@NotNull String input, long defaultValue) {
        return parseLong(input).orElse(defaultValue);
    }

    @Contract(pure = true)
    public long getLongAbs(@NotNull String input, long defaultValue) {
        return Math.abs(getLong(input, defaultValue));
    }

    @NotNull
    public Optional<Float> parseFloat(@NotNull String input) {
        return parseNumber(input, raw -> {
            float value = Float.parseFloat(raw);
            return Float.isNaN(value) || Float.isInfinite(value) ? null : value;
        });
    }

    @Contract(pure = true)
    public float getFloat(@NotNull String input, float defaultValue) {
        return parseFloat(input).orElse(defaultValue);
    }

    @Contract(pure = true)
    public float getFloatAbs(@NotNull String input, float defaultValue) {
        return Math.abs(getFloat(input, defaultValue));
    }

    @NotNull
    public Optional<Double> parseDouble(@NotNull String input) {
        return parseNumber(input, raw -> {
            double value = Double.parseDouble(raw);
            return Double.isNaN(value) || Double.isInfinite(value) ? null : value;
        });
    }

    @Contract(pure = true)
    public double getDouble(@NotNull String input, double defaultValue) {
        return parseDouble(input).orElse(defaultValue);
    }

    @Contract(pure = true)
    public double getDoubleAbs(@NotNull String input, double defaultValue) {
        return Math.abs(getDouble(input, defaultValue));
    }

    @NotNull
    private <T> Optional<T> parseNumber(@NotNull String input, @NotNull Function<String, T> converter) {
        if (input.isBlank()) return Optional.empty();

        try {
            T value = converter.apply(input);
            return value == null ? Optional.empty() : Optional.of(value);
        } catch (NumberFormatException ignored) {
            return Optional.empty();
        }
    }
}
