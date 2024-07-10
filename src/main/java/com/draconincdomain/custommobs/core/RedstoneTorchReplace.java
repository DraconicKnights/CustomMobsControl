package com.draconincdomain.custommobs.core;

import com.draconincdomain.custommobs.CustomMobsControl;
import com.draconincdomain.custommobs.core.Annotations.Runnable;
import com.draconincdomain.custommobs.utils.Runnable.RunnableCore;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Runnable
public class RedstoneTorchReplace extends RunnableCore {

    private final double chance = 0.60;

    public RedstoneTorchReplace() {
        super(CustomMobsControl.getInstance(), 60, 120);
        this.startTimedTask();
    }

    @Override
    protected void event() {
        double roll = ThreadLocalRandom.current().nextDouble();
        if (roll < chance) {

        }
        List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());

        if (!players.isEmpty()) {
            // Pick a random player
            Player player = players.get(ThreadLocalRandom.current().nextInt(players.size()));

            // Replace torches out of sight
            replaceOutOfSightTorches(player);

            player.playSound(player.getLocation(), Sound.AMBIENT_CAVE, 1, 1);
        }
    }

    private void replaceOutOfSightTorches(Player player) {
        Location location = player.getLocation();
        int radius = 15;

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    Location loc = location.clone().add(x, y, z);

                    if (loc.getBlock().getType() == Material.TORCH) {
                        Vector toBlock = loc.toVector().subtract(player.getEyeLocation().toVector());
                        Block losBlock = player.getEyeLocation().add(toBlock.normalize()).getBlock();
                        if (!losBlock.getType().isOccluding()) {
                            loc.getBlock().setType(Material.REDSTONE_TORCH);
                        }
                        loc.getBlock().setType(Material.REDSTONE_TORCH);
                    }
                }
            }
        }
    }
}
