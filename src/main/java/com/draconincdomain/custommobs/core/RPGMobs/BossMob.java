package com.draconincdomain.custommobs.core.RPGMobs;

import com.draconincdomain.custommobs.CustomMobsControl;
import com.draconincdomain.custommobs.core.Boss.GameEventScheduler;
import com.draconincdomain.custommobs.core.Boss.LootTable;
import com.draconincdomain.custommobs.core.enums.LoggerLevel;
import com.draconincdomain.custommobs.utils.Arrays.CustomEntityArrayHandler;
import com.draconincdomain.custommobs.utils.ItemDrop;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BossMob extends CustomMob {
    private String[] abilities; // Array of special abilities
    private int[] abilityLevels; // Array of ability levels
    private String[] attacks; // Array of special attacks
    private ItemDrop[] lootDrops; // Array of loot drops for boss mob
    private BossBar bossBar;
    private List<Audience> nearbyPlayers = new ArrayList<>();

    private LootTable lootTable;


    public BossMob(String name, String mobNameID, boolean champion, double maxHealth, int spawnChance, EntityType entityType, ItemStack weapon, double weaponDropChance, ItemStack[] armour, int mobID,
                   String[] abilities, int[] abilityLevels, String[] attacks, ItemDrop[] lootDrops) {

        super(name, mobNameID, champion, maxHealth, spawnChance, entityType, weapon, weaponDropChance, armour, mobID, null);

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

            new GameEventScheduler(livingEntity, this.getAttacks());

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

            this.bossBar = BossBar.bossBar(Component.text(this.getName()), 1f, BossBar.Color.RED, BossBar.Overlay.PROGRESS);

        }

        CustomEntityArrayHandler.getBossEntities().put(getEntity(), this);
    }

    public void updateNearbyPlayers() {
        List<Player> currentNearbyPlayers = getEntity().getNearbyEntities(50, 50, 50).stream()
                .filter(Player.class::isInstance)
                .map(Player.class::cast)
                .collect(Collectors.toList());

        for (Audience player : new ArrayList<>(nearbyPlayers)) {
            if (!currentNearbyPlayers.contains(player)) {
                player.hideBossBar(bossBar);
                nearbyPlayers.remove(player);
                bossBar.removeViewer(player);
            }
        }

        for (Player player : currentNearbyPlayers) {
            if (!nearbyPlayers.contains(player)) {
                player.showBossBar(bossBar);
                nearbyPlayers.add(player);
                bossBar.addViewer(player);
            }
        }
    }

    @Override
    public double getMaxHealth() {
        return super.getMaxHealth();
    }

    @Override
    public double getHealth() {
        return super.getHealth();
    }

    public BossBar getBossBar() {
        return bossBar;
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
