package net.xdevelopment.xlibrary.core;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * @author Anyachkaa
 */
public interface Identifiable {

    default Long getId() {
        return -1L;
    }

    @NotNull
    String getName();

    @NotNull
    UUID getUniqueId();

    default UUID generateUniqueId() {
        return UUID.randomUUID();
    }
}
