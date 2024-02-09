package com.draconincdomain.custommobs.events;

import com.draconincdomain.custommobs.core.CustomMob;
import com.draconincdomain.custommobs.core.CustomEntityData;
import com.draconincdomain.custommobs.utils.Random;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

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

            if (distance > (int) CustomEntityData.GetConfig().get("spawningDistanceMin") && distance < (int) CustomEntityData.GetConfig().get("spawningDistanceMax")) {
                event.getEntity().remove();
                CustomMob customMob = CustomEntityData.getRandomMob();

                if (!Random.SpawnChance(customMob.getSpawnChance())) return;

                customMob.spawnEntity(spawnLocation);
                break;
            }
        }
    }

}
