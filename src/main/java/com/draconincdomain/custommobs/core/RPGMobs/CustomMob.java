package com.draconincdomain.custommobs.core.RPGMobs;

import com.draconincdomain.custommobs.CustomMobsControl;
import com.draconincdomain.custommobs.core.Boss.LootTable;
import com.draconincdomain.custommobs.core.enums.LoggerLevel;
import com.draconincdomain.custommobs.utils.Desing.ColourCode;
import com.draconincdomain.custommobs.utils.Arrays.CustomEntityArrayHandler;
import com.draconincdomain.custommobs.utils.ItemDrop;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Custom mob object
 * Constructs the custom mob via the provided data and then used to manage custom mob
 */
public class CustomMob {
    private String name;
    private String mobNameID;
    private boolean champion;
    private int level;
    private double maxHealth;
    private int spawnChance;
    private EntityType entityType;
    private ItemStack weapon;
    private Double weaponDropChance;
    private ItemStack[] armour;
    private int mobID;
    private Entity entity;
    private double baseHealth;
    private final ItemDrop[] lootDrops;
    private LootTable lootTable;

    public CustomMob(String name, String mobNameID, boolean champion, double maxHealth, int spawnChance, EntityType entityType, ItemStack weapon, Double weaponDropChance, ItemStack[] armour, int mobID, ItemDrop[] lootDrops) {
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
        this.lootDrops = lootDrops;
    }

    public void spawnEntity(Location location, int level) {
        LivingEntity entity = (LivingEntity) location.getWorld().spawnEntity(location, entityType);

        this.level = level;
        levelScale(level);

        LootTable loot = new LootTable();
        // Check array is not null and length > 0
        if (this.getLootDrops() != null && this.getLootDrops().length > 0) {
            for (ItemDrop itemDrop : this.getLootDrops()) {
                loot.addItem(itemDrop.getItem(), itemDrop.getDropChance());
            }
        } else {
            CustomMobsControl.getInstance().CustomMobLogger("No loot drops items found", LoggerLevel.INFO);  // Just for debug
        }

        this.setLootTable(loot);

        if (this.getLootTable() == null) {
            CustomMobsControl.getInstance().CustomMobLogger("Boss mob's loot table is empty", LoggerLevel.INFO);
        } else {
            StringBuilder lootContent = new StringBuilder();
            for (ItemDrop lootDrop : this.getLootDrops()) {
                ItemStack itemStack = lootDrop.getItem();
                double chance = lootDrop.getDropChance();
                lootContent.append("[").append(itemStack.getType().toString()).append(" x").append(itemStack.getAmount())
                        .append(", Chance: ").append(chance).append("], ");
            }
            CustomMobsControl.getInstance().CustomMobLogger("Boss Mob loot content: " + lootContent.toString(), LoggerLevel.INFO);
        }

        // Set custom name
        String customName = String.format("&7[&3%s&r&7] %s &r&c%d/%d", level, name, (int) maxHealth, (int) maxHealth);
        entity.setCustomName(ColourCode.colour(customName));
        entity.setCustomNameVisible(true);

        // Set health
        entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth);
        entity.setHealth(maxHealth);

        // Set equipment and drop chances
        handleEquipment(entity);

        if (entity instanceof Zombie || entity instanceof Skeleton) {
            entity.setFireTicks(0);
        }

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
            if (weaponDropChance != null) { // check for null before using weaponDropChance
                entityEquipment.setItemInMainHandDropChance((float) (double) weaponDropChance);
            }
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

    public double getHealth() {
        return ((LivingEntity)getEntity()).getHealth();
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
        return weaponDropChance != null ? weaponDropChance : 0;
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

    public ItemDrop[] getLootDrops() {
        return lootDrops;
    }
    public void setLootTable(LootTable loot){
        this.lootTable = loot;
    }

    public LootTable getLootTable(){
        return this.lootTable;
    }

}
