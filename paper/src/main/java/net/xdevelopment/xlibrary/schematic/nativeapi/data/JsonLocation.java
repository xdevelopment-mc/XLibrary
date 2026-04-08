package net.xdevelopment.xlibrary.schematic.nativeapi.data;

import lombok.AllArgsConstructor;
import org.bukkit.Location;

public record JsonLocation(double x, double y, double z, String worldName) {

    public static JsonLocation fromLocation(Location location) {
        return new JsonLocation(location.getX(), location.getY(), location.getZ(), location.getWorld().getName());
    }
}
