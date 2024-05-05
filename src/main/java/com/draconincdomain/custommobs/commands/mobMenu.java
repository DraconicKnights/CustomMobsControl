package com.draconincdomain.custommobs.commands;

import com.draconincdomain.custommobs.core.Annotations.Commands;
import com.draconincdomain.custommobs.core.Inventory.MobGUI.Menu;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import java.util.List;

@Commands
public class mobMenu extends CommandCore{
    public mobMenu() {
        super("mobmenu", 0);
    }

    @Override
    protected void execute(Player player, String[] args) {
        Menu menu = new Menu();

        menu.open(player);
    }

    @Override
    protected List<String> commandCompletion(Player player, Command command, String[] strings) {
        return null;
    }
}
