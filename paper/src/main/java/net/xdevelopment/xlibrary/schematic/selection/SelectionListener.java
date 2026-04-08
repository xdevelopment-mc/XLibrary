package net.xdevelopment.xlibrary.schematic.selection;

import net.xdevelopment.xlibrary.schematic.SchematicMessages;
import net.xdevelopment.xlibrary.utility.ColorUtility;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SelectionListener implements Listener {

    public static final Material WAND_MATERIAL = Material.WOODEN_AXE;
    private final Map<UUID, PlayerSelection> selections = new HashMap<>();

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack hand = player.getInventory().getItemInMainHand();

        if (hand.getType() != WAND_MATERIAL) return;
        if (!player.hasPermission("xlibrary.schematic.wand")) return;

        Location clicked = event.getClickedBlock() != null
                ? event.getClickedBlock().getLocation()
                : null;

        if (clicked == null) return;

        PlayerSelection selection = selections.computeIfAbsent(player.getUniqueId(), k -> new PlayerSelection());

        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            event.setCancelled(true);
            selection.setPos1(clicked);
            player.sendMessage(ColorUtility.colorize(SchematicMessages.POS1_SET, Map.of(
                    "x", clicked.getBlockX(),
                    "y", clicked.getBlockY(),
                    "z", clicked.getBlockZ()
            )));
        } else if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            event.setCancelled(true);
            selection.setPos2(clicked);
            player.sendMessage(ColorUtility.colorize(SchematicMessages.POS2_SET, Map.of(
                    "x", clicked.getBlockX(),
                    "y", clicked.getBlockY(),
                    "z", clicked.getBlockZ()
            )));
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        selections.remove(event.getPlayer().getUniqueId());
    }

    public PlayerSelection getSelection(Player player) {
        return selections.get(player.getUniqueId());
    }

    public PlayerSelection getOrCreateSelection(Player player) {
        return selections.computeIfAbsent(player.getUniqueId(), k -> new PlayerSelection());
    }
}
