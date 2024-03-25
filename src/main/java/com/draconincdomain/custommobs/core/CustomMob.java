package com.draconincdomain.custommobs.core;

import com.draconincdomain.custommobs.utils.Desing.ColourCode;
import com.draconincdomain.custommobs.utils.Arrays.CustomEntityArrayHandler;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


public class CustomMob {
    private String name;
    private String mobNameID;
    private boolean champion;
    private int level;
    private double maxHealth;
    private int spawnChance;
    private EntityType entityType;
    private ItemStack weapon;
    private double weaponDropChance;
    private ItemStack[] armour;
    private boolean potionEnabled;
    private int mobID;
    private Entity entity;

    public CustomMob(String name, String mobNameID, boolean champion, double maxHealth, int spawnChance, EntityType entityType, ItemStack weapon, double weaponDropChance, ItemStack[] armour, boolean potionEnabled, int mobID) {
        this.name = name;
        this.mobNameID = mobNameID;
        this.champion = champion;
        this.maxHealth = maxHealth;
        this.spawnChance = spawnChance;
        this.entityType = entityType;
        this.weapon = weapon;
        this.weaponDropChance = weaponDropChance;
        this.armour = armour;
        this.potionEnabled = potionEnabled;
        this.mobID = mobID;
    }

    public void spawnEntity(Location location) {
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

        setEntity(entity);
        CustomEntityArrayHandler.getCustomEntities().put(getEntity(), this);
    }

    private void setEntity(Entity entity) {
        this.entity = entity;
    }

    public String getName() {
        return name;
    }

    public String getMobNameID() {
        return mobNameID;
    }

    public boolean getChampion() {
        return champion;
    }

    public int getLevel() {
        return level;
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

    public int getMobID() {
        return mobID;
    }

    public Entity getEntity() {
        return entity;
    }

}
