package com.draconincdomain.custommobs.commands;

import com.draconincdomain.custommobs.core.Annotations.Commands;
import com.draconincdomain.custommobs.core.BossMob;
import com.draconincdomain.custommobs.core.CustomMobManager;
import com.draconincdomain.custommobs.utils.Arrays.CustomEntityArrayHandler;
import com.draconincdomain.custommobs.utils.Desing.ColourCode;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import java.util.List;

@Commands
public class SpawnBossMob extends CommandCore{
    public SpawnBossMob() {
        super("spawnboss", 0);
    }

    @Override
    protected void execute(Player player, String[] args) {
        if (args.length == 1) {
            String mobName = args[0];
            BossMob[] bossMobs = CustomEntityArrayHandler.getRegisteredBossMobs().values().toArray(new BossMob[0]);

            for (BossMob bossMob : bossMobs) {
                if (bossMob.getMobNameID().equals(mobName)) {
                    CustomMobManager.getInstance().setMobLevelAndSpawn(player, bossMob, player.getLocation());
                    player.sendMessage(ColourCode.colour("&5[CustomMobControl]: Spawned " + bossMob.getName()));
                }
            }
        }
    }

    @Override
    protected List<String> commandCompletion(Player player, Command command, String[] strings) {
        return null;
    }
}
