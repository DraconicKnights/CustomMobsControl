package com.draconincdomain.custommobs.core.Player;

import com.draconincdomain.custommobs.CustomMobsControl;
import com.draconincdomain.custommobs.core.enums.LoggerLevel;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Player Data Manager, used for managing custom plugin data for each player
 */
public class PlayerDataManager {

    private static PlayerDataManager instance;

    private static final Gson gson = new Gson();

    private final Map<UUID, PlayerData> levels = new HashMap<>();
    private final File saveFolder;

    public PlayerDataManager() {
        instance = this;
        this.saveFolder = new File(CustomMobsControl.getInstance().getDataFolder(), "levels");
        if (!this.saveFolder.exists()) {
            this.saveFolder.mkdirs();
        }
    }

    public PlayerData loadPlayerData(UUID uuid) {
        File playerFile = new File(saveFolder, uuid.toString() + ".txt");
        return loadLevelData(uuid, playerFile);
    }

    public void savePlayerData(PlayerData playerData) {
        UUID uuid = playerData.getPlayerUUID();
        File playerFile = new File(saveFolder, uuid.toString() + ".txt");
        saveLevelData(playerData, playerFile);
    }

    private PlayerData loadLevelData(UUID playerUUID, File file) {
        if (!file.exists()) {
            return new PlayerData(playerUUID);
        }

        try {
            String content = new String(Files.readAllBytes(file.toPath()));
            PlayerData data = gson.fromJson(content, PlayerData.class);

            return new PlayerData(playerUUID, data.getLevel(), data.getXp());

        } catch (IOException e) {
            CustomMobsControl.getInstance().CustomMobLogger(e.toString(), LoggerLevel.ERROR);
            return new PlayerData(playerUUID);
        }
    }

    private void saveLevelData(PlayerData data, File file) {
        try (PrintWriter writer = new PrintWriter(file)) {
            String jsonOutput = gson.toJson(data);
            writer.println(jsonOutput);

        } catch (FileNotFoundException e) {
            CustomMobsControl.getInstance().CustomMobLogger(e.toString(), LoggerLevel.ERROR);
        }
    }

    public PlayerData getPlayerDataFromFile(UUID playerUuid) {
        // Get the directory containing the player files
        File playerDataDirectory = saveFolder;

        // Check each file in the directory
        for (File file : playerDataDirectory.listFiles()) {
            // Skip if not a file
            if (!file.isFile()) {
                continue;
            }

            // Skip if file does not correspond to the given UUID
            if (!file.getName().equals(playerUuid.toString() + ".txt")) {
                continue;
            }

            // Load and return the PlayerData from this file
            return loadPlayerData(playerUuid);
        }

        // If no matching file was found, return null
        return null;
    }

    public void addPlayerData(UUID uuid, PlayerData playerData) {
        levels.put(uuid, playerData);
    }

    public PlayerData getPlayerData(UUID uuid) {
        return levels.get(uuid);
    }

    public static PlayerDataManager getInstance() {
        return instance;
    }



}
