package net.xdevelopment.xlibrary.core;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class XLibrary {

    private static final XLibrary INSTANCE = new XLibrary();

    public static XLibrary getInstance() {
        return INSTANCE;
    }

    public void init() {
    }

    public void shutdown() {
    }
}
