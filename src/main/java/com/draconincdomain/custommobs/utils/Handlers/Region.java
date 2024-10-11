package com.draconincdomain.custommobs.utils.Handlers;

import org.bukkit.Location;

import java.util.Random;

public class Region {
    private String regionName;
    private Location firstCorner;
    private Location secondCorner;
    private int minLevel;
    private int maxlevel;

    public Region(String regionName, Location firstCorner, Location secondCorner, int minLevel, int maxlevel) {
        this.regionName = regionName;
        this.firstCorner = firstCorner;
        this.secondCorner = secondCorner;
        this.minLevel = minLevel;
        this.maxlevel = maxlevel;
    }

    public boolean isLocationInRegion(Location location) {
        return (location.getX() >= Math.min(firstCorner.getX(), secondCorner.getX()) &&
                location.getX() <= Math.max(firstCorner.getX(), secondCorner.getX()) &&
                location.getY() >= Math.min(firstCorner.getY(), secondCorner.getY()) &&
                location.getY() <= Math.max(firstCorner.getY(), secondCorner.getY()) &&
                location.getZ() >= Math.min(firstCorner.getZ(), secondCorner.getZ()) &&
                location.getZ() <= Math.max(firstCorner.getZ(), secondCorner.getZ()));
    }

    public void setFirstCorner(Location location) {
        this.firstCorner = location;
    }

    public void setSecondCorner(Location location) {
        this.secondCorner = location;
    }

    public boolean isDefined() {
        return firstCorner != null && secondCorner != null;
    }

    public String getRegionName() {
        return regionName;
    }

    public int getRandomLevel() {
        return minLevel + (int) (Math.random() * ((maxlevel - minLevel) + 1));
    }

    public int getMinLevel() {
        return minLevel;
    }

    public int getMaxLevel() {
        return maxlevel;
    }

    public Location getRandomLocation() {
        if (!isDefined()) return null;
        Random random = new Random();
        double x = Math.min(firstCorner.getX(), secondCorner.getX()) + random.nextDouble() * Math.abs(firstCorner.getX() - secondCorner.getX());
        double y = Math.min(firstCorner.getY(), secondCorner.getY()) + random.nextDouble() * Math.abs(firstCorner.getY() - secondCorner.getY());
        double z = Math.min(firstCorner.getZ(), secondCorner.getZ()) + random.nextDouble() * Math.abs(firstCorner.getZ() - secondCorner.getZ());
        return new Location(firstCorner.getWorld(), x, y, z);
    }
}
