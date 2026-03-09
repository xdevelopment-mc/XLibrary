package net.xdevelopment.xlibrary.core.time.impl;

import net.xdevelopment.xlibrary.core.time.StaticDurationFormat;

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
