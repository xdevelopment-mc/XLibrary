package net.xdevelopment.xlibrary.utility.gui.executable;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface ExecutableClose {
    void run(@NotNull Player player);
}
