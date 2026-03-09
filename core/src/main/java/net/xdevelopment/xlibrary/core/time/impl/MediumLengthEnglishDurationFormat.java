package net.xdevelopment.xlibrary.core.time.impl;

import net.xdevelopment.xlibrary.core.time.StaticDurationFormat;

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
