package com.draconincdomain.custommobs.utils;

import com.draconincdomain.custommobs.CustomMobsControl;
import com.draconincdomain.custommobs.core.enums.LoggerLevel;
import com.draconincdomain.custommobs.utils.Desing.ColourCode;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

/**
 * Item Builder class used for creating custom item data for other parts of the project
 */
public class ItemBuilder {

    public static ItemStack CreateCustomItem(Material material, boolean glow, String displayName, String metaContent) {

        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (glow) {
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            itemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
        }

        itemMeta.setDisplayName(displayName);

        List<String> lore = new ArrayList<>();
        lore.add(metaContent);

        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public static ItemStack createEnchantItem(Material type, int amount, Map<Enchantment, Integer> enchantmentMap, boolean glow, boolean unbreakable, boolean hideunbreakable, String name, String... lines) {
        ItemStack item = new ItemStack(type, amount);
        ItemMeta meta = item.getItemMeta();

        if (glow) {
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.addEnchant(Enchantment.DURABILITY, 1, true);
        }

        if (enchantmentMap != null) {
            for (Map.Entry<Enchantment, Integer> entry : enchantmentMap.entrySet()) {
                CustomMobsControl.getInstance().CustomMobLogger(entry.getKey().toString() + entry.getValue().toString(), LoggerLevel.INFO);
                meta.addEnchant(entry.getKey(), entry.getValue(), true);
            }
        }

        if (unbreakable) meta.setUnbreakable(true);
        if (hideunbreakable) meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        if (name != null) meta.displayName(Component.translatable(ColourCode.colour(name)));

        if (lines != null) {
            List<String> lore = new ArrayList<>();
            for (String line : lines) {
                lore.add(ColourCode.colour(line));
            }
            meta.setLore(lore);

        }
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack[] makeArmourSet(List<ItemStack> armour) {

        if (armour == null || armour.isEmpty()) return null;

        CustomMobsControl.getInstance().CustomMobLogger("Armour Contents: " + armour, LoggerLevel.INFO);

        ItemStack[] armourArray = new ItemStack[armour.size()];

        for (int i = 0; i < armour.size(); i++) {
            armourArray[i] = armour.get(i);
        }

        return armourArray;
    }
}
