package com.draconincdomain.custommobs.core.RPGMobs;

import com.draconincdomain.custommobs.CustomMobsControl;
import com.draconincdomain.custommobs.core.Boss.LootTable;
import com.draconincdomain.custommobs.core.enums.LoggerLevel;
import com.draconincdomain.custommobs.utils.Data.SerializableItemStack;
import com.draconincdomain.custommobs.utils.Desing.ColourCode;
import com.draconincdomain.custommobs.utils.Arrays.CustomEntityArrayHandler;
import com.draconincdomain.custommobs.utils.Handlers.Region;
import com.draconincdomain.custommobs.utils.Handlers.RegionManager;
import com.draconincdomain.custommobs.utils.ItemDrop;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * Custom mob object
 * Constructs the custom mob via the provided data and then used to manage custom mob
 */
public class CustomMob implements Serializable {
    private String name;
    private String mobNameID;
    private boolean champion;
    private int level;
    private double maxHealth;
    private int spawnChance;
    private transient EntityType entityType;
    private String entityTypeName;
    private SerializableItemStack weapon;
    private Double weaponDropChance;
    private SerializableItemStack[] armour;
    private int mobID;
    private transient Entity entity;
    private UUID entityUUID;
    private transient Region region;
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
        this.weapon = weapon != null? new SerializableItemStack(weapon) : null;
        this.weaponDropChance = weaponDropChance;
        this.armour = fromItemStackArray(armour);
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
        } else {
            StringBuilder lootContent = new StringBuilder();
            for (ItemDrop lootDrop : this.getLootDrops()) {
                ItemStack itemStack = lootDrop.getItem();
                double chance = lootDrop.getDropChance();
                lootContent.append("[").append(itemStack.getType().toString()).append(" x").append(itemStack.getAmount())
                        .append(", Chance: ").append(chance).append("], ");
            }
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

        Region region = RegionManager.getInstance().getRegionFromLocation(location);
        setRegion(region);

        // Set entity and add to custom entities
        setEntity(entity);
        CustomEntityArrayHandler.getCustomEntities().put(getEntity(), this);
    }

    private void handleEquipment(LivingEntity entity) {
        EntityEquipment entityEquipment = entity.getEquipment();

        ItemStack[] actualArmour = getArmour();
        if (actualArmour != null) {
            entityEquipment.setArmorContents(actualArmour);
            entityEquipment.setHelmetDropChance(0f);
            entityEquipment.setChestplateDropChance(0f);
            entityEquipment.setLeggingsDropChance(0f);
            entityEquipment.setBootsDropChance(0f);
        }

        ItemStack actualWeapon = getWeapon();
        if (actualWeapon != null) {
            entityEquipment.setItemInMainHand(actualWeapon);
            entityEquipment.setItemInMainHandDropChance((float) (double) getWeaponDropChance());
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
        if (this.entityType == null && this.entityTypeName != null) {
            this.entityType = EntityType.valueOf(this.entityTypeName);
        }
        return this.entityType;
    }
    public ItemStack getWeapon() {
        return weapon != null ? weapon.toItemStack() : null;
    }

    public double getWeaponDropChance() {
        return weaponDropChance != null ? weaponDropChance : 0;
    }

    public ItemStack[] getArmour() {
        if (armour == null) {
            return null;
        }

        ItemStack[] rArmour = new ItemStack[armour.length];
        for (int i = 0; i < armour.length; i++) {
            SerializableItemStack stack = armour[i];
            rArmour[i] = stack != null ? stack.toItemStack() : null;
        }
        return rArmour;
    }

    public int getMobID() {
        return mobID;
    }

    public Entity getEntity() {
        if (this.entity == null && this.entityUUID != null) {
            for (World world : Bukkit.getWorlds()) {
                for (Entity entity : world.getEntities()) {
                    if (entity.getUniqueId().equals(this.entityUUID)) {
                        this.entity = entity;
                        break;
                    }
                }
            }
        }
        return this.entity;
    }
    public Region getRegion() {
        return this.region;
    }
    public ItemDrop[] getLootDrops() {
        return lootDrops;
    }
    public void setLootTable(LootTable loot){
        this.lootTable = loot;
    }
    public void setRegion(Region region) {
        this.region = region;
    }

    public LootTable getLootTable(){
        return this.lootTable;
    }

    private SerializableItemStack[] fromItemStackArray(ItemStack[] armour) {
        if (armour == null) {
            return null;
        }

        SerializableItemStack[] sArmour = new SerializableItemStack[armour.length];
        for (int i = 0; i < armour.length; i++) {
            ItemStack stack = armour[i];
            sArmour[i] = stack != null ? new SerializableItemStack(stack) : null;
        }
        return sArmour;
    }
}
