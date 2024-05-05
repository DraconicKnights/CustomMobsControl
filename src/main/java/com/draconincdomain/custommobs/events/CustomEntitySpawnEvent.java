package com.draconincdomain.custommobs.events;

import com.draconincdomain.custommobs.CustomMobsControl;
import com.draconincdomain.custommobs.core.Annotations.Events;
import com.draconincdomain.custommobs.core.RPGMobs.BossMob;
import com.draconincdomain.custommobs.core.CustomEvents.CustomEntityEvent;
import com.draconincdomain.custommobs.core.RPGMobs.CustomMob;
import com.draconincdomain.custommobs.core.RPGData.CustomEntityData;
import com.draconincdomain.custommobs.core.CustomMobManager;
import com.draconincdomain.custommobs.core.enums.LoggerLevel;
import com.draconincdomain.custommobs.utils.*;
import com.draconincdomain.custommobs.utils.Arrays.CustomEntityArrayHandler;
import com.draconincdomain.custommobs.utils.Data.MobDataHandler;
import com.draconincdomain.custommobs.utils.Desing.ColourCode;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTransformEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

@Events
public class CustomEntitySpawnEvent implements Listener {

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (!Random.CustomSpawn(100)) return;

        if (!CustomEntityData.getInstance().isCustomMobsEnabled()) return;

        Location spawnLocation = event.getLocation();
        World world = spawnLocation.getWorld();

        for (Player player : world.getPlayers()) {
            Location playerLocation = player.getLocation();
            double distance = spawnLocation.distance(playerLocation);
            int minDistance = calculateMinSpawnDistance(player); // Custom method to calculate minimum spawn distance
            int maxDistance = calculateMaxSpawnDistance(player); // Custom method to calculate maximum spawn distance

            if (distance > minDistance && distance < maxDistance) {

                Block spawnBlock = spawnLocation.getBlock();

                if (spawnBlock.getType() == Material.WATER || spawnBlock.getRelative(BlockFace.UP).getType() == Material.WATER)
                    return;

                CustomMob customMob = CustomEntityData.getRandomMob();
                int spawnChance = calculateSpawnChance(customMob.getSpawnChance(), distance);
                if (!Random.SpawnChance(spawnChance)) return;

                Map<Entity, CustomMob> customEntities = CustomEntityArrayHandler.getCustomEntities();

                // Check the number of custom mobs around the player
                int nearbyCustomMobs = (int) customEntities.keySet().stream()
                        .filter(entity -> entity.getLocation().distance(playerLocation) <= maxDistance)
                        .count();

                // Don't spawn if there are already too many custom mobs near the player
                if(nearbyCustomMobs >= 5) return;

                // Spawn the mob
                event.getEntity().remove();
                CustomMobManager.getInstance().setMobLevelAndSpawn(player, customMob, spawnLocation);
                TriggerCustomEvent(player, customMob);

                break;
            }
        }
    }

    @EventHandler
    public void onCreatureDestroyed(EntityDeathEvent event) {
        Entity entity = event.getEntity();

        // Handle for any entity
        if (CustomEntityArrayHandler.getCustomEntities().containsKey(entity)) {
            CustomEntityArrayHandler.getCustomEntities().remove(entity);
            CustomMobsControl.getInstance().CustomMobLogger("Entity removed from CustomEntities", LoggerLevel.INFO);
        }

        // Handle specifically for boss entities
        if (CustomEntityArrayHandler.getBossEntities().containsKey(entity)) {
            CustomMobsControl.getInstance().CustomMobLogger("Entity found in BossEntities", LoggerLevel.INFO);
            BossMob bossMob = CustomEntityArrayHandler.getBossEntities().get(entity);
            if (bossMob != null) {
                List<ItemStack> lootToDrop = bossMob.getLootTable().rollLoot();
                // Log the loot to be dropped
                CustomMobsControl.getInstance().CustomMobLogger("Loot to be dropped: " + lootToDrop.toString(), LoggerLevel.INFO);

                List<ItemStack> drops = event.getDrops();
                drops.clear();
                drops.addAll(lootToDrop);
                CustomEntityArrayHandler.getBossEntities().remove(entity);
            } else {
                CustomMobsControl.getInstance().CustomMobLogger("BossMob is null", LoggerLevel.INFO);
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (!CustomEntityArrayHandler.getCustomEntities().containsKey(event.getEntity())) return;

        CustomMob customMob = CustomEntityArrayHandler.getCustomEntities().get(event.getEntity());

        LivingEntity entity = (LivingEntity) event.getEntity();

        double damage = event.getFinalDamage();
        double health = entity.getHealth() + entity.getAbsorptionAmount();

        if (health > damage) {
            health -= damage;

            String customName = String.format("&7[&3%s&r&7] %s &r&c%d/%d", customMob.getLevel(), customMob.getName(), (int) health, (int) customMob.getMaxHealth());
            entity.setCustomName(ColourCode.colour(customName));
        }
    }

    @EventHandler
    public void onZombieDrown(EntityTransformEvent event) {
        Entity entity = event.getEntity();

        // only cancel event for zombies within the custom entity group transforming to drowned

        if (!CustomEntityArrayHandler.getCustomEntities().containsKey(entity)) return;

        if (entity instanceof Zombie && event.getTransformReason() == EntityTransformEvent.TransformReason.DROWNED) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onCustomEntity(CustomEntityEvent customEntityEvent) {
        CustomMob customMob = customEntityEvent.getCustomMob();

        CustomMobsControl.getInstance().CustomMobLogger("Entity: " + customMob.getEntityType() + " Has spawned near player: " + customEntityEvent.getPlayer().getName() + " Type: " + customMob.getName(), LoggerLevel.INFO);
    }

    private int calculateMinSpawnDistance(Player player) {
        if (player.getWorld().getTime() > 13000 && player.getWorld().getTime() < 23000) {
            return MobDataHandler.minDistance / 2;
        }
        return MobDataHandler.minDistance;
    }

    private int calculateMaxSpawnDistance(Player player) {
        int numPlayers = Bukkit.getOnlinePlayers().size();
        return MobDataHandler.maxDistance + numPlayers * 10;
    }

    private int calculateSpawnChance(int baseChance, double distance) {
        float distanceEffect = 0.8f;

        //distance affects spawn chance now
        int distanceAdjustedChance = (int) (baseChance * Math.pow(distanceEffect, distance));

        //Make sure we return a positive number.
        if (distanceAdjustedChance <= 0) {
            return 1;
        }
        else {
            return distanceAdjustedChance;
        }
    }


    private void TriggerCustomEvent(Player player, CustomMob customMob) {
        Bukkit.getServer().getPluginManager().callEvent(new CustomEntityEvent(player, customMob));
    }

}
