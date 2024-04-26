package com.draconincdomain.custommobs.core.Boss;

import org.bukkit.inventory.ItemStack;

import java.util.*;

public class LootTable {

    // This map stores each ItemStack along with its probability (out of 100)
    private final Map<ItemStack, Integer> items = new HashMap<>();

    // Add item with drop chance
    public void addItem(ItemStack item, int chance){
        items.put(item, chance);
    }

    // Drop loot
    public List<ItemStack> rollLoot(){
        List<ItemStack> drops = new ArrayList<>();
        items.forEach((item, chance) -> {
            if(new Random().nextInt(100) < chance){
                drops.add(item);
            }
        });
        return drops;
    }
}
