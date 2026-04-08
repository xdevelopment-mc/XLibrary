package net.xdevelopment.xlibrary.utility;

import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

public interface Displayable {

    @NotNull
    String displayName();

    @NotNull
    Material icon();
}
