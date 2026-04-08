package net.xdevelopment.xlibrary.schematic.selection;

import lombok.Getter;
import lombok.Setter;
import net.xdevelopment.xlibrary.core.Identifiable;
import org.bukkit.Location;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

@Getter
@Setter
public class PlayerSelection implements Identifiable {

    private final UUID uniqueId = generateUniqueId();
    private Location pos1;
    private Location pos2;

    @Override
    public @org.jetbrains.annotations.NotNull String name() {
        return "Selection-" + uniqueId;
    }

    @Override
    public @org.jetbrains.annotations.NotNull UUID uniqueId() {
        return uniqueId;
    }

    public boolean isComplete() {
        return pos1 != null && pos2 != null;
    }

    @Nullable
    public Location getMinCorner() {
        if (!isComplete()) return null;
        return new Location(pos1.getWorld(),
                Math.min(pos1.getBlockX(), pos2.getBlockX()),
                Math.min(pos1.getBlockY(), pos2.getBlockY()),
                Math.min(pos1.getBlockZ(), pos2.getBlockZ()));
    }

    @Nullable
    public Location getMaxCorner() {
        if (!isComplete()) return null;
        return new Location(pos1.getWorld(),
                Math.max(pos1.getBlockX(), pos2.getBlockX()),
                Math.max(pos1.getBlockY(), pos2.getBlockY()),
                Math.max(pos1.getBlockZ(), pos2.getBlockZ()));
    }

    public void clear() {
        this.pos1 = null;
        this.pos2 = null;
    }
}
