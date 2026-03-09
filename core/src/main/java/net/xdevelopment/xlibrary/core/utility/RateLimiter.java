package net.xdevelopment.xlibrary.core.utility;

import java.util.concurrent.TimeUnit;

import lombok.Getter;
import lombok.Setter;

public class RateLimiter {

    @Getter private final int max;
    @Getter private final long periodMillis;
    @Getter @Setter private int amount;
    private long lastCheck;

    public RateLimiter(int max, long period) {
        this(max, period, TimeUnit.SECONDS);
    }

    public RateLimiter(int max, long period, TimeUnit unit) {
        this.max = max;
        this.periodMillis = unit.toMillis(period);
        this.amount = 0;
        this.lastCheck = System.currentTimeMillis();
    }

    public void reset() {
        this.amount = 0;
        this.lastCheck = System.currentTimeMillis();
    }

    public boolean isLimited() {
        recheck();
        return this.amount >= this.max;
    }

    public void increment() {
        this.amount++;
    }

    public boolean tryAcquire() {
        if (isLimited()) {
            return false;
        }
        this.amount++;
        return true;
    }

    private void recheck() {
        if (System.currentTimeMillis() - this.lastCheck >= this.periodMillis) {
            reset();
        }
    }
}
