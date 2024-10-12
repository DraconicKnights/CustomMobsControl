package com.draconincdomain.custommobs.runnables;

import com.draconincdomain.custommobs.CustomMobsControl;
import com.draconincdomain.custommobs.core.Annotations.Runnable;
import com.draconincdomain.custommobs.core.enums.LoggerLevel;
import com.draconincdomain.custommobs.utils.Runnable.RunnableCore;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.SkinTrait;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Represents an event that observes a player and spawns NPCs as watchers.
 * NPCs will spawn randomly around the player and perform random actions if they are being watched.
 * The event has a chance to trigger on a timed basis.
 *
 * @see RunnableCore
 */
public class ObservePlayerEvent extends RunnableCore {
    private final double chance = 0.99;  // Probability of event happening
    private final int minDistance = 50;  // Minimum distance NPC spawns from player
    private final int maxDistance = 100; // Maximum distance NPC spawns from player
    private final int maxVisibleTime = 200; // Maximum time NPC stays visible (in ticks)
    private final Set<NPC> activeWatchers = new HashSet<>();

    public ObservePlayerEvent() {
        super(CustomMobsControl.getInstance(), 60, 15);
        this.startTimedTask();
    }

    @Override
    protected void event() {
        // Random chance to trigger event
        double roll = ThreadLocalRandom.current().nextDouble();
        if (roll > chance) return;

        // Get a random player from online players
        List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
        if (players.isEmpty()) return;

        Player targetPlayer = players.get(ThreadLocalRandom.current().nextInt(players.size()));
        spawnWatcher(targetPlayer);
    }

    private void spawnWatcher(Player player) {
        Location playerLoc = player.getLocation();
        Location spawnLoc;

        // Random location around the player, ensuring solid ground and appropriate distance
        do {
            int x = ThreadLocalRandom.current().nextInt(-maxDistance, maxDistance);
            int z = ThreadLocalRandom.current().nextInt(-maxDistance, maxDistance);
            int y = playerLoc.getWorld().getHighestBlockYAt(playerLoc.clone().add(x, 0, z));  // Highest block ensures it's above ground
            spawnLoc = new Location(playerLoc.getWorld(), playerLoc.getX() + x, y, playerLoc.getZ() + z);

            // Ensure there's air above the ground for safe NPC spawn
            Block blockAbove = spawnLoc.getBlock().getRelative(BlockFace.UP);
        } while (spawnLoc.distance(player.getLocation()) < minDistance
                || !isSafeSpawnLocation(spawnLoc));

        // Ensure NPC is above ground and facing the player
        NPC npc = CitizensAPI.getNPCRegistry().createNPC(org.bukkit.entity.EntityType.PLAYER, " ");
        npc.spawn(spawnLoc);

        // Set the NPC skin using SkinTrait
        SkinTrait skin = npc.getOrAddTrait(SkinTrait.class);
        skin.setSkinName(player.getName());  // Replace with the specific skin you want

        // Rotate NPC to face the player
        facePlayer(npc, player);

        CustomMobsControl.getInstance().CustomMobLogger("A watcher has spawned nearby", LoggerLevel.INFO);

        activeWatchers.add(npc);

        // Schedule NPC removal after a set period if not spotted
        new BukkitRunnable() {
            @Override
            public void run() {
                if (npc.isSpawned()) {
                    npc.despawn();
                    CitizensAPI.getNPCRegistry().deregister(npc); // Deregister the NPC
                    activeWatchers.remove(npc);
                }
            }
        }.runTaskLater(CustomMobsControl.getInstance(), maxVisibleTime);

        // Check if NPC is being watched by the player
        new BukkitRunnable() {
            @Override
            public void run() {
                checkWatcher(player, npc);
            }
        }.runTaskTimer(CustomMobsControl.getInstance(), 0, 20);  // Check every second (20 ticks)
    }

    private void checkWatcher(Player player, NPC npc) {
        if (isLookingAt(player, npc)) {
            performRandomAction(npc, player);
        }
    }

    private void performRandomAction(NPC npc, Player player) {
        int action = ThreadLocalRandom.current().nextInt(3); // Random action selection

        switch (action) {
            case 0:
                runAway(npc); // Run away
                break;
            case 1:
                moveTowardPlayer(npc, player); // Move toward player
                break;
            case 2:
                hideBehindBlock(npc); // Hide behind a block
                break;
            default:
                runAway(npc); // Default to running away
                break;
        }

        // Schedule NPC removal after action is completed
        new BukkitRunnable() {
            @Override
            public void run() {
                if (npc.isSpawned()) {
                    npc.despawn();
                    CitizensAPI.getNPCRegistry().deregister(npc); // Deregister the NPC to ensure it's fully removed
                    activeWatchers.remove(npc);
                }
            }
        }.runTaskLater(CustomMobsControl.getInstance(), 100);  // Remove after 5 seconds
    }

    private void runAway(NPC npc) {
        // Make the NPC run away to a distant location
        npc.getNavigator().setTarget(npc.getEntity().getLocation().add(30, 0, 30));
    }

    private void moveTowardPlayer(NPC npc, Player player) {
        // Make the NPC walk towards the player
        npc.getNavigator().setTarget(player.getLocation());
    }

    private void hideBehindBlock(NPC npc) {
        // Find a nearby block to hide behind
        Location currentLoc = npc.getEntity().getLocation();
        Location hideLoc = currentLoc.add(ThreadLocalRandom.current().nextInt(-10, 10), 0, ThreadLocalRandom.current().nextInt(-10, 10));

        if (isSolidGround(hideLoc)) {
            npc.getNavigator().setTarget(hideLoc);
        }
    }

    private boolean isLookingAt(Player player, NPC npc) {

        if (!npc.isSpawned() || npc.getEntity() == null) {
            return false; // NPC is not present, return false
        }

        Location eyeLocation = player.getEyeLocation();
        Location npcLocation = npc.getEntity().getLocation();
        Vector toNpc = npcLocation.toVector().subtract(eyeLocation.toVector());
        double angle = eyeLocation.getDirection().angle(toNpc);

        return angle < Math.toRadians(20);  // 20-degree field of view
    }

    private boolean isSafeSpawnLocation(Location location) {
        Block block = location.getBlock();
        Block blockAbove = block.getRelative(BlockFace.UP);

        // Ensure ground block is solid and space above it is air
        return block.getType().isSolid() && blockAbove.getType().isAir();
    }

    private boolean isSolidGround(Location location) {
        return location.getBlock().getType().isSolid() && location.clone().add(0, 1, 0).getBlock().isEmpty();
    }

    private void facePlayer(NPC npc, Player player) {
        Location npcLoc = npc.getEntity().getLocation();
        Location playerLoc = player.getLocation();

        Vector direction = playerLoc.toVector().subtract(npcLoc.toVector()).normalize();
        npcLoc.setDirection(direction);  // Set NPC facing the player

        npc.getEntity().teleport(npcLoc);  // Apply the direction to the NPC
    }

}
