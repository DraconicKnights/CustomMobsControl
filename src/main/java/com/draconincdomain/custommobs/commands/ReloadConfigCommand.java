package com.draconincdomain.custommobs.commands;

import com.draconincdomain.custommobs.CustomMobsControl;
import com.draconincdomain.custommobs.utils.DataHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ReloadConfigCommand extends CommandCore {

    public ReloadConfigCommand() {
        super("customreload");
    }

    @Override
    protected void execute(Player player, String[] args) {

        DataHandler.getInstance().ReloadMobsConfig();

        player.sendMessage(ChatColor.RED + "Reloading mob values");
    }

    @Override
    protected List<String> commandCompletion(Player player, Command command, String[] strings) {

        return null;
    }

}
