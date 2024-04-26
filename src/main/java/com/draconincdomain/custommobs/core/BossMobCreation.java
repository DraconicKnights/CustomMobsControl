package com.draconincdomain.custommobs.core;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class BossMobCreation {
    public static BossMob fromMap(Map<?, ?> bossMobMap) {

        // This would be similar to CustomMobCreation#FromMap but this time you extract boss-specific information.

        // Convert abilities, attacks and lootDrops from objects to Strings.
        String[] abilities = convertToArrString(bossMobMap.get("abilities"));
        String[] attacks = convertToArrString(bossMobMap.get("attacks"));
        ItemStack[] lootDrops = convertToItemStackArray(bossMobMap.get("lootDrops"));

        // Create CustomMob instance first
        CustomMob customMob = CustomMobCreation.fromMap(bossMobMap);

        // Create BossMob instance
        return new BossMob(customMob.getName(), customMob.getMobNameID(), customMob.getChampion(), customMob.getMaxHealth(),
                customMob.getSpawnChance(), customMob.getEntityType(), customMob.getWeapon(), customMob.getWeaponDropChance(),
                customMob.getArmour(), customMob.getPotionEnabled(), customMob.getMobID(), abilities, attacks, lootDrops);
    }

    // Helper method to convert objects to String[]
    private static String[] convertToArrString(Object obj) {
        // Assume obj is an instance of List of String
        if(obj instanceof List<?>) {
            List<?> list = (List<?>) obj;
            return list.stream()
                    .filter(item -> item instanceof String)
                    .map(item -> (String) item)
                    .toArray(String[]::new);
        }

        // Return an empty array if obj is not a List instance
        return new String[0];
    }

    // Helper method to convert objects to ItemStack[]
    private static ItemStack[] convertToItemStackArray(Object obj) {

        // Assume obj is an instance of List of String representing Material names
        if(obj instanceof List<?>) {
            List<?> list = (List<?>) obj;
            return list.stream()
                    .filter(item -> item instanceof String)
                    .map(item -> {
                        Material material = Material.getMaterial((String) item);
                        return material != null ? new ItemStack(material) : null;
                    })
                    .filter(Objects::nonNull)
                    .toArray(ItemStack[]::new);
        }

        // Return an empty array if obj is not a List instance
        return new ItemStack[0];
    }
}
