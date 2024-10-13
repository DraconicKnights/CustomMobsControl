package com.draconincdomain.custommobs.utils.Handlers;


import com.draconincdomain.custommobs.CustomMobsControl;
import com.draconincdomain.custommobs.core.RPGMobs.CustomMob;
import com.draconincdomain.custommobs.utils.Arrays.CustomEntityArrayHandler;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RegionManager {
    private static RegionManager Instance;
    private static List<Region> regions = new ArrayList<>();

    public RegionManager() {
        Instance = this;
        loadRegions();
    }

    public void addRegion(Region region) {
        regions.add(region);
    }

    public List<Region> getRegion() {
        return regions;
    }

    public Region getRegionFromLocation(Location location) {
        for(Region region : regions) { // Assume regions is a Collection of all regions managed by RegionManager
            if(region.isLocationInRegion(location)) {
                return region;
            }
        }
        return null;
    }

    public void saveRegion(Region region) {
        try {
            File file = new File(CustomMobsControl.getInstance().getDataFolder() + "/RegionData/" + region.getRegionName() + ".ser");
            file.getParentFile().mkdirs(); // Ensure the directory exists

            ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(file.toPath()));
            oos.writeObject(region);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load regions from the storage on startup
    private void loadRegions() {
        File directory = new File(CustomMobsControl.getInstance().getDataFolder() + "/RegionData");
        File[] regionFiles = directory.listFiles();
        if (regionFiles != null) {
            for (File regionFile : regionFiles) {
                try {
                    ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(regionFile.toPath()));
                    Region region = (Region) ois.readObject();
                    regions.add(region);
                    ois.close();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void spawnMobsInRegion(Region region, CustomMob customMob) {
        Random random = new Random();

        int mobCount = random.nextInt(5) + 1;  // This decides to spawn 1 to 5 mobs

        for (int i = 0; i < mobCount; i++) {
            int mobLevel = region.getMinLevel() + random.nextInt(region.getMaxLevel() - region.getMinLevel() + 1);
            Location spawnLocation = region.getRandomLocation();
            customMob.spawnEntity(spawnLocation, mobLevel);
            CustomEntityArrayHandler.getCustomEntities().put(customMob.getEntity(), customMob);
        }
    }

    public static RegionManager getInstance() {
        return Instance;
    }
}
