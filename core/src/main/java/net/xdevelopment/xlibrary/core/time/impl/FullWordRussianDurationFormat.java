package net.xdevelopment.xlibrary.core.time.impl;

import net.xdevelopment.xlibrary.core.time.StaticDurationFormat;

public class FullWordRussianDurationFormat extends StaticDurationFormat {

    public FullWordRussianDurationFormat() {
        super("наносекунд",
                "миллисекунд",
                "секунд",
                "минут",
                "часов",
                "дней");
    }
}
