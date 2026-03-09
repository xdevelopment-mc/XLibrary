package net.xdevelopment.xlibrary.core.time.impl;

import net.xdevelopment.xlibrary.core.time.StaticDurationFormat;

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
