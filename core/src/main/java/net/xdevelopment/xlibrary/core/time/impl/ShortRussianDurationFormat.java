package net.xdevelopment.xlibrary.core.time.impl;

import net.xdevelopment.xlibrary.core.time.StaticDurationFormat;

// Adapted and improved from: https://github.com/BlackBaroness/duration-serializer-java
public class ShortRussianDurationFormat extends StaticDurationFormat {

    public ShortRussianDurationFormat() {
        super("нс",
                "мс",
                "с",
                "м",
                "ч",
                "д");
    }
}
