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
    private int mobID;
    private Entity entity;

    private double baseHealth;

    public CustomMob(String name, String mobNameID, boolean champion, double maxHealth, int spawnChance, EntityType entityType, ItemStack weapon, double weaponDropChance, ItemStack[] armour, int mobID) {
        this.name = name;
        this.mobNameID = mobNameID;
        this.champion = champion;
        this.maxHealth = maxHealth;
        this.spawnChance = spawnChance;
        this.entityType = entityType;
        this.weapon = weapon;
        this.weaponDropChance = weaponDropChance;
        this.armour = armour;
        this.mobID = mobID;
        this.baseHealth = maxHealth;
    }

    public void spawnEntity(Location location, int level) {
        LivingEntity entity = (LivingEntity) location.getWorld().spawnEntity(location, entityType);

        levelScale(level);

        // Set custom name
        String customName = String.format("&7[&3%s&r&7] %s &r&c%d/%d", level, name, (int) maxHealth, (int) maxHealth);
        entity.setCustomName(ColourCode.colour(customName));
        entity.setCustomNameVisible(true);

        // Set health
        entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth);
        entity.setHealth(maxHealth);

        // Set equipment and drop chances
        handleEquipment(entity);

        // Set entity and add to custom entities
        setEntity(entity);
        CustomEntityArrayHandler.getCustomEntities().put(getEntity(), this);
    }

    private void handleEquipment(LivingEntity entity) {
        EntityEquipment entityEquipment = entity.getEquipment();

        if (armour != null) {
            entityEquipment.setArmorContents(armour);
            entityEquipment.setHelmetDropChance(0f);
            entityEquipment.setChestplateDropChance(0f);
            entityEquipment.setLeggingsDropChance(0f);
            entityEquipment.setBootsDropChance(0f);
        }

        if (weapon != null) {
            entityEquipment.setItemInMainHand(weapon);
            entityEquipment.setItemInMainHandDropChance((float) weaponDropChance);
        }
    }

    private void levelScale(int level) {
        this.maxHealth = this.baseHealth + (level * 1.5);
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

    public int getMobID() {
        return mobID;
    }

    public Entity getEntity() {
        return entity;
    }

}
