package net.xdevelopment.xlibrary.core.time;

import java.time.Duration;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public interface DurationFormat {

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull String serialize(@NotNull DurationUnit unit, @Range(from = 0, to = Long.MAX_VALUE) long amount);

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull Duration deserialize(
            @NotNull String unitInput,
            @Range(from = 0, to = Long.MAX_VALUE) long amountInput
    ) throws InvalidFormatException;
}
