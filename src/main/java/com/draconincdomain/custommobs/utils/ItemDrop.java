package com.draconincdomain.custommobs.utils;

import com.draconincdomain.custommobs.utils.Data.SerializableItemStack;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;

public class ItemDrop implements Serializable {
    private final SerializableItemStack item;
    private final double dropChance;

    public ItemDrop(ItemStack item, double dropChance) {
        this.item = item != null ? new SerializableItemStack(item) : null;
        this.dropChance = dropChance;
    }

    public ItemStack getItem() {
        return item != null ? this.item.toItemStack() : null;
    }

    public double getDropChance() {
        return dropChance;
    }
}
