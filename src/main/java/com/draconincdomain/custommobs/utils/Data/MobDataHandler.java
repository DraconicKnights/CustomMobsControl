package com.draconincdomain.custommobs.utils.Data;

import com.draconincdomain.custommobs.CustomMobsControl;
import com.draconincdomain.custommobs.core.RPGData.BossMobData;
import com.draconincdomain.custommobs.core.RPGData.CustomEntityData;
import com.draconincdomain.custommobs.core.RPGData.CustomMobCreation;
import com.draconincdomain.custommobs.core.RPGMobs.CustomMob;
import com.draconincdomain.custommobs.core.enums.LoggerLevel;
import com.draconincdomain.custommobs.utils.Arrays.CustomEntityArrayHandler;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MobDataHandler {

    private static MobDataHandler Instance;
    public static int minDistance;
    public static int maxDistance;
    public static boolean isEnabled;
    private static File mobDataFile = new File(CustomMobsControl.getInstance().getDataFolder(), "mobs.yml");
    private static YamlConfiguration mobDataConfig = YamlConfiguration.loadConfiguration(mobDataFile);

    public MobDataHandler() {
        Instance = this;
        load();
        setDistanceValues();
        setIsEnabledValue();
    }

    private void load() {
        if (!mobDataFile.exists()) CustomMobsControl.getInstance().saveResource("mobs.yml", false);
        mobDataConfig = YamlConfiguration.loadConfiguration(mobDataFile);
    }

    public static YamlConfiguration GetConfig() {
        return mobDataConfig;
    }

    private void setDistanceValues() {
        minDistance = mobDataConfig.getInt("spawningDistanceMin");
        maxDistance = mobDataConfig.getInt("spawningDistanceMax");
    }

    private void setIsEnabledValue() {
        isEnabled = mobDataConfig.getBoolean("customMobsEnabled");
    }

    public void ReloadMobsConfig() {
        RemoveAllCustomMobs();
        CustomEntityArrayHandler.getRegisteredCustomMobs().clear();
        CustomEntityArrayHandler.getCustomEntities().clear();
        CustomEntityArrayHandler.getRegisteredBossMobs().clear();
        CustomEntityArrayHandler.getBossEntities().clear();

        load();

        CustomEntityData.getInstance().GetData();
        BossMobData.getInstance().getData();
    }

    public void RemoveAllMobs() {
        RemoveAllCustomMobs();
        CustomEntityArrayHandler.getRegisteredCustomMobs().clear();
        CustomEntityArrayHandler.getRegisteredBossMobs().clear();
    }

    private void RemoveAllCustomMobs() {
        try {
            CustomMobsControl.getInstance().CustomMobLogger("Starting removal of all custom mobs", LoggerLevel.INFO);
            for (Entity entity : CustomEntityArrayHandler.getCustomEntities().keySet()) {
                entity.remove();
            }
            for (Entity entity : CustomEntityArrayHandler.getBossEntities().keySet()) {
                entity.remove();
            }
            CustomMobsControl.getInstance().CustomMobLogger("All mobs have successfully been removed", LoggerLevel.INFO);
            CustomMobsControl.getInstance().CustomMobLogger("Mobs active: " + CustomEntityArrayHandler.getRegisteredCustomMobs().keySet().size(), LoggerLevel.INFO);
        } catch (Exception e) {
            CustomMobsControl.getInstance().CustomMobLogger("An error occurred while attempting to remove all custom mobs", LoggerLevel.INFO);
            e.printStackTrace();
        }
    }

    public static MobDataHandler getInstance() {
        return Instance;
    }

    public static void saveCustomMobData() {
        List<Map<String, Object>> mobList = new ArrayList<>();
        for (CustomMob mob : CustomEntityArrayHandler.getRegisteredCustomMobs().values()) {
            mobList.add(CustomMobCreation.toMap(mob));
        }
        mobDataConfig.set("customMobs", mobList);
        try {
            mobDataConfig.save(mobDataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
