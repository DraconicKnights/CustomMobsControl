package com.draconincdomain.custommobs.runnables;

import com.draconincdomain.custommobs.CustomMobsControl;
import com.draconincdomain.custommobs.core.Annotations.Runnable;
import com.draconincdomain.custommobs.core.RPGMobs.CustomMob;
import com.draconincdomain.custommobs.core.enums.LoggerLevel;
import com.draconincdomain.custommobs.utils.Arrays.CustomEntityArrayHandler;
import com.draconincdomain.custommobs.utils.Runnable.RunnableCore;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A class that checks if custom mobs need to be despawned based on the distance from players.
 * Extends the RunnableCore class.
 */
@Runnable
public class RPGMobEntityCheck extends RunnableCore {
    public RPGMobEntityCheck() {
        super(CustomMobsControl.getInstance(), 60, 120);
        this.startTimedTask();
    }

    @Override
    protected void event() {
        double despawnRange = 50; // set distance (in blocks) to despawn mobs from players
        Map<Entity, CustomMob> customEntities = CustomEntityArrayHandler.getCustomEntities();
        List<Entity> toRemove = new ArrayList<>();

        // for each custom mob, find the nearest player and their distance
        for (Entity entity: customEntities.keySet()) {
            List<Player> players = entity.getWorld().getPlayers();
            double minDistance = Double.MAX_VALUE;
            for (Player player : players) {
                double distance = player.getLocation().distance(entity.getLocation());
                if (distance < minDistance) {
                    minDistance = distance;
                }
            }

            // Add the mob to remove list if the nearest player is further than the despawnRange
            if (minDistance >= despawnRange) {
                toRemove.add(entity);
            }
        }

        for (Entity entity : toRemove) {
            if(entity instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity) entity;
                CustomMob customMob = customEntities.get(entity);
                if (customMob != null) {
                    CustomMobsControl.getInstance().CustomMobLogger("Custom mob despawned: " + customMob.getName(), LoggerLevel.INFO);
                    livingEntity.remove();
                    if (!livingEntity.isDead()) {
                        // If the entity wasn't successfully removed
                        CustomMobsControl.getInstance().CustomMobLogger("Could not despawn: " + customMob.getName(), LoggerLevel.ERROR);
                    }
                    customEntities.remove(entity);
                }
            }
        }
    }
}
