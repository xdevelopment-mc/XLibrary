package net.xdevelopment.xlibrary.core.time.impl.en;

import net.xdevelopment.xlibrary.core.time.StaticDurationFormat;

// Adapted and improved from: https://github.com/BlackBaroness/duration-serializer-java
public class FullWordEnglishDurationFormat extends StaticDurationFormat {

    public FullWordEnglishDurationFormat() {
        super("nanoseconds",
                "milliseconds",
                "seconds",
                "minutes",
                "hours",
                "days");
    }
}
