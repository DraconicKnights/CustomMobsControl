package com.draconincdomain.custommobs.events;

import com.draconincdomain.custommobs.core.CustomMob;
import com.draconincdomain.custommobs.core.CustomEntityData;
import com.draconincdomain.custommobs.utils.ColourCode;
import com.draconincdomain.custommobs.utils.CustomEntityArrayHandler;
import com.draconincdomain.custommobs.utils.DataHandler;
import com.draconincdomain.custommobs.utils.Random;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class CustomEntitySpawnEvent implements Listener {
    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (!Random.CustomSpawn(100)) return;

        if (!CustomEntityData.getInstance().isCustomMobsEnabled()) return;

        Location spawnLocation = event.getLocation();
        World world = spawnLocation.getWorld();

        if (event.getEntity().isInWater() || event.getEntity().isInLava()) return;

        for (Player player : world.getPlayers()) {
            Location playerLocation = player.getLocation();
            double distance = spawnLocation.distance(playerLocation);

            if (distance > (int) DataHandler.GetConfig().get("spawningDistanceMin") && distance < (int) DataHandler.GetConfig().get("spawningDistanceMax")) {
                CustomMob customMob = CustomEntityData.getRandomMob();

                if (!Random.SpawnChance(customMob.getSpawnChance())) return;

                event.getEntity().remove();

                customMob.spawnEntity(spawnLocation);
                CustomEntityArrayHandler.getCustomEntities().put(customMob.getEntity(), customMob);
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

}
