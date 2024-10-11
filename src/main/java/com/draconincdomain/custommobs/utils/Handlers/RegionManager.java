package com.draconincdomain.custommobs.utils.Handlers;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class RegionManager {
    private static List<Region> regions = new ArrayList<>();

    public static void addRegion(Region region) {
        regions.add(region);
    }

    public static Region getRegionByLocation(Location location) {
        for (Region region : regions) {
            if (region.isLocationInRegion(location)) {
                return region;
            }
        }
        return null; // No region found for the location
    }
}
