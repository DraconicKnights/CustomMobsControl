package com.draconincdomain.custommobs.commands;

import com.draconincdomain.custommobs.core.Annotations.Commands;
import com.draconincdomain.custommobs.utils.Data.MobDataHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import java.util.List;

@Commands
public class ReloadConfigCommand extends CommandCore {

    public ReloadConfigCommand() {
        super("customreload","custom.admin", 0);
    }

    @Override
    protected void execute(Player player, String[] args) {

        MobDataHandler.getInstance().ReloadMobsConfig();

        player.sendMessage(ChatColor.RED + "Reloading mob values");
    }

    @Override
    protected List<String> commandCompletion(Player player, Command command, String[] strings) {

        return null;
    }

}
