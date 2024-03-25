package com.draconincdomain.custommobs.utils.Data;

import com.draconincdomain.custommobs.CustomMobsControl;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class PlayerDataHandler {

    private static PlayerDataHandler Instance;

    public PlayerDataHandler() {
        Instance = this;
        load();
        GetConfig();
    }

    private void load() {
        File playerConfig = new File(CustomMobsControl.getInstance().getDataFolder(), "player.yml");
        if (!playerConfig.exists()) CustomMobsControl.getInstance().saveResource("player.yml", false);
    }

    public static YamlConfiguration GetConfig() {
        File configFIle = new File(CustomMobsControl.getInstance().getDataFolder(), "player.yml");
        return YamlConfiguration.loadConfiguration(configFIle);
    }

    public static PlayerDataHandler getInstance() {
        return Instance;
    }


}
