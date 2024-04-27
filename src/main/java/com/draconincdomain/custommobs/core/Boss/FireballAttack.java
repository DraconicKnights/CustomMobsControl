package com.draconincdomain.custommobs.core.Boss;

import com.draconincdomain.custommobs.CustomMobsControl;
import com.draconincdomain.custommobs.utils.Runnable.RunnableCore;
import org.bukkit.Location;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class FireballAttack extends RunnableCore {

    protected LivingEntity target;

    public FireballAttack(LivingEntity target) {
        super(CustomMobsControl.getInstance(), 60, 60);
        this.target = target;
    }

    @Override
    protected void event() {
        if(target == null || target.isDead()) {
            this.stop();
        } else {
            Player randomNearbyPlayer = getRandomNearbyPlayer(target.getLocation(), 20);

            // Check if randomNearbyPlayer is not null
            if (randomNearbyPlayer != null && randomNearbyPlayer.getWorld() != null) {
                Fireball fireball = randomNearbyPlayer.getWorld().spawn(randomNearbyPlayer.getLocation().add(0, 1, 0), Fireball.class);
                fireball.setDirection(randomNearbyPlayer.getLocation().getDirection());
            }
        }
    }

    protected Player getRandomNearbyPlayer(Location location, double range) {
        // Get all players within the specified range
        List<Player> nearbyPlayers = location.getWorld().getPlayers().stream()
                .filter(player -> player.getLocation().distance(location) <= range)
                .collect(Collectors.toList());

        // Return a random player from the list
        if (!nearbyPlayers.isEmpty()) {
            Random random = new Random();
            return nearbyPlayers.get(random.nextInt(nearbyPlayers.size()));
        }

        // If no players were found, return null
        return null;
    }
}
