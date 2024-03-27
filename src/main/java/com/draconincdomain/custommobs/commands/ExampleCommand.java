package com.draconincdomain.custommobs.commands;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import java.util.List;

public class ExampleCommand extends CommandCore{
    public ExampleCommand() {
        super("example", true);
        this.cooldownDuration = 3000;
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
