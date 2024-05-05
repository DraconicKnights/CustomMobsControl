package com.draconincdomain.custommobs.utils.Data;

import com.draconincdomain.custommobs.CustomMobsControl;
import com.draconincdomain.custommobs.core.RPGData.BossMobData;
import com.draconincdomain.custommobs.core.RPGData.CustomEntityData;
import com.draconincdomain.custommobs.core.enums.LoggerLevel;
import com.draconincdomain.custommobs.utils.Arrays.CustomEntityArrayHandler;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;

import java.io.File;

public class MobDataHandler {

    private static MobDataHandler Instance;

    public static int minDistance;
    public static int maxDistance;
    public static boolean isEnabled;

    public MobDataHandler() {
        Instance = this;
        load();
        setDistanceValues();
        setIsEnabledValue();
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

    private void setIsEnabledValue() {
        isEnabled = (boolean) GetConfig().get("customMobsEnabled");
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
}
