package com.draconincdomain.custommobs.core.Player;

import com.draconincdomain.custommobs.core.CustomMobManager;
import com.draconincdomain.custommobs.utils.Data.PlayerDataHandler;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerData {
    private final UUID playerUUID;
    private int level = 1;
    private int xp = 0;

    public PlayerData(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }

    public PlayerData(UUID playerUUID, int level, int xp) {
        this.playerUUID = playerUUID;
        this.level = level;
        this.xp = xp;
    }

    public void onMobKill(Player player) {
        addXp(PlayerDataHandler.MOB_KILL_XP, player);
    }

    public void onBlockMine(Player player) {
        addXp(PlayerDataHandler.BLOCK_BREAK_XP, player);
    }

    private void addXp(int amount, Player player) {
        xp += amount;

        while (xp >= level * 100) {
            xp -= level * 100;
            level++;

            CustomMobManager.getInstance().levelUpPlayer(player, level);
        }
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public int getLevel() {
        return level;
    }

    public int getXp() {
        return xp;
    }
}
