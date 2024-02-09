package com.draconincdomain.custommobs.core;

import com.draconincdomain.custommobs.core.CustomMob;
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
        double maxHealth = (double) mobMap.get("maxHealth");
        int spawnChance = (int) mobMap.get("spawnChance");
        EntityType entityType = EntityType.valueOf((String) mobMap.get("entityType"));

        // Extract weapon properties
        Map<?, ?> weaponMap = (Map<?, ?>) mobMap.get("weapon");

        Material weaponMaterial = Material.valueOf((String) weaponMap.get("material"));
        int weaponAmount = (int) weaponMap.get("amount");
        List<String> enchantmentNames = (List<String>) weaponMap.get("enchantments"); // Read enchantment names as strings
        List<Integer> enchantmentLevels = (List<Integer>) weaponMap.get("enchantmentLevels"); // Reads the Enchantment Levels and streams them to int[] Array

        Map<Enchantment, Integer> enchantmentMap = new HashMap<>();

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


        boolean glow = (boolean) weaponMap.get("glow");
        boolean unbreakable = (boolean) weaponMap.get("unbreakable");
        boolean hide = (boolean) weaponMap.get("hide");
        String weaponName = (String) weaponMap.get("weaponName");
        String weaponLore = (String) weaponMap.get("weaponLore");

        double weaponDropChance = (double) mobMap.get("weaponDropChance");

        // Assuming 'armour' is stored as a list of ItemStacks
        List<?> armourList = (List<?>) mobMap.get("armour");
        ItemStack[] armour = new ItemStack[armourList.size()];
        for (int i = 0; i < armourList.size(); i++) {
            Map<?, ?> armourItemMap = (Map<?, ?>) armourList.get(i);
            Material armourMaterial = Material.valueOf((String) armourItemMap.get("material"));
            armour[i] = new ItemStack(armourMaterial);
        }

        boolean potionEnabled = (boolean) mobMap.get("potionEnabled");

        int mobID = CustomEntityArrayHandler.getRegisteredCustomMobs().size();

        // Create the CustomMob instance

        return new CustomMob(name, maxHealth, spawnChance, entityType,
                ItemBuilder.createEnchantItem(weaponMaterial, weaponAmount, enchantmentMap, glow, unbreakable, hide, weaponName, weaponLore), weaponDropChance, armour, potionEnabled, mobID);
    }

}
