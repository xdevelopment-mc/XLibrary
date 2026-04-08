package net.xdevelopment.xlibrary.core.time.impl.ru;

import net.xdevelopment.xlibrary.core.time.StaticDurationFormat;

// Adapted and improved from: https://github.com/BlackBaroness/duration-serializer-java
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
