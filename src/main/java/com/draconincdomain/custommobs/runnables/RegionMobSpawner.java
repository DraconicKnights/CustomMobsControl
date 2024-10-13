package com.draconincdomain.custommobs.runnables;

import com.draconincdomain.custommobs.CustomMobsControl;
import com.draconincdomain.custommobs.core.Annotations.Runnable;
import com.draconincdomain.custommobs.core.RPGMobs.CustomMob;
import com.draconincdomain.custommobs.utils.Handlers.Region;
import com.draconincdomain.custommobs.utils.Handlers.RegionManager;
import com.draconincdomain.custommobs.utils.Runnable.RunnableCore;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        int mobCapInRegion = 20;
        Random random = new Random();

        for (Region region : rm.getRegion()) {
            List<CustomMob> spawnableMobs = region.getSpawnableMobs();

            // Stop if the region has reached its mob cap
            if (region.getCurrentMobs() >= mobCapInRegion || spawnableMobs.isEmpty() ) continue;

            CustomMob randomMob = spawnableMobs.get(random.nextInt(spawnableMobs.size()));

            Location spawnLocation = IntStream.range(0, 10).mapToObj(i -> region.getRandomLocation()).filter(RegionMobSpawner::isValidSpawnLocation).findFirst().orElse(null);


            // If a valid spawn location is found
            if (spawnLocation != null) {
                List<Player> nearbyPlayers = Bukkit.getOnlinePlayers().stream()
                        .filter(player -> player.getWorld().equals(spawnLocation.getWorld()) && player.getLocation().distance(spawnLocation) <= radius)
                        .collect(Collectors.toList());

                if (!nearbyPlayers.isEmpty()) {
                    rm.spawnMobsInRegion(region, randomMob);
                    region.setCurrentMobs(region.getCurrentMobs() + 1);
                }
            }
        }
    }

    private static boolean isValidSpawnLocation(Location location) {
        Block block = location.getBlock();
        return block.getType() == Material.AIR
                && block.getRelative(BlockFace.UP).getType() == Material.AIR
                && block.getRelative(BlockFace.DOWN).getType().isSolid()
                && block.getRelative(BlockFace.DOWN).getType() != Material.WATER
                && block.getRelative(BlockFace.DOWN).getType() != Material.LAVA;
    }
}
