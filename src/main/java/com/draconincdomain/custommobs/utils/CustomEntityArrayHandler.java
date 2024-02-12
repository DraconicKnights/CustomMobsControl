package com.draconincdomain.custommobs.utils;

import com.draconincdomain.custommobs.core.CustomMob;
import org.bukkit.entity.Entity;

import java.util.HashMap;
import java.util.Map;

public class CustomEntityArrayHandler {

    private static Map<Integer, CustomMob> registeredMobs = new HashMap<>();
    private static Map<Entity, CustomMob> customEntities = new HashMap<>();

    public static Map<Integer, CustomMob> getRegisteredCustomMobs() {
        return registeredMobs;
    }

    public static Map<Entity, CustomMob> getCustomEntities() {
        return customEntities;
    }


}
