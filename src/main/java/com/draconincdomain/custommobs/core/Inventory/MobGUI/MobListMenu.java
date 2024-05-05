package com.draconincdomain.custommobs.core.Inventory.MobGUI;

import com.draconincdomain.custommobs.core.RPGMobs.CustomMob;
import com.draconincdomain.custommobs.utils.Arrays.CustomEntityArrayHandler;
import com.draconincdomain.custommobs.utils.Desing.ColourCode;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MobListMenu implements InventoryHolder {
    private final Inventory inventory;

    public MobListMenu() {

        int numMobs = CustomEntityArrayHandler.getRegisteredCustomMobs().size();
        int numRows = (int) Math.ceil(numMobs / 9.0);
        int size = Math.min(numRows * 9, 54);

        this.inventory = Bukkit.createInventory(this, size, ColourCode.colour("&5 Registered Entities"));
        // Add an item for each registered custom mob
        for (CustomMob mob : CustomEntityArrayHandler.getRegisteredCustomMobs().values()) {
            addItem(mob);  // assuming getEntityType() returns an EntityType
        }
    }

    private void addItem(CustomMob mob) {
        ItemStack item = new ItemStack(Material.valueOf(mob.getEntityType().name() + "_SPAWN_EGG"));
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ColourCode.colour(mob.getMobNameID()));
        List<String> lore = new ArrayList<>();
        lore.add("ID: " + mob.getMobID());
        meta.setLore(lore);
        item.setItemMeta(meta);
        inventory.addItem(item);  // to the next empty slot
    }

    public void open(Player player) {
        player.openInventory(inventory);
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }
}
