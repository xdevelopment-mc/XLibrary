package net.xdevelopment.xlibrary.schematic.nativeapi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.experimental.UtilityClass;
import net.xdevelopment.xlibrary.core.utility.NumberUtility;
import net.xdevelopment.xlibrary.schematic.nativeapi.data.JsonLocation;
import net.xdevelopment.xlibrary.schematic.nativeapi.data.JsonSchematicData;
import net.xdevelopment.xlibrary.schematic.nativeapi.data.WorldBlock;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

// Deprecated: будет удалено через 5-6 месяцев после релиза нового формата
@UtilityClass
public class SchematicConverter {

    final Gson GSON = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();

    @NotNull
        String json = Files.readString(esrcFile.toPath());
        JsonObject root = JsonParser.parseString(json).getAsJsonObject();

        JsonLocation firstPoint = GSON.fromJson(root.get("firstPoint"), JsonLocation.class);
        JsonLocation secondPoint = GSON.fromJson(root.get("secondPoint"), JsonLocation.class);
        JsonLocation midPoint = GSON.fromJson(root.get("midPoint"), JsonLocation.class);
        List<WorldBlock> blocks = parseLegacyBlocks(root.get("data").getAsString());

        JsonSchematicData newData = new JsonSchematicData(firstPoint, secondPoint, midPoint, blocks);

        String baseName = esrcFile.getName().replace(".esrc", "");
        File jsonFile = new File(esrcFile.getParentFile(), baseName + ".json");

        Files.writeString(jsonFile.toPath(), GSON.toJson(newData));
        return jsonFile;
    }

    public int convertAll(@NotNull File directory) {
        File[] esrcFiles = directory.listFiles((dir, name) -> name.endsWith(".esrc"));
        if (esrcFiles == null) return 0;

        int converted = 0;
        for (File esrc : esrcFiles) {
            try {
                convert(esrc);
                converted++;
            } catch (Exception ignored) {}
        }
        return converted;
    }

    @NotNull
    private List<WorldBlock> parseLegacyBlocks(@NotNull String data) {
        String[] split = data.split("@");
        List<WorldBlock> blocks = new ArrayList<>(split.length);

        for (String blockStr : split) {
            if (blockStr.isEmpty()) continue;
            String[] info = blockStr.split("%");
            if (info.length < 5) continue;

            int dx = NumberUtility.getInteger(info[0], 0);
            int dy = NumberUtility.getInteger(info[1], 0);
            int dz = NumberUtility.getInteger(info[2], 0);

            blocks.add(new WorldBlock(dx, dy, dz, info[4]));
        }
        return blocks;
    }
}
