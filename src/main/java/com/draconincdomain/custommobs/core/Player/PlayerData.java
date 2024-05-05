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

    public void onMobKill(Player player, int multiplier) {
        addXp(PlayerDataHandler.MOB_KILL_XP, player, multiplier);
    }

    public void onBlockMine(Player player, int multiplier) {
        addXp(PlayerDataHandler.BLOCK_BREAK_XP, player, multiplier);
    }

    public void addXp(int amount, Player player, int multiplier) {
        this.xp += amount * multiplier;
        levelUp(player);
    }

    public void setXp(int xp, Player player) {
        this.xp = xp;
        levelUp(player);
    }

    private void levelUp(Player player) {
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
