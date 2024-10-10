package com.draconincdomain.custommobs.commands;

import com.draconincdomain.custommobs.core.Annotations.Commands;
import com.draconincdomain.custommobs.core.Player.PlayerData;
import com.draconincdomain.custommobs.core.Player.PlayerDataManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Level XP Command used for setting the custom plugin XP value
 */
@Commands
public class LevelXPCommand extends CommandCore{
    public LevelXPCommand() {
        super("levelxp", "custom.admin",0);
    }

    @Override
    protected void execute(Player player, String[] args) {
        if (args.length < 2 || args.length > 3) {
            player.sendMessage(ChatColor.RED + "Invalid usage: /levelxp setxp|addxp <player optional> <value>");
            return;
        }

        int firstArgOffset = 0;
        String command = args[0];
        Player targetPlayer;

        if (Bukkit.getPlayer(args[1]) != null) {
            targetPlayer = Bukkit.getPlayer(args[1]);
            firstArgOffset = 1;
        } else {
            targetPlayer = player;
        }

        int value = Integer.parseInt(args[1 + firstArgOffset]);

        PlayerData targetPlayerData = PlayerDataManager.getInstance().getPlayerData(targetPlayer.getUniqueId());

        if (targetPlayerData != null) {
            switch (command) {
                case "setxp":
                    targetPlayerData.setXp(value, targetPlayer);
                    player.sendMessage(ChatColor.GREEN + targetPlayer.getName() + "'s XP set to " + value);
                    break;
                case "addxp":
                    targetPlayerData.addXp(value, targetPlayer, 1);
                    player.sendMessage(ChatColor.GREEN + "Added " + value + " XP to " + targetPlayer.getName());
                    break;
                default:
                    player.sendMessage(ChatColor.RED + "Invalid command: use setxp, addxp or setlevel");
            }
        } else {
            player.sendMessage(ChatColor.RED + "Failed to fetch PlayerData for " + targetPlayer.getName());
        }
    }

    @Override
    protected List<String> commandCompletion(Player player, Command command, String[] strings) {
        return null;
    }
}
