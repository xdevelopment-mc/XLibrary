package net.xdevelopment.xlibrary.schematic.nativeapi.data;

import lombok.AllArgsConstructor;

import java.util.List;

public record JsonSchematicData(
        JsonLocation firstPoint,
        JsonLocation secondPoint,
        JsonLocation midPoint,
        List<WorldBlock> blocks
) {}
