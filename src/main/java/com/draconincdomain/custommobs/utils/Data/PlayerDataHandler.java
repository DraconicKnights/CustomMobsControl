package com.draconincdomain.custommobs.utils.Data;

import com.draconincdomain.custommobs.CustomMobsControl;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class PlayerDataHandler {

    private static PlayerDataHandler Instance;
    public static int MOB_KILL_XP;
    public static int BLOCK_BREAK_XP;

    public PlayerDataHandler() {
        Instance = this;
        load();
        GetConfig();
        setPlayerDataValues();
    }

    private void load() {
        File playerConfig = new File(CustomMobsControl.getInstance().getDataFolder(), "player.yml");
        if (!playerConfig.exists()) CustomMobsControl.getInstance().saveResource("player.yml", false);
    }

    public static YamlConfiguration GetConfig() {
        File configFIle = new File(CustomMobsControl.getInstance().getDataFolder(), "player.yml");
        return YamlConfiguration.loadConfiguration(configFIle);
    }

    private void setPlayerDataValues() {
        MOB_KILL_XP = (int) GetConfig().get("MOB_KILL_XP");
        BLOCK_BREAK_XP = (int) GetConfig().get("BLOCK_BREAK_XP");
    }

    public static PlayerDataHandler getInstance() {
        return Instance;
    }


}
