package com.draconincdomain.custommobs.core.Inventory;

import com.draconincdomain.custommobs.core.CustomMob;
import com.draconincdomain.custommobs.utils.CustomEntityArrayHandler;
import com.draconincdomain.custommobs.utils.ItemBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class CustomMobsGUI {

    private static Inventory gui;

    public void BuildInventory() {
        Inventory inventory = Bukkit.createInventory(null, 9 * 6, Component.text(ChatColor.AQUA + "Example"));

        CustomMob[] customMobs = CustomEntityArrayHandler.getRegisteredCustomMobs().values().toArray(new CustomMob[0]);

        for (CustomMob customMob : customMobs) {

            ItemStack itemStack = new ItemStack(Material.LEGACY_MONSTER_EGG, 1, customMob.getEntityType().getTypeId());
            ItemMeta itemMeta = itemStack.getItemMeta();

            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            itemMeta.addEnchant(Enchantment.DURABILITY, 1, true);

            itemMeta.setDisplayName(customMob.getName());

            List<String> lore = new ArrayList<>();
            lore.add(customMob.getName());
            lore.add(customMob.getEntityType().toString());
            lore.add(customMob.getEntity().toString());
            lore.add(customMob.getWeapon().toString());
            lore.add(String.valueOf(customMob.getSpawnChance()));

            itemMeta.setLore(lore);
            itemStack.setItemMeta(itemMeta);

        }

        SetServerGUI(inventory);

    }

    public void SetServerGUI(Inventory inventory) {
        gui = inventory;
    }

    public static Inventory GetServerGUI() {
        return gui;
    }

    public static void OpenServerGUI(Player player) {
        player.openInventory(gui);
    }

}
