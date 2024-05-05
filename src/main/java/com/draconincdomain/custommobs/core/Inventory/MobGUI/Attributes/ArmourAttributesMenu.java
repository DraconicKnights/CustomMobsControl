package com.draconincdomain.custommobs.core.Inventory.MobGUI.Attributes;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class ArmourAttributesMenu implements InventoryHolder {
    private Inventory inventory;

    public ArmourAttributesMenu() {
        this.inventory = Bukkit.createInventory(this, 9, "Armor Attributes");

        addItem("Set Boots", Material.IRON_BOOTS, 2);
        addItem("Set Leggings", Material.IRON_LEGGINGS, 4);
        addItem("Set Chestplate", Material.IRON_CHESTPLATE, 6);

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
