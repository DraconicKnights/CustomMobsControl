package com.draconincdomain.custommobs.events;

import com.draconincdomain.custommobs.core.Annotations.Events;
import com.draconincdomain.custommobs.utils.Handlers.EditorModeManager;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

@Events
public class RegionEditorListener implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (EditorModeManager.isInEditorMode(player)) {
            ItemStack item = player.getInventory().getItemInMainHand();

            if (!item.hasItemMeta()) return;

            String itemName = item.getItemMeta().getDisplayName();

            if (itemName.contains("Mob Spawn Tool")) {
                EditorModeManager.setRegionMobs( player);
            }
            else if (itemName.contains("Level Tool")) {
                EditorModeManager.setRegionLevels( player, 10, 20);
            }
            else if (itemName.contains("Region Name Tool")) {
                EditorModeManager.setRegionName( player, "Example");
            }

            if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                Block clickedBlock = event.getClickedBlock();

                if (clickedBlock != null) {
                    Location clickedLocation = clickedBlock.getLocation();

                    if (itemName.contains("Region Selector")) {
                        // Check if this is first or second corner
                        EditorModeManager.setRegionCorner(player, clickedLocation, !player.isSneaking());
                    }
                }

            } else if (itemName.contains("Save Region")) {
                // Save the region when Nether Star is clicked
                EditorModeManager.saveRegion(player);
            }
        }
    }

    // preventing item movement in inventory if player is in Editor Mode
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            if (EditorModeManager.isInEditorMode(player)) {
                event.setCancelled(true);
            }
        }
    }

    // preventing item drop if player is in Editor Mode
    @EventHandler
    public void onDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if (EditorModeManager.isInEditorMode(player)) {
            event.setCancelled(true);
        }
    }
}
