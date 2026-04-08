package net.xdevelopment.xlibrary.core.time.impl.en;

import net.xdevelopment.xlibrary.core.time.StaticDurationFormat;

// Adapted and improved from: https://github.com/BlackBaroness/duration-serializer-java
public class ShortEnglishDurationFormat extends StaticDurationFormat {

    public ShortEnglishDurationFormat() {
        super("ns",
                "ms",
                "s",
                "min",
                "h",
                "d");
    }
}
