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

public class MobCreationMenu implements InventoryHolder {
    private Inventory inventory;

    public MobCreationMenu() {
        this.inventory = Bukkit.createInventory(null, 27, "Custom Mob Creator");  // assuming that the player will not interact with it

        addItem("Set Name", Material.NAME_TAG, 1);
        addItem("Set Champion Status", Material.DIAMOND, 3);
        addItem("Set Health", Material.GOLDEN_APPLE, 5);
        addItem("Set Spawn Chance", Material.ENDER_EYE, 7);
        addItem("Set Entity Type", Material.CREEPER_SPAWN_EGG, 22);
        addItem("Set Weapon Attributes", Material.BOW, 12);
        addItem("Set Armor", Material.IRON_CHESTPLATE, 14);

        for (int slot = 0; slot < inventory.getSize(); slot++) {
            if (inventory.getItem(slot) == null) {
                addItem("", Material.GRAY_STAINED_GLASS_PANE, slot);
            }
        }
    }

    private void addItem(String info, Material material, int slot) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ColourCode.colour("&5" + info));
        item.setItemMeta(meta);
        inventory.setItem(slot, item);
    }

    public void open (Player player) {
        player.openInventory(inventory);
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }
}
