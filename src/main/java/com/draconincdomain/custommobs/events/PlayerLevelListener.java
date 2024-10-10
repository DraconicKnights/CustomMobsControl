package com.draconincdomain.custommobs.events;

import com.draconincdomain.custommobs.core.Annotations.Events;
import com.draconincdomain.custommobs.core.RPGMobs.CustomMob;
import com.draconincdomain.custommobs.core.Player.PlayerDataManager;
import com.draconincdomain.custommobs.core.Player.PlayerData;
import com.draconincdomain.custommobs.utils.Arrays.CustomEntityArrayHandler;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wither;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;

/**
 * Player Level listener
 * Adjusts the players custom XP via event actions and scales based on each time and a multiplier within the Player YML
 */
@Events
public class PlayerLevelListener implements Listener {

    @EventHandler
    public void onEntityKill(EntityDeathEvent event) {
        if (event.getEntity().getKiller() != null) {
            Player player = event.getEntity().getKiller();
            PlayerData pData = PlayerDataManager.getInstance().getPlayerData(player.getUniqueId());
            int multiplier = getMobExperienceMultiplier(event.getEntity());
            pData.onMobKill(player, multiplier);
        }
    }

    @EventHandler
    public void  onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData = PlayerDataManager.getInstance().getPlayerData(player.getUniqueId());
        int multiplier = getBlockExperienceMultiplier(event.getBlock());
        playerData.onBlockMine(player, multiplier);
    }

    private int getMobExperienceMultiplier(LivingEntity entity) {
        if(entity instanceof EnderDragon || entity instanceof Wither) {
            return 10;
        } else if(CustomEntityArrayHandler.getCustomEntities().containsKey(entity)) {
            CustomMob customMob = CustomEntityArrayHandler.getCustomEntities().get(entity);
            return (int) Math.sqrt(customMob.getLevel());
        }
        return 1;
    }

    private int getBlockExperienceMultiplier(Block block) {
        Material type = block.getType();
        if(type == Material.DIAMOND_ORE || type == Material.EMERALD_ORE) {
            return 5;
        } else if(type == Material.COAL_ORE || type == Material.IRON_ORE) {
            return 2;
        }
        return 1;
    }
}
