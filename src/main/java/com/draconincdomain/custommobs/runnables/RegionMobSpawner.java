package com.draconincdomain.custommobs.runnables;

import com.draconincdomain.custommobs.CustomMobsControl;
import com.draconincdomain.custommobs.core.Annotations.Runnable;
import com.draconincdomain.custommobs.core.RPGMobs.CustomMob;
import com.draconincdomain.custommobs.utils.Handlers.Region;
import com.draconincdomain.custommobs.utils.Handlers.RegionManager;
import com.draconincdomain.custommobs.utils.Runnable.RunnableCore;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Random;
@Runnable
public class RegionMobSpawner extends RunnableCore {
    public RegionMobSpawner() {
        super(CustomMobsControl.getInstance(), 60, 120);
        this.startTimedTask();
    }

    @Override
    protected void event() {
        RegionManager rm = RegionManager.getInstance();

        double radius = 10.0;

        for (Region region : rm.getRegion()) {

            List<CustomMob> spawnableMobs = region.getSpawnableMobs();

            if (region.getCurrentMobs() < 20 && !spawnableMobs.isEmpty()) {
                Random random = new Random();

                CustomMob randomMob = spawnableMobs.get(random.nextInt(spawnableMobs.size()));

                Location spawnLocation = region.getRandomLocation();

                List<Player> nearbyPlayers = (List<Player>) Bukkit.getWorld(spawnLocation.getWorld().getName()).getNearbyPlayers(spawnLocation, radius);

                if (!nearbyPlayers.isEmpty()) {
                    rm.spawnMobsInRegion(region, randomMob);
                    region.setCurrentMobs(region.getCurrentMobs() + 1);
                }
            }
        }
    }
}
