package net.xdevelopment.xlibrary.command;

import net.xdevelopment.xlibrary.core.utility.EnumUtility;
import net.xdevelopment.xlibrary.core.utility.NumberUtility;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.StringJoiner;

/**
 * @author Anyachkaa
 */
public interface ArgumentProvider {

    String argument(int i);

    int argumentCount();

    default boolean hasArgument(int i) {
        return i >= 0 && i < argumentCount();
    }

    default int argumentAsInt(int i, int defaultValue) {
        return hasArgument(i) ? NumberUtility.getInteger(argument(i), defaultValue) : defaultValue;
    }

    default long argumentAsLong(int i, long defaultValue) {
        return hasArgument(i) ? NumberUtility.getLong(argument(i), defaultValue) : defaultValue;
    }

    default double argumentAsDouble(int i, double defaultValue) {
        return hasArgument(i) ? NumberUtility.getDouble(argument(i), defaultValue) : defaultValue;
    }

    default float argumentAsFloat(int i, float defaultValue) {
        return hasArgument(i) ? NumberUtility.getFloat(argument(i), defaultValue) : defaultValue;
    }

    @Nullable
    default <T extends Enum<T>> T argumentAsEnum(int i, @NotNull Class<T> clazz) {
        return hasArgument(i) ? EnumUtility.get(argument(i), clazz) : null;
    }

    default @NotNull String joinArguments(int start) {
        var joiner = new StringJoiner(" ");
        for (int i = start, j = argumentCount(); i < j; i++) {
            joiner.add(argument(i));
        }
        return joiner.toString();
    }
}
