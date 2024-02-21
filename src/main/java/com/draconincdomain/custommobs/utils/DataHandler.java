package com.draconincdomain.custommobs.utils;

import com.draconincdomain.custommobs.CustomMobsControl;
import com.draconincdomain.custommobs.core.CustomEntityData;
import com.draconincdomain.custommobs.core.enums.LoggerLevel;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;

import java.io.File;

public class DataHandler {

    private static DataHandler Instance;

    public int minDistance;
    public int maxDistance;

    public DataHandler() {
        Instance = this;
        load();
        GetConfig();
        setDistanceValues();
    }

    private void load() {
        File mobsConfig = new File(CustomMobsControl.getInstance().getDataFolder(), "mobs.yml");
        if (!mobsConfig.exists()) CustomMobsControl.getInstance().saveResource("mobs.yml", false);
    }

    public static YamlConfiguration GetConfig() {
        File configFIle = new File(CustomMobsControl.getInstance().getDataFolder(), "mobs.yml");
        return YamlConfiguration.loadConfiguration(configFIle);
    }

    private void setDistanceValues() {
        minDistance = (int) GetConfig().get("spawningDistanceMin");
        maxDistance = (int) GetConfig().get("spawningDistanceMax");
    }

    public void ReloadMobsConfig() {

        RemoveAllCustomMobs();
        CustomEntityArrayHandler.getRegisteredCustomMobs().clear();
        CustomEntityArrayHandler.getCustomEntities().clear();

        load();

        CustomEntityData.getInstance().GetData();
    }

    public void RemoveAllMobs() {
        RemoveAllCustomMobs();
    }

    private void RemoveAllCustomMobs() {
        try {
            CustomMobsControl.getInstance().CustomMobLogger("Starting removal of all custom mobs", LoggerLevel.INFO);
            for (Entity entity : CustomEntityArrayHandler.getCustomEntities().keySet()) {
                entity.remove();
            }
            CustomMobsControl.getInstance().CustomMobLogger("All mobs have successfully been removed", LoggerLevel.INFO);
        } catch (Exception e) {
            CustomMobsControl.getInstance().CustomMobLogger("An error occurred while attempting to remove all custom mobs", LoggerLevel.INFO);
            e.printStackTrace();
        }
    }

    public static DataHandler getInstance() {
        return Instance;
    }
}
