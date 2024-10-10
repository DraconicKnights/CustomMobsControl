package com.draconincdomain.custommobs.commands;

import com.draconincdomain.custommobs.core.Annotations.Commands;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

/**
 * Vanish command used to hide the player for others
 */
@Commands
public class Vanish extends CommandCore {

    private HashSet<UUID> vanishedPlayers = new HashSet<>();
    public Vanish() {
        super("vanish", "custom.admin", 0);
    }

    @Override
    protected void execute(Player player, String[] args) {

        if (args.length < 0 || args.length > 1) {
            player.sendMessage(ChatColor.RED + "Invalid usage. Usage: /vanish [player]");
            return;
        }


        if (args.length == 0) {
            ToggleVanish(player, vanishedPlayers.contains(player.getUniqueId()));
        }

        if (args.length == 1) {
            Player targetPlayer = Bukkit.getPlayerExact(args[0]);
            if (targetPlayer == null) {
                player.sendMessage(ChatColor.RED + "Cannot find player " + args[0]);
                return;
            }
            ToggleVanish(targetPlayer, vanishedPlayers.contains(targetPlayer.getUniqueId()));
        }
    }

    @Override
    protected List<String> commandCompletion(Player player, Command command, String[] strings) {
        return null;
    }

    private void ToggleVanish(Player player, boolean value) {

        if (value) {
            for (Player otherPlayers : Bukkit.getOnlinePlayers()) {
                otherPlayers.showPlayer(player);
                vanishedPlayers.add(player.getUniqueId());
            }
        } else {
            for (Player otherPlayers : Bukkit.getOnlinePlayers()) {
                otherPlayers.showPlayer(player);
                vanishedPlayers.add(player.getUniqueId());
            }
        }
    }
}
