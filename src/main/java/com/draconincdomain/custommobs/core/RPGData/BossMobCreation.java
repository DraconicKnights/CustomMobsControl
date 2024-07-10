package com.draconincdomain.custommobs.core.RPGData;

import com.draconincdomain.custommobs.core.RPGMobs.BossMob;
import com.draconincdomain.custommobs.core.RPGMobs.CustomMob;
import com.draconincdomain.custommobs.utils.ItemDrop;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class BossMobCreation {
    public static BossMob fromMap(Map<?, ?> bossMobMap) {

        // Convert abilities, attacks and lootDrops from objects to Strings.
        String[] abilities = convertToArrString(bossMobMap.get("abilities"));
        int[] potionValues = convertToIntArray(bossMobMap.get("abilityLevels"));
        String[] attacks = convertToArrString(bossMobMap.get("attacks"));
        // Convert lootDrops from objects to ItemDrop[]
        ItemDrop[] lootDrops = convertToItemDropArray(bossMobMap.get("lootDrops"));

        // Create CustomMob instance first
        CustomMob customMob = CustomMobCreation.fromMap(bossMobMap);


        // Create BossMob instance
        return new BossMob(customMob.getName(), customMob.getMobNameID(), customMob.getChampion(), customMob.getMaxHealth(),
                customMob.getSpawnChance(), customMob.getEntityType(), customMob.getWeapon(), customMob.getWeaponDropChance(),
                customMob.getArmour(), customMob.getMobID(), abilities, potionValues, attacks, lootDrops);
    }

    private static String[] convertToArrString(Object obj) {
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

    private static int[] convertToIntArray(Object obj) {
        if(obj instanceof List<?>) {
            List<?> list = (List<?>) obj;
            return list.stream()
                    .filter(item -> item instanceof Integer)
                    .mapToInt(item -> (Integer) item)
                    .toArray();
        }

        // Return an empty array if obj is not a List instance
        return new int[0];
    }

    private static ItemDrop[] convertToItemDropArray(Object obj) {
        if(obj instanceof List<?>) {
            List<?> list = (List<?>) obj;
            return list.stream()
                    .filter(item -> item instanceof Map<?, ?>)
                    .map(item -> {
                        Map<?, ?> itemMap = (Map<?, ?>) item;
                        String materialName = (String) itemMap.get("item");
                        int count = ((Number)itemMap.get("count")).intValue();  // Safe conversion from generic Number to int
                        double dropChance = ((Number)itemMap.get("dropChance")).doubleValue();  // Safe conversion from generic Number to double

                        Material material = Material.getMaterial(materialName);
                        return material != null ? new ItemDrop(new ItemStack(material, count), dropChance) : null;
                    })
                    .filter(Objects::nonNull)
                    .toArray(ItemDrop[]::new);
        }

        // Return an empty array if obj is not a List instance
        return new ItemDrop[0];
    }
}
