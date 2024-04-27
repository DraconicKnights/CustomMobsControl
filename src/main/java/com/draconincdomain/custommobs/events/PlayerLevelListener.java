package com.draconincdomain.custommobs.events;

import com.draconincdomain.custommobs.core.Annotations.Events;
import com.draconincdomain.custommobs.core.Player.PlayerDataManager;
import com.draconincdomain.custommobs.core.Player.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;

@Events
public class PlayerLevelListener implements Listener {

    @EventHandler
    public void onEntityKill(EntityDeathEvent event) {
        if (event.getEntity().getKiller() != null) {
            Player player = event.getEntity().getKiller();
            PlayerData pData = PlayerDataManager.getInstance().getPlayerData(player.getUniqueId());
            pData.onMobKill(player);
        }
    }

    @EventHandler
    public void  onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        PlayerData playerData = PlayerDataManager.getInstance().getPlayerData(player.getUniqueId());
        playerData.onBlockMine(player);
    }
}
