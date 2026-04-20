package net.xdevelopment.xlibrary.utility.gui.slot;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.ItemLore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import net.kyori.adventure.text.Component;
import net.xdevelopment.xlibrary.utility.Displayable;
import net.xdevelopment.xlibrary.utility.HeadUtility;
import net.xdevelopment.xlibrary.utility.gui.executable.ExecutableClick;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Getter
@Accessors(chain = true, makeFinal = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@SuppressWarnings("UnstableApiUsage")
public final class MenuSlot {
    @NonFinal ItemStack item;
    @NonFinal ExecutableClick executable;
    @Getter
    @NonFinal boolean interactDisabled;
    @Setter
    @NonFinal int position;
    @NonFinal boolean staticSlot;

    public MenuSlot(@NotNull ItemStack item) {
        this.item = item;
    }

    public MenuSlot(@NotNull Material material) {
        this.item = ItemStack.of(material);
    }

    public MenuSlot(@NotNull String materialKey) {
        this.item = HeadUtility.headBuilder(materialKey);
    }

    public MenuSlot(@NotNull Displayable displayable) {
        this.item = ItemStack.of(displayable.getIcon());
        this.item.setData(DataComponentTypes.CUSTOM_NAME, Component.text(displayable.getDisplayName()));
    }

    public MenuSlot(@NotNull Material material, @NotNull String display, @NotNull List<String> lore) {
        this.item = ItemStack.of(material);
        this.item.setData(DataComponentTypes.CUSTOM_NAME, Component.text(display));
        this.item.setData(DataComponentTypes.LORE, ItemLore.lore()
                .addLines(lore.stream().map(Component::text).toList())
                .build());
        this.item.editMeta(meta -> meta.setHideTooltip(true));
    }

    public MenuSlot setItem(@NotNull ItemStack item) {
        this.item = item;
        return this;
    }

    public MenuSlot display(@NotNull String display) {
        item.setData(DataComponentTypes.CUSTOM_NAME, Component.text(display));
        return this;
    }

    public MenuSlot lore(@NotNull List<String> lore) {
        item.setData(DataComponentTypes.LORE, ItemLore.lore()
                .addLines(lore.stream().map(Component::text).toList())
                .build());
        return this;
    }

    public MenuSlot amount(int amount) {
        item.setAmount(amount);
        return this;
    }

    public MenuSlot hideAttributes(boolean hide) {
        item.editMeta(meta -> meta.setHideTooltip(hide));
        return this;
    }

    public MenuSlot enchanted(boolean enchanted) {
        item.setData(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, enchanted);
        return this;
    }

    public MenuSlot disableInteract(boolean disable) {
        this.interactDisabled = disable;
        return this;
    }

    public MenuSlot headOwner(@NotNull OfflinePlayer player) {
        if (!(item.getItemMeta() instanceof SkullMeta skullMeta)) {
            return this;
        }
        skullMeta.setOwningPlayer(player);
        item.setItemMeta(skullMeta);
        return this;
    }

    public MenuSlot headTexture(@NotNull String value) {
        this.item = HeadUtility.headBuilder("PLAYER_HEAD;" + value);
        return this;
    }

    public MenuSlot staticSlot(boolean staticSlot) {
        this.staticSlot = staticSlot;
        return this;
    }

    public boolean isStatic() {
        return staticSlot;
    }

    public MenuSlot executable(ExecutableClick executable) {
        this.executable = executable;
        if (executable != null) {
            executable.attach(this);
        }
        return this;
    }

    public boolean hasExecutable() {
        return executable != null;
    }
}
