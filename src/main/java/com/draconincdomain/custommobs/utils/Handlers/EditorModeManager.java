package com.draconincdomain.custommobs.utils.Handlers;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class EditorModeManager {
    private static Set<UUID> editorPlayers = new HashSet<>();
    private static Map<UUID, Region> playerRegions = new HashMap<>();

    public static void enterEditorMode(Player player) {
        editorPlayers.add(player.getUniqueId());
        player.sendMessage(ChatColor.AQUA + "You have entered Editor Mode.");

        // Give player tools for selection and mob spawning
        ItemStack selectionWand = new ItemStack(Material.BLAZE_ROD);
        ItemMeta wandMeta = selectionWand.getItemMeta();
        wandMeta.setDisplayName(ChatColor.GREEN + "Region Selector");
        selectionWand.setItemMeta(wandMeta);

        ItemStack mobSpawnerTool = new ItemStack(Material.CHEST);
        ItemMeta mobToolMeta = mobSpawnerTool.getItemMeta();
        mobToolMeta.setDisplayName(ChatColor.YELLOW + "Mob Spawn Tool");
        mobSpawnerTool.setItemMeta(mobToolMeta);

        ItemStack saveTool = new ItemStack(Material.NETHER_STAR);
        ItemMeta saveToolMeta = saveTool.getItemMeta();
        saveToolMeta.setDisplayName(ChatColor.GOLD + "Save Region");
        saveTool.setItemMeta(saveToolMeta);

        player.getInventory().clear();
        player.getInventory().setItem(0, selectionWand);
        player.getInventory().setItem(1, mobSpawnerTool);
        player.getInventory().setItem(8, saveTool);
    }

    public static void exitEditorMode(Player player) {
        editorPlayers.remove(player.getUniqueId());
        player.sendMessage(ChatColor.RED + "You have exited Editor Mode.");
        player.getInventory().clear();
    }

    public static boolean isInEditorMode(Player player) {
        return editorPlayers.contains(player.getUniqueId());
    }

    private static String formatLocation(Location location) {
        return String.format("(%d, %d, %d)", location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }
}
