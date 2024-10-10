package com.draconincdomain.custommobs.core.Boss;

import com.draconincdomain.custommobs.utils.ItemDrop;
import org.bukkit.inventory.ItemStack;

import java.util.*;

/**
 * Custom Entity LootTable for plugin
 */
public class LootTable {

    // This map stores each ItemStack along with its probability (out of 100)
    private final List<ItemDrop> lootItems = new ArrayList<>();

    // Add item with drop chance
    public void addItem(ItemStack item, double chance) {
        lootItems.add(new ItemDrop(item, chance));
    }

    // Drop loot
    public List<ItemStack> rollLoot() {
        List<ItemStack> dropItems = new ArrayList<>();
        Random rand = new Random();

        for (ItemDrop lootItem : lootItems) {
            if (rand.nextDouble() <= lootItem.getDropChance()) {
                dropItems.add(lootItem.getItem());
            }
        }

        return dropItems;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(ItemDrop item : lootItems) {
            sb.append(item.toString()).append(", ");
        }
        return sb.toString();
    }
}
