package net.xdevelopment.xlibrary.utility;

import org.bukkit.Material;

import org.jetbrains.annotations.NotNull;

/**
 * @author Anyachkaa
 */
public interface Displayable {

    @NotNull
    String getDisplayName();

    @NotNull
    Material getIcon();
}
