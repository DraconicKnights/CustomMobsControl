package com.draconincdomain.custommobs.utils.Data;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;

public class SerializableItemStack implements Serializable {
    private final Material type;
    private final int amount;

    public SerializableItemStack(ItemStack itemStack) {
        this.type = itemStack.getType();
        this.amount = itemStack.getAmount();
        // Initialize other members as needed
    }

    public ItemStack toItemStack() {
        // Recreate the ItemStack using the saved data
        return new ItemStack(this.type, this.amount);
    }
}
