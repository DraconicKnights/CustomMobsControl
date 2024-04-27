package com.draconincdomain.custommobs.core;

import com.draconincdomain.custommobs.core.Player.PlayerDataManager;
import com.draconincdomain.custommobs.core.Player.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class CustomMobManager {
    private static CustomMobManager Instance;
    private static final java.util.Random random = new java.util.Random();


    public CustomMobManager() {
        Instance = this;
    }

    public void setMobLevelAndSpawn(Player player, CustomMob customMob, Location spawnLocation) {
        PlayerData playerData = PlayerDataManager.getInstance().getPlayerData(player.getUniqueId());

        if (playerData == null) {
            playerData = new PlayerData(player.getUniqueId());
            PlayerDataManager.getInstance().addPlayerData(player.getUniqueId(), playerData);
        }

        int playerLevel = playerData.getLevel();
        int mobLevel = Math.max(1, playerLevel - 5 + random.nextInt(11));
        customMob.spawnEntity(spawnLocation, mobLevel);
    }

    public void levelUpPlayer(Player player, int level) {
        player.sendMessage(ChatColor.GREEN + "Congratulations! You have reached level " + level);
    }

    public static CustomMobManager getInstance() {
        return Instance;
    }
}
