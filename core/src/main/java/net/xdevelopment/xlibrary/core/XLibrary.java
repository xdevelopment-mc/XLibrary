package net.xdevelopment.xlibrary.core;

import org.jetbrains.annotations.NotNull;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class XLibrary {

    private static XLibrary instance;

    @NotNull
    public static XLibrary getInstance() {
        if (instance == null) {
            instance = new XLibrary();
        }
        return instance;
    }

    public void init() {
    }

    public void shutdown() {
    }
}
