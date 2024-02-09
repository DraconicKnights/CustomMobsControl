package com.draconincdomain.custommobs.commands;

import com.draconincdomain.custommobs.core.CustomMob;
import com.draconincdomain.custommobs.core.CustomEntityData;
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
        return true;
    }


}
