package net.xdevelopment.xlibrary.utility;

import java.util.UUID;

import net.xdevelopment.xlibrary.core.utility.EnumUtility;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;

import lombok.experimental.UtilityClass;

@UtilityClass
public class HeadUtility {

    private final String HEAD_PREFIX = "PLAYER_HEAD;";

    public ItemStack headBuilder(String material) {
        if (material.startsWith(HEAD_PREFIX)) {
            final ItemStack stack = ItemStack.of(Material.PLAYER_HEAD);
            final SkullMeta meta = (SkullMeta) stack.getItemMeta();
            final String texture = material.substring(HEAD_PREFIX.length());
            if (meta != null) {
                createRandomProfile(meta, texture);
                stack.setItemMeta(meta);
            }
            return stack;
        }
        Material mat = EnumUtility.get(material, Material.class);
        return ItemStack.of(mat != null ? mat : Material.BARRIER);
    }

    private void createRandomProfile(SkullMeta skullMeta, String texture) {
        final PlayerProfile profile = Bukkit.createProfile(UUID.randomUUID());
        profile.setProperty(new ProfileProperty("textures", texture));
        skullMeta.setPlayerProfile(profile);
    }
}
