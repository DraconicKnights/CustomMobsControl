package com.draconincdomain.custommobs.utils.Handlers;

import com.draconincdomain.custommobs.CustomMobsControl;
import com.draconincdomain.custommobs.core.RPGMobs.CustomMob;
import com.draconincdomain.custommobs.utils.Arrays.CustomEntityArrayHandler;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.*;
import java.nio.file.Files;
import java.util.*;

public class EditorModeManager {
    private static Set<UUID> editorPlayers = new HashSet<>();
    private static final Map<UUID, Region> playerRegions = new HashMap<>();

    public static void enterEditorMode(Player player) {
        editorPlayers.add(player.getUniqueId());
        player.sendMessage(ChatColor.AQUA + "You have entered Editor Mode.");

        // Give player tools for selection and mob spawning
        ItemStack selectionWand = new ItemStack(Material.BLAZE_ROD);
        ItemMeta wandMeta = selectionWand.getItemMeta();
        wandMeta.setDisplayName(ChatColor.GREEN + "Region Selector");
        selectionWand.setItemMeta(wandMeta);

        ItemStack regionNameTool = new ItemStack(Material.ANVIL);
        ItemMeta regionNameToolItemMeta = regionNameTool.getItemMeta();
        regionNameToolItemMeta.setDisplayName(ChatColor.GREEN + "Region Name Tool");
        regionNameTool.setItemMeta(regionNameToolItemMeta);

        ItemStack mobSpawnerTool = new ItemStack(Material.CHEST);
        ItemMeta mobToolMeta = mobSpawnerTool.getItemMeta();
        mobToolMeta.setDisplayName(ChatColor.YELLOW + "Mob Spawn Tool");
        mobSpawnerTool.setItemMeta(mobToolMeta);

        ItemStack levelSetTool = new ItemStack(Material.DIAMOND);
        ItemMeta levelSetToolMeta = levelSetTool.getItemMeta();
        levelSetToolMeta.setDisplayName(ChatColor.GREEN + "Level Tool");
        levelSetTool.setItemMeta(levelSetToolMeta);

        ItemStack saveTool = new ItemStack(Material.NETHER_STAR);
        ItemMeta saveToolMeta = saveTool.getItemMeta();
        saveToolMeta.setDisplayName(ChatColor.GOLD + "Save Region");
        saveTool.setItemMeta(saveToolMeta);

        player.getInventory().clear();
        player.getInventory().setItem(0, selectionWand);
        player.getInventory().setItem(2, regionNameTool);
        player.getInventory().setItem(4, mobSpawnerTool);
        player.getInventory().setItem(6, levelSetTool);
        player.getInventory().setItem(8, saveTool);
    }

    public static Region getPlayerRegion(Player player) {
        return playerRegions.computeIfAbsent(player.getUniqueId(), p -> new Region());
    }

    public static void setRegionCorner(Player player, Location location, boolean isFirstCorner) {
        Region region = getPlayerRegion(player);
        if (isFirstCorner) {
            region.setFirstCorner(location);
            player.sendMessage(ChatColor.GREEN + "First corner set at " + formatLocation(location));
        } else {
            region.setSecondCorner(location);
            player.sendMessage(ChatColor.GREEN + "Second corner set at " + formatLocation(location));
        }
        RegionManager.getInstance().addRegion(region);
    }

    public static void setRegionName(Player player, String name) {
        Region region = getPlayerRegion(player);
        region.setRegionName(name);
        player.sendMessage(ChatColor.GREEN + "Region name has been set");
    }

    public static void setRegionMobs(Player player) {
        Region region = getPlayerRegion(player);
        List<CustomMob> mobList = new ArrayList<>(CustomEntityArrayHandler.getRegisteredCustomMobs().values());
        region.setSpawnableMobs(mobList);
        player.sendMessage(ChatColor.GREEN + "Region mobs set");
    }

    public static void setRegionLevels(Player player, int minLevel, int maxLevel) {
        Region region = getPlayerRegion(player);
        region.setMinLevel(minLevel);
        region.setMaxLevel(maxLevel);
        player.sendMessage(ChatColor.GREEN + "Region level set");
    }

    public static void saveRegion(Player player) {
        Region region = getPlayerRegion(player);
        RegionManager.getInstance().saveRegion(region);
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
