package com.draconincdomain.custommobs.core;

import com.draconincdomain.custommobs.CustomMobsControl;
import com.draconincdomain.custommobs.core.Boss.GameEventScheduler;
import com.draconincdomain.custommobs.core.Boss.LootTable;
import com.draconincdomain.custommobs.core.enums.LoggerLevel;
import com.draconincdomain.custommobs.utils.Arrays.CustomEntityArrayHandler;
import com.draconincdomain.custommobs.utils.ItemDrop;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BossMob extends CustomMob {
    private String[] abilities; // Array of special abilities
    private int[] abilityLevels; // Array of ability levels
    private String[] attacks; // Array of special attacks
    private ItemDrop[] lootDrops; // Array of loot drops

    private LootTable lootTable;


    public BossMob(String name, String mobNameID, boolean champion, double maxHealth, int spawnChance, EntityType entityType, ItemStack weapon, double weaponDropChance, ItemStack[] armour, int mobID,
                   String[] abilities, int[] abilityLevels, String[] attacks, ItemDrop[] lootDrops) {

        super(name, mobNameID, champion, maxHealth, spawnChance, entityType, weapon, weaponDropChance, armour, mobID);

        this.abilities = abilities;
        this.abilityLevels = abilityLevels;
        this.attacks = attacks;
        this.lootDrops = lootDrops;
    }

    @Override
    public void spawnEntity(Location location, int level) {
        super.spawnEntity(location, level);

        if (this.getEntity() instanceof LivingEntity) { // Ensure the entity is of type LivingEntity
            LivingEntity livingEntity = (LivingEntity) this.getEntity();
            for (int i = 0; i < this.getAbilities().length; i++) {
                String ability = this.getAbilities()[i];
                int abilityLevel = this.getAbilityLevels()[i];  // get corresponding abilityLevel of the ability
                PotionEffectType effectType = PotionEffectType.getByName(ability);
                if (effectType != null) {
                    livingEntity.addPotionEffect(new PotionEffect(effectType, 10000, abilityLevel, false, false));
                }
            }

            // Schedule attacks
            for (String attack : this.getAttacks()) {
                // Schedule these attacks
                GameEventScheduler.scheduleSpecialAttack(livingEntity, attack);
            }

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

        }

        CustomEntityArrayHandler.getBossEntities().put(getEntity(), this);
    }

    public String[] getAbilities() {
        return abilities;
    }

    public int[] getAbilityLevels() {
        return abilityLevels;
    }


    public String[] getAttacks() {
        return attacks;
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
