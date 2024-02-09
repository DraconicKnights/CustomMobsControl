package com.draconincdomain.custommobs.utils;

import com.draconincdomain.custommobs.CustomMobsControl;
import com.draconincdomain.custommobs.core.CustomEntityData;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.io.File;

public class DataHandler {

    private static DataHandler Instance;

    public DataHandler() {
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

    public void ReloadMobsConfig() {

        RemoveAllCustomMobs();
        CustomEntityArrayHandler.getRegisteredCustomMobs().clear();
        CustomEntityArrayHandler.getCustomEntities().clear();

        load();

        CustomEntityData.getInstance().GetData();
    }

    private void RemoveAllCustomMobs() {
        try {
            CustomMobsControl.getInstance().CustomMobLogger("Starting removal of all custom mobs");
            for (Entity entity : CustomEntityArrayHandler.getCustomEntities().keySet()) {
                entity.remove();
            }
            CustomMobsControl.getInstance().CustomMobLogger("All mobs have successfully been removed");
        } catch (Exception e) {
            CustomMobsControl.getInstance().CustomMobLogger("An error occurred while attempting to remove all custom mobs");
            e.printStackTrace();
        }
    }

    public static DataHandler getInstance() {
        return Instance;
    }
}
