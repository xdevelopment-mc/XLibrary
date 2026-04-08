package net.xdevelopment.xlibrary.core.time.impl;

import net.xdevelopment.xlibrary.core.time.StaticDurationFormat;

// Adapted and improved from: https://github.com/BlackBaroness/duration-serializer-java
public class MediumLengthEnglishDurationFormat extends StaticDurationFormat {

    public MediumLengthEnglishDurationFormat() {
        super("nanos",
                "millis",
                "sec",
                "min",
                "hours",
                "days");
    }
}
