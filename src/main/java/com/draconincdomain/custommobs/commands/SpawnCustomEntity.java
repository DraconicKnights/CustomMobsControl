package com.draconincdomain.custommobs.commands;

import com.draconincdomain.custommobs.core.Annotations.Commands;
import com.draconincdomain.custommobs.core.CustomMob;
import com.draconincdomain.custommobs.utils.ColourCode;
import com.draconincdomain.custommobs.utils.CustomEntityArrayHandler;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@Commands
public class SpawnCustomEntity extends CommandCore {

    public SpawnCustomEntity() {
        super("custommob");
    }

    @Override
    protected void execute(Player player, String[] args) {

        if (args.length == 4) {

            String mobName = args[0];
            CustomMob[] customMobs = CustomEntityArrayHandler.getRegisteredCustomMobs().values().toArray(new CustomMob[0]);

            double x = Double.parseDouble(args[1]);
            double y = Double.parseDouble(args[2]);
            double z = Double.parseDouble(args[3]);

            Location location = new Location(player.getWorld(), x, y, z);

            for (CustomMob customMob : customMobs) {
                if (customMob.getMobNameID().equals(mobName)) {
                    customMob.spawnEntity(location);
                    player.sendMessage(ColourCode.colour("&5[CustomMobControl]: Spawned " + customMob.getName()));
                }
            }
        }

        if (args.length == 1) {
            String mobName = args[0];
            CustomMob[] customMobs = CustomEntityArrayHandler.getRegisteredCustomMobs().values().toArray(new CustomMob[0]);

            for (CustomMob customMob : customMobs) {
                if (customMob.getMobNameID().equals(mobName)) {
                    customMob.spawnEntity(player.getLocation());
                    player.sendMessage(ColourCode.colour("&5[CustomMobControl]: Spawned " + customMob.getName()));
                }
            }
        }

    }

    @Override
    protected List<String> commandCompletion(Player player, Command command, String[] strings) {

        List<String> completion = new ArrayList<>();

        CustomMob[] customMobs = CustomEntityArrayHandler.getRegisteredCustomMobs().values().toArray(new CustomMob[0]);

        if (command.getName().equalsIgnoreCase(commandName)) {
            if (strings.length == 1) {
                for (CustomMob customMob : customMobs) {
                    completion.add(customMob.getMobNameID());
                }
            }

            if (strings.length == 2) {
                completion.add("x");
            }

            if (strings.length == 3) {
                completion.add("y");
            }

            if (strings.length == 4) {
                completion.add("Z");
            }

        }

        return completion;
    }

}
