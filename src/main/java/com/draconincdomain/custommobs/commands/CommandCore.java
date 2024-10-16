package com.draconincdomain.custommobs.commands;

import com.draconincdomain.custommobs.CustomMobsControl;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * CommandCore, Used as a base for all in-game plugins commands with built in cooldown system and perm check
 */
public abstract class CommandCore implements CommandExecutor, TabExecutor {
    protected String commandName;
    protected String permission;
    protected int cooldownDuration;
    protected Map<UUID, Long> cooldowns = new HashMap<>();

    public CommandCore(String cmdName, @Nullable String permission, int cooldown) {
        CustomMobsControl.getInstance().getCommand(cmdName).setExecutor(this);
        this.commandName = cmdName;
        this.permission = permission;
        this.cooldownDuration = cooldown;
    }

    protected abstract void execute(Player player, String[] args);
    protected abstract List<String> commandCompletion(Player player, Command command, String[] strings);

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (commandSender instanceof Player) {

            Player player = (Player) commandSender;

            if (!player.hasPermission(this.permission)) {
                player.sendMessage(ChatColor.RED + "You don't have permission to use this command, please contact a server administrator");
                return true;
            }

            UUID playerID = player.getUniqueId();
            if (cooldowns.containsKey(playerID)) {
                long cooldownEnds = cooldowns.get(playerID);
                if (cooldownEnds > System.currentTimeMillis()) {
                    return true;
                }
            }
            cooldowns.put(playerID, System.currentTimeMillis() + cooldownDuration * 1000);

            execute(player, strings);
            return true;
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return commandCompletion((Player) commandSender, command, strings);
    }

}