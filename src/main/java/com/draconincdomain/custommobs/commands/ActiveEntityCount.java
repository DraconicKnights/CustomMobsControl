package com.draconincdomain.custommobs.commands;

import com.draconincdomain.custommobs.core.Annotations.Commands;
import com.draconincdomain.custommobs.utils.Arrays.CustomEntityArrayHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Active Entity Count for checking how many entities are currently spawned
 */
@Commands
public class ActiveEntityCount extends CommandCore {

    public ActiveEntityCount() {
        super("entitycount","custom.admin", 10);
    }

    @Override
    protected void execute(Player player, String[] args) {
        player.sendMessage(ChatColor.YELLOW + "Spawned Entities: " + ChatColor.AQUA + CustomEntityArrayHandler.getCustomEntities().keySet().size());
    }

    @Override
    protected List<String> commandCompletion(Player player, Command command, String[] strings) {
        return null;
    }

}
