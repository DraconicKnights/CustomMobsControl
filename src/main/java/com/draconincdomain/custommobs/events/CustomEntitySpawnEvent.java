package com.draconincdomain.custommobs.events;

import com.draconincdomain.custommobs.CustomMobsControl;
import com.draconincdomain.custommobs.core.Annotations.Events;
import com.draconincdomain.custommobs.core.BossMob;
import com.draconincdomain.custommobs.core.CustomEvents.CustomEntityEvent;
import com.draconincdomain.custommobs.core.CustomMob;
import com.draconincdomain.custommobs.core.CustomEntityData;
import com.draconincdomain.custommobs.core.CustomMobManager;
import com.draconincdomain.custommobs.core.enums.LoggerLevel;
import com.draconincdomain.custommobs.utils.*;
import com.draconincdomain.custommobs.utils.Arrays.CustomEntityArrayHandler;
import com.draconincdomain.custommobs.utils.Data.MobDataHandler;
import com.draconincdomain.custommobs.utils.Desing.ColourCode;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

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

            if (distance > MobDataHandler.minDistance && distance < MobDataHandler.maxDistance) {
                if (event.getEntity().isInWater() || event.getEntity().isInLava()) return;
                CustomMob customMob = CustomEntityData.getRandomMob();

                if (!Random.SpawnChance(customMob.getSpawnChance())) return;

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
    public void onCustomEntity(CustomEntityEvent customEntityEvent) {
        CustomMob customMob = customEntityEvent.getCustomMob();

        CustomMobsControl.getInstance().CustomMobLogger("Entity: " + customMob.getEntityType() + " Has spawned near player: " + customEntityEvent.getPlayer().getName() + " Type: " + customMob.getName(), LoggerLevel.INFO);
    }


    private void TriggerCustomEvent(Player player, CustomMob customMob) {
        Bukkit.getServer().getPluginManager().callEvent(new CustomEntityEvent(player, customMob));
    }

}
