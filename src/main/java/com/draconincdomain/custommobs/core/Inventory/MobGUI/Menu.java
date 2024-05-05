package com.draconincdomain.custommobs.core.Inventory.MobGUI;

import com.draconincdomain.custommobs.utils.Desing.ColourCode;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class Menu implements InventoryHolder {
    private final Inventory inventory;

    public Menu() {
        this.inventory = Bukkit.createInventory(this, 9, ColourCode.colour("&6 Custom Mob Control"));

        addItem("Spawn Custom Mob", Material.DIAMOND, 3);
        addItem("Custom Mob Creation", Material.ANVIL, 5);
    }

    private void addItem(String info, Material material, int slot) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(info);
        item.setItemMeta(meta);
        inventory.setItem(slot, item);
    }

    public void open(Player player) {
        player.openInventory(inventory);
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }
}
