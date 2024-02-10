package com.draconincdomain.custommobs.commands;

import com.draconincdomain.custommobs.CustomMobsControl;
import com.draconincdomain.custommobs.core.CustomMob;
import com.draconincdomain.custommobs.core.CustomEntityData;
import com.draconincdomain.custommobs.utils.CustomEntityArrayHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SpawnCustomEntity extends CommandCore {

    public SpawnCustomEntity() {
        super("customspawn");
    }

    @Override
    protected void execute(Player player, String[] args) {
        CustomMob customMob = CustomEntityData.getRandomMob();

        customMob.spawnEntity(player.getLocation());

        player.sendMessage(ChatColor.DARK_BLUE + "Registered Mobs: " + String.valueOf(CustomEntityArrayHandler.getRegisteredCustomMobs().size()));
        player.sendMessage(ChatColor.DARK_BLUE + "Spawned Custom Mobs: " + String.valueOf(CustomEntityArrayHandler.getCustomEntities().size()));
    }

    @Override
    protected List<String> commandCompletion(Player player, Command command, String[] strings) {

        List<String> completion = new ArrayList<>();

        if (command.getName().equalsIgnoreCase("customspawn")) {
            if (strings.length == 1) {
                completion.add("spawn");
            }
        }

        return completion;
    }

}
