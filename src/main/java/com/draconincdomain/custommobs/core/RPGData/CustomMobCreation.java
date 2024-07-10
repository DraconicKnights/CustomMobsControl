package com.draconincdomain.custommobs.core.RPGData;

import com.draconincdomain.custommobs.core.RPGMobs.CustomMob;
import com.draconincdomain.custommobs.utils.Arrays.CustomEntityArrayHandler;
import com.draconincdomain.custommobs.utils.ItemBuilder;
import com.draconincdomain.custommobs.utils.ItemDrop;
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
        ItemDrop[] lootDrops = convertToItemDropArray(mobMap.get("lootDrops"));

        Material weaponMaterial = null;
        int weaponAmount = 0;
        Map<Enchantment, Integer> enchantmentMap = null;
        boolean glow = false;
        boolean unbreakable = false;
        boolean hide = false;
        String weaponName = null;
        String weaponLore = null;
        Double weaponDropChance = null;

        // Extract weapon properties
        if(mobMap.containsKey("weapon")) {
            Map<?, ?> weaponMap = (Map<?, ?>) mobMap.get("weapon");
            weaponMaterial = Material.valueOf((String) weaponMap.get("material"));
            weaponAmount = (int) weaponMap.get("amount");
            enchantmentMap = extractEnchantments(weaponMap);
            glow = (boolean) weaponMap.get("glow");
            unbreakable = (boolean) weaponMap.get("unbreakable");
            hide = (boolean) weaponMap.get("hide");
            weaponName = (String) weaponMap.get("weaponName");
            weaponLore = (String) weaponMap.get("weaponLore");

            if(weaponMap.containsKey("weaponDropChance")){
                weaponDropChance = (Double) weaponMap.get("weaponDropChance");
            }
        }

        boolean hasArmour = (boolean) mobMap.get("hasArmour");
        List<ItemStack> armour = getArmourItems(mobMap, hasArmour);

        int mobID = CustomEntityArrayHandler.getRegisteredCustomMobs().size();
        ItemStack weapon = null;

        if(weaponMaterial != null && enchantmentMap != null) {
            weapon = ItemBuilder.createEnchantItem(weaponMaterial, weaponAmount, enchantmentMap, glow, unbreakable, hide, weaponName, weaponLore);
        }
        // Create the CustomMob instance
        return new CustomMob(name, mobNameID, isChampion, maxHealth, spawnChance, entityType, weapon, weaponDropChance, ItemBuilder.makeArmourSet(armour), mobID, lootDrops);
    }

    private static Map<Enchantment, Integer> extractEnchantments(Map<?, ?> weaponMap) {
        Map<Enchantment, Integer> enchantmentMap = new HashMap<>();
        if ((boolean) weaponMap.get("enchanted")) {
            List<String> enchantmentNames = (List<String>) weaponMap.get("enchantments"); // Read enchantment names as strings
            List<Integer> enchantmentLevels = (List<Integer>) weaponMap.get("enchantmentLevels"); // Reads the Enchantment Levels and streams them to int[] Array

            for (int i = 0; i < enchantmentNames.size(); i++) {
                Enchantment enchantment = Enchantment.getByName(enchantmentNames.get(i));

                if (enchantment != null) {
                    enchantmentMap.put(enchantment, enchantmentLevels.get(i));
                }
            }
        }
        return enchantmentMap;
    }

    private static List<ItemStack> getArmourItems(Map<?, ?> mobMap, boolean hasArmour) {
        List<ItemStack> armour = new ArrayList<>();

        if (hasArmour) {
            List<String> armourNames = (List<String>) mobMap.get("armour");

            for (String armourName : armourNames) {
                Material material = Material.matchMaterial(armourName);

                if (material != null) {
                    ItemStack itemStack = new ItemStack(material, 1);
                    armour.add(itemStack);
                }
            }
        }
        return armour;
    }

    private static ItemDrop[] convertToItemDropArray(Object obj) {
        // Assume obj is an instance of List of Map representing an item and drop chance
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

    public static Map<String, Object> toMap(CustomMob mob) {
        Map<String, Object> mobMap = new HashMap<>();
        mobMap.put("name", mob.getName());
        mobMap.put("mobID", mob.getMobNameID());
        mobMap.put("champion", mob.getChampion());
        mobMap.put("maxHealth", mob.getMaxHealth());
        mobMap.put("spawnChance", mob.getSpawnChance());
        mobMap.put("entityType", mob.getEntityType().name());
        // Add other properties
        return mobMap;
    }
}