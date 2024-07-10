package com.draconincdomain.custommobs.core.Inventory.MobGUI;

import com.draconincdomain.custommobs.core.RPGMobs.CustomMob;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MobInfoMenu {
    private Inventory inventory;

    public MobInfoMenu(CustomMob mob) {
        this.inventory = Bukkit.createInventory(null, 9, "Mob Info");

        addItem("Name: " + ChatColor.stripColor(mob.getName()), Material.PAPER);
        addItem("Entity type: " + mob.getEntityType().name(), Material.BOOK);
    }

    private void addItem(String info, Material material) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(info);
        item.setItemMeta(meta);
        inventory.setItem(inventory.firstEmpty(), item);
    }

    public void open (Player player) {
        player.openInventory(inventory);
    }
}
