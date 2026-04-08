package net.xdevelopment.xlibrary.core.time.impl;

import net.xdevelopment.xlibrary.core.time.StaticDurationFormat;

// Adapted and improved from: https://github.com/BlackBaroness/duration-serializer-java
public class MediumLenghtRussianDurationFormat extends StaticDurationFormat {

    public MediumLenghtRussianDurationFormat() {
        super("нсек",
                "мсек",
                "сек",
                "мин",
                "ч",
                "дн");
    }
}
