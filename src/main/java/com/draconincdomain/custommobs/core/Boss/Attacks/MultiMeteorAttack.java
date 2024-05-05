package com.draconincdomain.custommobs.core.Boss.Attacks;

import com.draconincdomain.custommobs.CustomMobsControl;
import com.draconincdomain.custommobs.core.Boss.SpecialAttack;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MultiMeteorAttack extends SpecialAttack {
    public MultiMeteorAttack(LivingEntity target) {
        super(target);
    }

    @Override
    protected void executeAttack() {
        List<Player> nearbyPlayers = getAllNearbyPlayers(target.getLocation(), 20);

        for (Player player : nearbyPlayers) {
            target.setInvulnerable(true);  // Make the mob invincible until all meteors have landed

            final int numOfMeteors = 20;  // Number of meteors to spawn
            final BukkitRunnable[] meteorTasks = new BukkitRunnable[numOfMeteors];
            final List<Fireball> meteors = new ArrayList<>();

            for (int i = 0; i < numOfMeteors; i++) {
                final double fireballHeight = player.getLocation().getY() + 20 + new Random().nextInt(11);
                final Location fireballLocation = player.getLocation().clone().add(0, fireballHeight, 0);
                final Fireball fireball = player.getWorld().spawn(fireballLocation, Fireball.class);
                meteors.add(fireball);

                final Vector direction = player.getLocation().toVector().subtract(fireballLocation.toVector()).normalize();
                fireball.setDirection(direction);  // Point the fireball downward
                player.sendTitle("§cBOSS ACTION", "§6Boss has used Multi-Meteor Storm!", 10, 70, 20);  // Alert player about the event

                // Spawn particles periodically
                meteorTasks[i] = new BukkitRunnable() {

                    double targetvar = 0;
                    Location location, first, second, third, fourth;
                    @Override
                    public void run() {
                        // Stop this runnable and remove the meteor from the meteors list when it hits the ground
                        if (fireball.isDead()) {
                            meteors.remove(fireball);
                            this.cancel();

                            // If there are no meteors left, make the mob vulnerable again
                            if (meteors.isEmpty()) {
                                target.setInvulnerable(false);
                            }
                        }

                        targetvar += Math.PI / 16;

                        location = target.getLocation();
                        first = location.clone().add(Math.cos(targetvar), Math.sin(targetvar) + 1, Math.sin(targetvar));
                        second = location.clone().add(Math.cos(targetvar + Math.PI), Math.sin(targetvar) + 1, Math.sin(targetvar + Math.PI));
                        third = location.clone().add( Math.sin(targetvar + Math.PI), Math.cos(targetvar) + 1, Math.sin(targetvar));
                        fourth = location.clone().add(Math.cos(targetvar), Math.cos(targetvar) + 1, Math.sin(targetvar + Math.PI));

                        player.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, first, 0);
                        player.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, second, 0);
                        player.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, third, 0);
                        player.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, fourth, 0);
                    }
                };
                meteorTasks[i].runTaskTimer(CustomMobsControl.getInstance(), 0L, 1L);  // Start immediately, repeat every tick

                new BukkitRunnable() {
                    double targetvar = 0;
                    Location location, first, second;
                    @Override
                    public void run() {
                        // Check if the fireball has hit the ground
                        if (fireball.isDead()) {
                            this.cancel();
                        }

                        targetvar += Math.PI / 16;

                        location = fireball.getLocation();
                        first = location.clone().add(Math.cos(targetvar), Math.sin(targetvar) + 1, Math.sin(targetvar));
                        second = location.clone().add(Math.cos(targetvar + Math.PI), Math.sin(targetvar) + 1, Math.sin(targetvar + Math.PI));

                        player.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, first, 0);
                        player.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, second, 0);

                        // Spawn particles at the fireball's location
                        player.getWorld().spawnParticle(Particle.FLAME, fireball.getLocation(), 10);
                    }
                }.runTaskTimer(CustomMobsControl.getInstance(), 0L, 1L);
            }
        }
    }
    protected List<Player> getAllNearbyPlayers(Location location, int radius) {
        List<Player> nearbyPlayers = new ArrayList<>();
        for (Player player : location.getWorld().getPlayers()) {
            if (player.getLocation().distance(location) <= radius) {
                nearbyPlayers.add(player);
            }
        }
        return nearbyPlayers;
    }
}
