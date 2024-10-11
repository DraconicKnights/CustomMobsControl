package com.draconincdomain.custommobs.commands;

import com.draconincdomain.custommobs.core.Annotations.Commands;
import com.draconincdomain.custommobs.utils.Handlers.EditorModeManager;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import java.util.List;

@Commands
public class EditorCommand extends CommandCore{
    public EditorCommand() {
        super("editor", "custommob.admin", 0);
    }

    @Override
    protected void execute(Player player, String[] args) {
        if (!EditorModeManager.isInEditorMode(player)) {
            EditorModeManager.enterEditorMode(player);
        } else {
            EditorModeManager.exitEditorMode(player);
        }
    }

    @Override
    protected List<String> commandCompletion(Player player, Command command, String[] strings) {
        return null;
    }
}
