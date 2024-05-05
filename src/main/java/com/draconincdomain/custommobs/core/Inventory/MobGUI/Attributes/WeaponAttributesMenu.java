package com.draconincdomain.custommobs.core.Inventory.MobGUI.Attributes;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class WeaponAttributesMenu implements InventoryHolder {

    private Inventory inventory;

    public WeaponAttributesMenu() {
        this.inventory = Bukkit.createInventory(this, 27, "Weapon Attributes");

        addItem("Set Material", Material.IRON_SWORD, 10);
        addItem("Set Enchantments", Material.ENCHANTED_BOOK, 12);
        addItem("Set Unbreakable", Material.ANVIL, 14);
        addItem("Set Hide Attributes", Material.BOOK, 16);

        // Rest are filled with non-clickable item (e.g., Glass Pane)
        for (int slot = 0; slot < inventory.getSize(); slot++)
            if (inventory.getItem(slot) == null)
                addItem("", Material.GRAY_STAINED_GLASS_PANE, slot);

    }

    private void addItem(String info, Material material, int slot) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(info);
        item.setItemMeta(meta);
        this.inventory.setItem(slot, item);
    }

    public void open(Player player) {
        player.openInventory(inventory);
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }
}
