package com.draconincdomain.custommobs.commands;

import com.draconincdomain.custommobs.core.CustomMob;
import com.draconincdomain.custommobs.core.CustomEntityData;
import com.draconincdomain.custommobs.utils.CustomEntityArrayHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SpawnCustomEntity implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Only players can use this command");
            return true;
        }

        Player player = (Player) commandSender;

        CustomMob customMob = CustomEntityData.getRandomMob();

        customMob.spawnEntity(player.getLocation());

        player.sendMessage(ChatColor.DARK_BLUE + "Registered Mobs: " + String.valueOf(CustomEntityArrayHandler.getRegisteredCustomMobs().size()));
        player.sendMessage(ChatColor.DARK_BLUE + "Spawned Custom Mobs: " + String.valueOf(CustomEntityArrayHandler.getCustomEntities().size()));
        return true;
    }


}
