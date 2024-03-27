package com.draconincdomain.custommobs.commands;

import com.draconincdomain.custommobs.core.Annotations.Commands;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import java.util.List;

@Commands
public class ExampleCommand extends CommandCore{
    public ExampleCommand() {
        super("example");
    }

    @Override
    protected void execute(Player player, String[] args) {
        player.sendMessage("Example");
    }

    @Override
    protected List<String> commandCompletion(Player player, Command command, String[] strings) {
        return null;
    }
}
