package net.xdevelopment.xlibrary.core.utility;

import java.util.concurrent.ThreadLocalRandom;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MathUtility {

    public int randomMinMax(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    public double mapPercentTo1(double value, double maxValue) {
        return 1.0 - getPercent(value, maxValue) / 100.0;
    }

    public double getPercent(double value, double maxValue) {
        return (value * 100.0) / maxValue;
    }
}
