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
import java.util.UUID;

public abstract class CommandCore implements CommandExecutor, TabExecutor {

    protected String commandName;
    private boolean hasCooldown;
    protected HashMap<UUID, Long> cooldown = new HashMap<>();
    protected long cooldownDuration;

    public CommandCore(String cmdName, boolean hasCooldown) {
        CustomMobsControl.getInstance().getCommand(cmdName).setExecutor(this);
        this.commandName = cmdName;
        this.hasCooldown = hasCooldown;
    }

    protected abstract void execute(Player player, String[] args);
    protected abstract List<String> commandCompletion(Player player, Command command, String[] strings);

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (commandSender instanceof Player) {

            Player player = (Player) commandSender;

            if (hasCooldown) {
                if (cooldown.containsKey(player.getUniqueId())) {
                    long lastExecutionTime = cooldown.get(player.getUniqueId());
                    long currentTime = System.currentTimeMillis();
                    long timeRemaining = lastExecutionTime + cooldownDuration - currentTime;

                    if (timeRemaining > 0) {
                        player.sendMessage(ChatColor.RED + "Command is on cooldown. Please wait.");
                    }
                }
            }

            execute(player, strings);

            if (hasCooldown)
                cooldown.put(player.getUniqueId(), System.currentTimeMillis());

            return true;
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return commandCompletion((Player) commandSender, command, strings);
    }

}
