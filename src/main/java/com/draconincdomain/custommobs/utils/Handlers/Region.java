package com.draconincdomain.custommobs.utils.Handlers;

import com.draconincdomain.custommobs.core.RPGMobs.CustomMob;
import com.draconincdomain.custommobs.utils.Data.SerializableLocation;
import org.bukkit.Location;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Region implements Serializable {
    private String regionName;
    private SerializableLocation firstCorner;
    private SerializableLocation secondCorner;
    private List<CustomMob> spawnableMobs;
    private int currentMobs;
    private int minLevel;
    private int maxLevel;

    public Region(String regionName, Location firstCorner, Location secondCorner, List<CustomMob> spawnableMobs, int minLevel, int maxlevel) {
        this.regionName = regionName;
        this.firstCorner = (firstCorner != null) ? new SerializableLocation(firstCorner) : null;
        this.secondCorner = (secondCorner != null) ? new SerializableLocation(secondCorner) : null;
        this.spawnableMobs = spawnableMobs;
        this.minLevel = minLevel;
        this.maxLevel = maxlevel;
    }

    public Region() {
        this(null, null, null, new ArrayList<>(), 0, 0);
    }

    private Location toLocation(SerializableLocation serializableLocation) {
        return serializableLocation == null ? null : serializableLocation.toLocation();
    }

    public boolean isLocationInRegion(Location location) {
        Location firstCornerLocation = toLocation(firstCorner);
        Location secondCornerLocation = toLocation(secondCorner);

        return (location.getX() >= Math.min(firstCornerLocation.getX(), secondCornerLocation.getX()) &&
                location.getX() <= Math.max(firstCornerLocation.getX(), secondCornerLocation.getX()) &&
                location.getY() >= Math.min(firstCornerLocation.getY(), secondCornerLocation.getY()) &&
                location.getY() <= Math.max(firstCornerLocation.getY(), secondCornerLocation.getY()) &&
                location.getZ() >= Math.min(firstCornerLocation.getZ(), secondCornerLocation.getZ()) &&
                location.getZ() <= Math.max(firstCornerLocation.getZ(), secondCornerLocation.getZ()));
    }

    public Location getRandomLocation() {
        if (!isDefined())
            return null;

        Random rand = new Random();

        Location firstCornerLocation = toLocation(firstCorner);
        Location secondCornerLocation = toLocation(secondCorner);

        double minX = Math.min(firstCornerLocation.getX(), secondCornerLocation.getX());
        double maxX = Math.max(firstCornerLocation.getX(), secondCornerLocation.getX());

        double minY = Math.min(firstCornerLocation.getY(), secondCornerLocation.getY());
        double maxY = Math.max(firstCornerLocation.getY(), secondCornerLocation.getY());

        double minZ = Math.min(firstCornerLocation.getZ(), secondCornerLocation.getZ());
        double maxZ = Math.max(firstCornerLocation.getZ(), secondCornerLocation.getZ());

        double x = minX + (maxX - minX) * rand.nextDouble();
        double y = minY + (maxY - minY) * rand.nextDouble();
        double z = minZ + (maxZ - minZ) * rand.nextDouble();

        return new SerializableLocation(firstCornerLocation.getWorld().getName(), x, y, z).toLocation();
    }

    public boolean isDefined() {
        return firstCorner != null && secondCorner != null;
    }

    public String getRegionName() {
        return regionName;
    }

    public List<CustomMob> getSpawnableMobs() {
        return spawnableMobs;
    }

    public int getCurrentMobs() {
        return currentMobs;
    }

    public int getRandomLevel() {
        return minLevel + (int) (Math.random() * ((maxLevel - minLevel) + 1));
    }

    public int getMinLevel() {
        return minLevel;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public Location getFirstCorner() {
        return toLocation(firstCorner);
    }

    public Location getSecondCorner() {
        return toLocation(secondCorner);
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public void setFirstCorner(Location location) {
        this.firstCorner = new SerializableLocation(location);
    }

    public void setSecondCorner(Location location) {
        this.secondCorner = new SerializableLocation(location);
    }

    public void setSpawnableMobs(List<CustomMob> spawnableMobs) {
        this.spawnableMobs.addAll(spawnableMobs);
    }

    public void setCurrentMobs(int currentMobs) {
        this.currentMobs = currentMobs;
    }

    public void setMinLevel(int minLevel) {
        this.minLevel = minLevel;
    }

    public void setMaxLevel(int maxlevel) {
        this.maxLevel = maxlevel;
    }
}


