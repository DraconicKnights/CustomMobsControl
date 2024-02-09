package com.draconincdomain.custommobs.core;

import com.draconincdomain.custommobs.utils.ColourCode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


public class CustomMob {

    /*SKELETON_FLAMER("&6Skeleton Flame Archer", 20, 45, EntityType.SKELETON,
            ItemBuilder.createEnchantItem(Material.BOW, 1,
                    new Enchantment[]{Enchantment.ARROW_FIRE, Enchantment.ARROW_DAMAGE},
                    new int[] {1, 3},
                    true,true, false,
                    "&4 Example", "Lore example"),
            20f, null, null, 0),

    WITHER_SCOUT("&6Wither Scout", 30, 35, EntityType.WITHER_SKELETON,
            ItemBuilder.createEnchantItem(Material.DIAMOND_AXE, 1,
                    new Enchantment[]{Enchantment.KNOCKBACK, Enchantment.DAMAGE_UNDEAD},
                    new int[] {2, 1},
                    true, true, false,
                    "&3 Ancient Axe", "lore examole"),
            12f, null, PotionEffectType.SPEED, 4),

    ZOMBIE_SCOUT("&6Zombie Scout", 18, 55, EntityType.ZOMBIE,
                   ItemBuilder.createEnchantItem(Material.IRON_AXE, 1,
                           new Enchantment[]{Enchantment.KNOCKBACK, Enchantment.DAMAGE_ALL},
            new int[] {1, 2},
            true, true, false,
            "&3 Lost Axe", "lore examole"),
            35f, null,null, 0),

    ZOMBIE_BRUTE("&6Zombie Brute", 40, 25, EntityType.ZOMBIE,
                 ItemBuilder.createEnchantItem(Material.IRON_SWORD, 1,
                         new Enchantment[]{Enchantment.DAMAGE_ALL, Enchantment.FIRE_ASPECT},
            new int[] {2, 1},
            true, true, false,
            "&3 Forged Dagger", "lore examole"),
            28f, ItemBuilder.makeArmourSet(new ItemStack(Material.DIAMOND_CHESTPLATE), null, null, null),
            null, 0)

    ;*/
    private String name;
    private int level;
    private double maxHealth;
    private int spawnChance;
    private EntityType entityType;
    private ItemStack weapon;
    private double weaponDropChance;
    private ItemStack[] armour;
    private boolean potionEnabled;

    public CustomMob(String name, double maxHealth, int spawnChance, EntityType entityType, ItemStack weapon, double weaponDropChance, ItemStack[] armour, boolean potionEnabled) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.spawnChance = spawnChance;
        this.entityType = entityType;
        this.weapon = weapon;
        this.weaponDropChance = weaponDropChance;
        this.armour = armour;
        this.potionEnabled = potionEnabled;
    }

    public LivingEntity spawnEntity(Location location) {
        LivingEntity entity = (LivingEntity) location.getWorld().spawnEntity(location, entityType);
        entity.setCustomNameVisible(true);
        entity.setCustomName(ColourCode.colour("&7[&3" + level + "&r&7] " + name + " &r&c" + (int) maxHealth + "/" + (int) maxHealth));
        entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth);
        entity.setHealth(maxHealth);

        EntityEquipment entityEquipment = entity.getEquipment();
        if (armour != null) entityEquipment.setArmorContents(armour);
        entityEquipment.setHelmetDropChance(0f);
        entityEquipment.setChestplateDropChance(0f);
        entityEquipment.setLeggingsDropChance(0f);
        entityEquipment.setBootsDropChance(0f);
        if (weapon != null) entityEquipment.setItemInMainHand(weapon);
        entityEquipment.setItemInMainHandDropChance((float) weaponDropChance);

        if (potionEnabled) entity.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 1500, 10));

        return entity;
    }

    public String getName() {
        return name;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public int getSpawnChance() {
        return spawnChance;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public ItemStack getWeapon() {
        return weapon;
    }

    public double getWeaponDropChance() {
        return weaponDropChance;
    }

    public ItemStack[] getArmour() {
        return armour;
    }

    public boolean getPotionEnabled() {
        return potionEnabled;
    }

}
