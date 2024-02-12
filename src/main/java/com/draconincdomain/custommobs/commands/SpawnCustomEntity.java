package com.draconincdomain.custommobs.commands;

import com.draconincdomain.custommobs.core.Annotations.Commands;
import com.draconincdomain.custommobs.core.CustomMob;
import com.draconincdomain.custommobs.core.CustomEntityData;
import com.draconincdomain.custommobs.utils.CustomEntityArrayHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@Commands
public class SpawnCustomEntity extends CommandCore {

    public SpawnCustomEntity() {
        super("customspawn");
    }

    @Override
    protected void execute(Player player, String[] args) {
        CustomMob customMob = CustomEntityData.getRandomMob();

        customMob.spawnEntity(player.getLocation());

        player.sendMessage(ChatColor.DARK_BLUE + "Registered Mobs: " + CustomEntityArrayHandler.getRegisteredCustomMobs().size());
        player.sendMessage(ChatColor.DARK_BLUE + "Spawned Custom Mobs: " + CustomEntityArrayHandler.getCustomEntities().size());
    }

    @Override
    protected List<String> commandCompletion(Player player, Command command, String[] strings) {

        List<String> completion = new ArrayList<>();

        if (command.getName().equalsIgnoreCase(commandName)) {
            if (strings.length == 1) {
                completion.add("spawn");
            }
        }

        return completion;
    }

}
