package net.xdevelopment.xlibrary.schematic.nativeapi.load;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import net.xdevelopment.xlibrary.schematic.nativeapi.data.JsonSchematicData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.nio.file.Files;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
public class AsyncSchematicLoader {

    private final Plugin plugin;
    private final Gson gson;
    private final int pasteTransactionSize;

    public void loadAndPaste(File file, Location location, CompletableFuture<Object> completion) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                if (!file.exists()) {
                    completion.complete(null);
                    return;
                }

                String json = Files.readString(file.toPath());
                JsonSchematicData data = gson.fromJson(json, JsonSchematicData.class);

                if (data.blocks() == null || data.blocks().isEmpty()) {
                    completion.complete(null);
                    return;
                }

                TerritoryPasteTransaction transaction = new TerritoryPasteTransaction(
                        plugin, location, data.blocks(), pasteTransactionSize
                );

                transaction.start(completion);

            } catch (Exception exception) {
                completion.completeExceptionally(exception);
            }
        });
    }
}
