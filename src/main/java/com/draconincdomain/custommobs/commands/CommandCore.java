package com.draconincdomain.custommobs.commands;

import com.draconincdomain.custommobs.CustomMobsControl;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class CommandCore implements CommandExecutor, TabExecutor {

    protected String commandName;

    public CommandCore(String cmdName) {
        CustomMobsControl.getInstance().getCommand(cmdName).setExecutor(this);
        this.commandName = cmdName;
    }

    protected abstract void execute(Player player, String[] args);
    protected abstract List<String> commandCompletion(Player player, Command command, String[] strings);

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (commandSender instanceof Player) {
            execute((Player) commandSender, strings);
            return true;
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return commandCompletion((Player) commandSender, command, strings);
    }
}
