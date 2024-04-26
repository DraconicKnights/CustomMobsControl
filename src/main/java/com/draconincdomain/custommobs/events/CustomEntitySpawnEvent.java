package com.draconincdomain.custommobs.events;

import com.draconincdomain.custommobs.CustomMobsControl;
import com.draconincdomain.custommobs.core.Annotations.Events;
import com.draconincdomain.custommobs.core.BossMob;
import com.draconincdomain.custommobs.core.CustomEvents.CustomEntityEvent;
import com.draconincdomain.custommobs.core.CustomMob;
import com.draconincdomain.custommobs.core.CustomEntityData;
import com.draconincdomain.custommobs.core.enums.LoggerLevel;
import com.draconincdomain.custommobs.utils.*;
import com.draconincdomain.custommobs.utils.Arrays.CustomEntityArrayHandler;
import com.draconincdomain.custommobs.utils.Data.MobDataHandler;
import com.draconincdomain.custommobs.utils.Desing.ColourCode;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
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

                customMob.spawnEntity(spawnLocation);
                TriggerCustomEvent(player, customMob);
                break;
            }
        }
    }

    @EventHandler
    public void onCreatureDestroyed(EntityDeathEvent event) {
        if (!CustomEntityArrayHandler.getCustomEntities().containsKey(event.getEntity())) return;
        CustomEntityArrayHandler.getCustomEntities().remove(event.getEntity());
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

            entity.setCustomName(ColourCode.colour("&7[&3" + customMob.getLevel() + "&r&7] " + customMob.getName() + " &r&c" + (int) health + "/" + (int) customMob.getMaxHealth()));
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

    @EventHandler
    public void onBossDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();

        if (CustomEntityArrayHandler.getBossEntities().containsKey(entity)) {
            BossMob bossMob = CustomEntityArrayHandler.getBossEntities().get(entity);
           if (bossMob != null) {
               List<ItemStack> drops = event.getDrops();
               drops.clear();
               drops.addAll(bossMob.getLootTable().rollLoot());
           }
        }
    }
}
