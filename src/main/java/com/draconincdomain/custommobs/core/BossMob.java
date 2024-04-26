package com.draconincdomain.custommobs.core;

import com.draconincdomain.custommobs.core.Boss.GameEventScheduler;
import com.draconincdomain.custommobs.core.Boss.LootTable;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BossMob extends CustomMob {
    private String[] abilities; // Array of special abilities
    private String[] attacks; // Array of special attacks
    private ItemStack[] lootDrops; // Array of loot drops

    private LootTable lootTable;


    public BossMob(String name, String mobNameID, boolean champion, double maxHealth, int spawnChance, EntityType entityType, ItemStack weapon, double weaponDropChance, ItemStack[] armour, boolean potionEnabled, int mobID, String[] abilities, String[] attacks, ItemStack[] lootDrops) {
        super(name, mobNameID, champion, maxHealth, spawnChance, entityType, weapon, weaponDropChance, armour, potionEnabled, mobID);
        this.abilities = abilities;
        this.attacks = attacks;
        this.lootDrops = lootDrops;
    }

    // You can override the spawnEntity method here to incorporate the abilities and attacks for the boss

    @Override
    public void spawnEntity(Location location) {
        super.spawnEntity(location);

        // Apply abilities (as potion effects in this case)
        if (this.getEntity() instanceof LivingEntity) { // Ensure the entity is of type LivingEntity
            LivingEntity livingEntity = (LivingEntity) this.getEntity();
            for (String ability : this.getAbilities()) {
                PotionEffectType effectType = PotionEffectType.getByName(ability);
                if (effectType != null) {
                    livingEntity.addPotionEffect(new PotionEffect(effectType, 10000, 1, false, false));
                }
            }

            // Schedule attacks
            for (String attack : this.getAttacks()) {
                // Schedule these attacks
                GameEventScheduler.scheduleSpecialAttack(livingEntity, attack);
            }

            LootTable loot = new LootTable();
            for (ItemStack item : this.getLootDrops()) {
                loot.addItem(item, 5);
            }

            this.setLootTable(loot);
        }
    }

    public String[] getAbilities() {
        return abilities;
    }

    public String[] getAttacks() {
        return attacks;
    }

    public ItemStack[] getLootDrops() {
        return lootDrops;
    }

    public void setLootTable(LootTable loot){
        this.lootTable = loot;
    }

    public LootTable getLootTable(){
        return this.lootTable;
    }
}
