package net.xdevelopment.xlibrary.core;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public interface Identifiable {

    default Long id() {
        return -1L;
    }

    @NotNull
    String name();

    @NotNull
    UUID uniqueId();

    default UUID generateUniqueId() {
        return UUID.randomUUID();
    }
}
