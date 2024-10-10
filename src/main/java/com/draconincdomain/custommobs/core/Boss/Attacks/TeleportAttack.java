package com.draconincdomain.custommobs.core.Boss.Attacks;

import com.draconincdomain.custommobs.CustomMobsControl;
import com.draconincdomain.custommobs.core.Boss.SpecialAttack;
import com.draconincdomain.custommobs.core.enums.LoggerLevel;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Random;

/**
 * Teleportation Attack, will randomly teleport to another location within a near distance
 * Will play particles and sounds on the player
 */
public class TeleportAttack extends SpecialAttack {
    private static Random random = new Random();
    public TeleportAttack(LivingEntity target) {
        super(target);
    }

    @Override
    protected void executeAttack() {
        Player randomNearbyPlayer = getRandomNearbyPlayer(target.getLocation(), 20);

        if (randomNearbyPlayer != null) {
            CustomMobsControl.getInstance().CustomMobLogger("Found a nearby player. Preparing to teleport...", LoggerLevel.INFO);

            Location oldLocation = target.getLocation();
            Location newLocation = randomNearbyPlayer.getLocation();

            // Teleport to player
            target.teleport(newLocation);
            CustomMobsControl.getInstance().CustomMobLogger("Teleported!", LoggerLevel.INFO);

            // Play end teleport sound
            randomNearbyPlayer.playSound(newLocation, Sound.ENTITY_ENDERMAN_TELEPORT, 1.0F, 1.0F);

            // Spawn particles along the path from the old location to the new one
            oldLocation.getWorld().spawnParticle(Particle.REDSTONE, oldLocation, 100, new Particle.DustOptions(Color.PURPLE, 1));
            CustomMobsControl.getInstance().CustomMobLogger("Particles and sound played!", LoggerLevel.INFO);
        } else {
            CustomMobsControl.getInstance().CustomMobLogger("No nearby players found.", LoggerLevel.INFO);
        }
    }


    private Player getRandomNearbyPlayer(Location location, int radius) {
        Collection<Entity> nearbyEntities = target.getNearbyEntities(radius, radius, radius);
        return nearbyEntities.stream()
                .filter(entity -> entity instanceof Player)
                .map(entity -> (Player) entity)
                .skip(random.nextInt(nearbyEntities.size()))
                .findFirst()
                .orElse(null);
    }
}
