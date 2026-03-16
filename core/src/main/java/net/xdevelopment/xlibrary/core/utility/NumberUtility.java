package net.xdevelopment.xlibrary.core.utility;

import java.text.NumberFormat;
import java.util.Locale;

import lombok.experimental.UtilityClass;

@UtilityClass
public class NumberUtility {

    private final ThreadLocal<NumberFormat> FORMAT = ThreadLocal.withInitial(() -> {
        final NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
        nf.setMaximumFractionDigits(2);
        return nf;
    });

    public String format(int amount) {
        return FORMAT.get().format(amount);
    }

    public String format(double amount) {
        return FORMAT.get().format(amount);
    }

    public String format(long amount) {
        return FORMAT.get().format(amount);
    }

    public String formatCompact(long amount, String[] suffixes) {
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
}
