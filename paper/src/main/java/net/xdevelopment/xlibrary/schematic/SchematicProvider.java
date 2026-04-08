package net.xdevelopment.xlibrary.schematic;

import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.concurrent.CompletableFuture;

public interface SchematicProvider {

    boolean init();

    @NotNull
    CompletableFuture<Object> paste(@NotNull File file, @NotNull Location location);

    @NotNull
    CompletableFuture<File> save(@NotNull File file, @NotNull Location corner1, @NotNull Location corner2, boolean ignoreAir);

    @NotNull
    CompletableFuture<Void> undo(@NotNull Object session);
}
