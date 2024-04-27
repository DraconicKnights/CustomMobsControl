package com.draconincdomain.custommobs.utils;

import org.bukkit.inventory.ItemStack;

public class ItemDrop {
    private final ItemStack item;
    private final double dropChance;

    public ItemDrop(ItemStack item, double dropChance) {
        this.item = item;
        this.dropChance = dropChance;
    }

    public ItemStack getItem() {
        return item;
    }

    public double getDropChance() {
        return dropChance;
    }
}
