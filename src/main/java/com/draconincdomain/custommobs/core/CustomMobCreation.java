package com.draconincdomain.custommobs.core;

import com.draconincdomain.custommobs.utils.CustomEntityArrayHandler;
import com.draconincdomain.custommobs.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class CustomMobCreation {

    public static CustomMob fromMap(Map<?, ?> mobMap) {

        String name = (String) mobMap.get("name");
        String mobNameID = (String) mobMap.get("mobID");
        boolean isChampion = (boolean) mobMap.get("champion");
        double maxHealth = (double) mobMap.get("maxHealth");
        int spawnChance = (int) mobMap.get("spawnChance");
        EntityType entityType = EntityType.valueOf((String) mobMap.get("entityType"));

        // Extract weapon properties
        Map<?, ?> weaponMap = (Map<?, ?>) mobMap.get("weapon");

        Material weaponMaterial = Material.valueOf((String) weaponMap.get("material"));
        int weaponAmount = (int) weaponMap.get("amount");

        boolean enchanted = (boolean) weaponMap.get("enchanted");

        Map<Enchantment, Integer> enchantmentMap = new HashMap<>();

        if (enchanted) {
            List<String> enchantmentNames = (List<String>) weaponMap.get("enchantments"); // Read enchantment names as strings
            List<Integer> enchantmentLevels = (List<Integer>) weaponMap.get("enchantmentLevels"); // Reads the Enchantment Levels and streams them to int[] Array

            List<Enchantment> enchantments = new ArrayList<>();

//        int[] levelArray = enchantmentLevels.stream().mapToInt(Integer::intValue).toArray();
//        List<Integer> levelist = Arrays.stream(levelArray).boxed().collect(Collectors.toList());

            for (String enchantmentName : enchantmentNames) {
                Enchantment enchantment = Enchantment.getByName(enchantmentName);

                if (enchantment != null) {
                    int level = enchantmentLevels.get(enchantments.size());
                    enchantmentMap.put(enchantment, level);
                    enchantments.add(enchantment);
                }
            }
        }

        boolean glow = (boolean) weaponMap.get("glow");
        boolean unbreakable = (boolean) weaponMap.get("unbreakable");
        boolean hide = (boolean) weaponMap.get("hide");
        String weaponName = (String) weaponMap.get("weaponName");
        String weaponLore = (String) weaponMap.get("weaponLore");

        double weaponDropChance = (double) mobMap.get("weaponDropChance");

        boolean hasArmour = (boolean) mobMap.get("hasArmour");

        List<ItemStack> armour = new ArrayList<>();

        if (hasArmour) {
            List<String> armourNames = (List<String>) mobMap.get("armour");

            for (String armourName : armourNames) {
                Material material = Material.matchMaterial(armourName);

                if (material == null) continue;

                ItemStack itemStack = new ItemStack(material, 1);

                armour.add(itemStack);
            }
        }

        boolean potionEnabled = (boolean) mobMap.get("potionEnabled");

        int mobID = CustomEntityArrayHandler.getRegisteredCustomMobs().size();

        // Create the CustomMob instance

        return new CustomMob(name, mobNameID, isChampion, maxHealth, spawnChance, entityType,
                ItemBuilder.createEnchantItem(weaponMaterial, weaponAmount, enchantmentMap, glow, unbreakable, hide, weaponName, weaponLore), weaponDropChance,
                ItemBuilder.makeArmourSet(armour), potionEnabled, mobID);
    }

}
