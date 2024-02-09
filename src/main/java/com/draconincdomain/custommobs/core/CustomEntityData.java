package com.draconincdomain.custommobs.core;

import com.draconincdomain.custommobs.CustomMobsControl;

import com.draconincdomain.custommobs.utils.CustomEntityArrayHandler;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.Map;

public class CustomEntityData {

    private static CustomEntityData Instance;

    public CustomEntityData() {
        Instance = this;
    }

    public void load() {
        File mobsConfig = new File(CustomMobsControl.getInstance().getDataFolder(), "mobs.yml");

        if (!mobsConfig.exists()) CustomMobsControl.getInstance().saveResource("mobs.yml", false);
    }

    public static YamlConfiguration GetConfig() {
        File configFIle = new File(CustomMobsControl.getInstance().getDataFolder(), "mobs.yml");
        return YamlConfiguration.loadConfiguration(configFIle);
    }

    public void GetData() {

        try {

            for (Map<?, ?> mobMap : GetConfig().getMapList("customMobs")) {
                CustomMob mob = CustomMobCreation.fromMap(mobMap);
                CustomEntityArrayHandler.getRegisteredCustomMobs().add(mob);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static CustomMob getRandomMob() {
        if (CustomEntityArrayHandler.getRegisteredCustomMobs().isEmpty()) return null;
        int randomIndex = (int) (Math.random() * CustomEntityArrayHandler.getRegisteredCustomMobs().size());

        return CustomEntityArrayHandler.getRegisteredCustomMobs().get(randomIndex);
    }

    public boolean isCustomMobsEnabled() {
        return (boolean) GetConfig().get("customMobsEnabled") == true;
    }

    public static CustomEntityData getInstance() {
        return Instance;
    }

}
