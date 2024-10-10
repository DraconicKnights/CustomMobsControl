package com.draconincdomain.custommobs.events;

import com.draconincdomain.custommobs.core.Annotations.Events;
import com.draconincdomain.custommobs.core.Player.PlayerDataManager;
import com.draconincdomain.custommobs.core.Player.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

/**
 * Player Login and Logout listener
 * Used for grabbing and saving custom player data
 */
@Events
public class PlayerLoginAndLogoutListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();
        PlayerData playerData = PlayerDataManager.getInstance().loadPlayerData(playerUUID);
        PlayerDataManager.getInstance().addPlayerData(playerUUID, playerData);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();
        PlayerData playerData = PlayerDataManager.getInstance().getPlayerData(playerUUID);
        PlayerDataManager.getInstance().savePlayerData(playerData);
    }
}
