package net.xdevelopment.xlibrary.core.utility;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import lombok.experimental.UtilityClass;
import net.xdevelopment.xlibrary.core.time.DurationFormat;
import net.xdevelopment.xlibrary.core.time.impl.FullWordEnglishDurationFormat;
import net.xdevelopment.xlibrary.core.time.impl.FullWordRussianDurationFormat;
import net.xdevelopment.xlibrary.core.time.impl.MediumLenghtRussianDurationFormat;
import net.xdevelopment.xlibrary.core.time.impl.MediumLengthEnglishDurationFormat;
import net.xdevelopment.xlibrary.core.time.impl.ShortEnglishDurationFormat;
import net.xdevelopment.xlibrary.core.time.impl.ShortRussianDurationFormat;

@UtilityClass
public class DurationFormats {

    private final ShortEnglishDurationFormat shortEnglish = new ShortEnglishDurationFormat();
    private final MediumLengthEnglishDurationFormat mediumEnglish = new MediumLengthEnglishDurationFormat();
    private final FullWordEnglishDurationFormat fullEnglish = new FullWordEnglishDurationFormat();

    private final ShortRussianDurationFormat shortRussian = new ShortRussianDurationFormat();
    private final MediumLenghtRussianDurationFormat mediumRussian = new MediumLenghtRussianDurationFormat();
    private final FullWordRussianDurationFormat fullRussian = new FullWordRussianDurationFormat();

    @Contract(pure = true)
    public @NotNull DurationFormat shortEnglish() {
        return shortEnglish;
    }

    @Contract(pure = true)
    public @NotNull DurationFormat mediumLengthEnglish() {
        return mediumEnglish;
    }

    @Contract(pure = true)
    public @NotNull DurationFormat fullWordEnglish() {
        return fullEnglish;
    }

    @Contract(pure = true)
    public @NotNull ShortRussianDurationFormat shortRussian() {
        return shortRussian;
    }

    @Contract(pure = true)
    public @NotNull DurationFormat mediumLengthRussian() {
        return mediumRussian;
    }

    @Contract(pure = true)
    public @NotNull DurationFormat fullWordRussian() {
        return fullRussian;
    }

    @Contract(value = "-> new", pure = true)
    public @NotNull DurationFormat @NotNull [] allBundled() {
        return new DurationFormat[]{
                shortEnglish, mediumEnglish, fullEnglish,
                shortRussian, mediumRussian, fullRussian
        };
    }
}
