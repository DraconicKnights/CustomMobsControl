package com.draconincdomain.custommobs.runnables;

import com.draconincdomain.custommobs.CustomMobsControl;
import com.draconincdomain.custommobs.core.Annotations.Runnable;
import com.draconincdomain.custommobs.core.enums.RandomEvents;
import com.draconincdomain.custommobs.utils.Runnable.RunnableCore;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.block.data.type.Door;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * EventTriggerControl class represents an EventController event checker that executes random events based on escalation levels and player interactions.
 */
@Runnable
public class EventTriggerControl extends RunnableCore {

    private final double chance = 0.80;
    private int escalationLevel = 0;
    private final Map<UUID, Integer> playerInteractionMap = new HashMap<>();
    private final Map<UUID, Integer> escalationLevels = new HashMap<>();

    private final List<String[]> signLinesLibrary = List.of(
            new String[] {"Beware...", "The AI is watching"},
            new String[] {"Don't look back", "Something lurks in the dark"},
            new String[] {"You are not alone", "Eyes are on you"},
            new String[] {"Shadows move", "Beware the night"}
    );

    private final List<Location> mineshaftLocations = new ArrayList<>();

    public EventTriggerControl() {
        super(CustomMobsControl.getInstance(), 60, 45);
        this.startTimedTask();
    }

    @Override
    protected void event() {
        double roll = ThreadLocalRandom.current().nextDouble();
        if (roll < chance) {
            List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());

            if (!players.isEmpty()) {
                Player player = players.get(ThreadLocalRandom.current().nextInt(players.size()));
                int escalationLevel = escalationLevels.getOrDefault(player.getUniqueId(), 1);

                if (ThreadLocalRandom.current().nextDouble() < 0.5) {
                    escalationLevel = Math.min(escalationLevel + 1, 10); // Cap the escalation level
                } else {
                    escalationLevel = Math.max(escalationLevel - 1, 1); // Ensure escalation level does not go below 1
                }

                RandomEvents event = RandomEvents.values()[ThreadLocalRandom.current().nextInt(RandomEvents.values().length)];
                Bukkit.getLogger().info("Executing event: " + event.name() + " at escalation level " + escalationLevel);
                executeAIEvent(player, event, escalationLevel);
                escalationLevels.put(player.getUniqueId(), escalationLevel);
            }
        }
    }

    // Method to execute events based on the enum
    private void executeAIEvent(Player player, RandomEvents event, int escalationLevel) {
        switch (event) {
            case REPLACE_TORCH:
                replaceOutOfSightTorches(player, escalationLevel);
                break;
            case PLAY_SOUND:
                playRandomEerieSounds(player, escalationLevel);
                break;
            case ESCALATE_BEHAVIOR:
                escalateAIBehavior(player, escalationLevel);
                break;
            case PLAY_MUSIC_DISC:
                playCreepyMusicDisc(player, escalationLevel);
                break;
            case STRIKE_LIGHTNING:
                strikeLightning(player, escalationLevel);
                break;
            case GIVE_RANDOM_ITEM:
                giveRandomItem(player, escalationLevel);
                break;
            case TELEPORT_RANDOMLY:
                teleportRandomly(player, escalationLevel);
                break;
            case OPEN_NEARBY_DOOR:
                openNearbyDoor(player, escalationLevel);
                break;
            case BREAK_RANDOM_BLOCK:
                breakRandomBlockNearPlayer(player, escalationLevel);
                break;
            case PLACE_RANDOM_SIGN:
                placeRandomSignNearPlayer(player, escalationLevel);
                break;
            case CREATE_FIRE:
                createRandomFire(player, escalationLevel);
                break;
            case CHANGE_WEATHER:
                changeWeatherNearPlayer(player, escalationLevel);
                break;
            case CREATE_MINESHAFT_HOLE:
                createMineshaftHole(player, escalationLevel);
                break;
        }
    }

    // Replace torches out of the player's line of sight
    private void replaceOutOfSightTorches(Player player, int escalationLevel) {
        Location location = player.getLocation();
        int radius = 10 + (escalationLevel * 5);  // Increase radius based on escalation level

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    Location loc = location.clone().add(x, y, z);

                    if (loc.getBlock().getType() == Material.TORCH) {
                        if (!isInLineOfSight(player, loc)) {
                            loc.getBlock().setType(Material.REDSTONE_TORCH);
                            player.sendMessage(ChatColor.RED + "A torch near you has turned into a redstone torch!");
                        }
                    }
                }
            }
        }
    }

    private void createMineshaftHole(Player player, int escalationLevel) {
        World world = player.getWorld();
        int minDistance = 100;
        int maxDistance = 200;
        int minY = 30;
        int maxY = 50;

        // Find a location far from the player and underground
        Location mineshaftLoc;
        do {
            int x = ThreadLocalRandom.current().nextInt(-maxDistance, maxDistance);
            int z = ThreadLocalRandom.current().nextInt(-maxDistance, maxDistance);
            int y = ThreadLocalRandom.current().nextInt(minY, maxY);
            mineshaftLoc = player.getLocation().clone().add(x, 0, z);
            mineshaftLoc.setY(y);
        } while (mineshaftLoc.distance(player.getLocation()) < minDistance ||
                isTooCloseToExistingMineshaft(mineshaftLoc) ||
                isOnWater(mineshaftLoc) ||
                isSurfaceVisible(mineshaftLoc));

        mineshaftLocations.add(mineshaftLoc);

        int maxLength = 10;
        int minLength = 6;
        int length = ThreadLocalRandom.current().nextInt(minLength, maxLength + 1);
        int radius = 5 + (int) (escalationLevel * 1.5);
        int depth = 5;

        Location startLoc = mineshaftLoc.clone().add(0, depth, 0);

        Bukkit.getLogger().info("Creating mineshaft at: " + startLoc.toString());

        for (int i = 0; i < length; i++) {
            Location currentLoc = startLoc.clone().add(i, 0, 0);

            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    Location blockLoc = currentLoc.clone().add(0, y, z);
                    if (blockLoc.getBlock().getType() != Material.AIR) {
                        blockLoc.getBlock().setType(Material.AIR);
                    }
                }
            }

            if (ThreadLocalRandom.current().nextDouble() < 0.3) {
                Location blockageLoc = currentLoc.clone().add(ThreadLocalRandom.current().nextInt(-1, 2), 0, ThreadLocalRandom.current().nextInt(-1, 2));
                Material[] blockTypes = {Material.COBBLESTONE, Material.GRAVEL, Material.STONE};
                blockageLoc.getBlock().setType(blockTypes[ThreadLocalRandom.current().nextInt(blockTypes.length)]);
            }

            if (ThreadLocalRandom.current().nextDouble() < 0.3) {
                for (BlockFace face : BlockFace.values()) {
                    Location torchLoc = currentLoc.clone().add(face.getModX(), face.getModY(), face.getModZ());
                    if (torchLoc.getBlock().getType() == Material.AIR) {
                        Location wallBlockLoc = torchLoc.clone().add(face.getOppositeFace().getModX(), face.getOppositeFace().getModY(), face.getOppositeFace().getModZ());
                        if (wallBlockLoc.getBlock().getType() != Material.AIR) {
                            torchLoc.getBlock().setType(Material.REDSTONE_TORCH);
                            break;
                        }
                    }
                }
            }
        }

        Location endLoc = startLoc.clone().add(length - 1, 0, 0);
        Bukkit.getLogger().info("Mineshaft ends at: " + endLoc.toString());

        player.sendMessage(ChatColor.RED + "You notice a mineshaft that seems to have been recently abandoned, with cobblestone and other blockages scattered along the way...");
    }


    private void openNearbyDoor(Player player, int escalationLevel) {
        Location loc = player.getLocation();
        int radius = 10 + (escalationLevel * 5);

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    Location doorLoc = loc.clone().add(x, y, z);
                    Block block = doorLoc.getBlock();

                    if (block.getType() == Material.OAK_DOOR || block.getType() == Material.IRON_DOOR) {
                        Door door = (Door) block.getBlockData();
                        if (!door.isOpen()) {
                            door.setOpen(true);
                            block.setBlockData(door);
                            player.sendMessage(ChatColor.YELLOW + "A nearby door has mysteriously opened...");
                            return;
                        }
                    }
                }
            }
        }
    }

    private void breakRandomBlockNearPlayer(Player player, int escalationLevel) {
        Location loc = player.getLocation();
        int radius = 10 + (escalationLevel * 5);

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    Location blockLoc = loc.clone().add(x, y, z);
                    Block block = blockLoc.getBlock();

                    if (block.getType() != Material.AIR && block.getType().isOccluding()) {
                        if (!isInLineOfSight(player, blockLoc)) {
                            block.breakNaturally();
                            player.sendMessage(ChatColor.RED + "You hear the sound of blocks breaking nearby...");
                            return;
                        }
                    }
                }
            }
        }
    }

    private void placeRandomSignNearPlayer(Player player, int escalationLevel) {
        Location loc = player.getLocation();
        int radius = 10 + (int) (escalationLevel * 1.5);

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    Location signLoc = loc.clone().add(x, y, z);
                    Block block = signLoc.getBlock();

                    // Ensure sign is placed on top of a solid block
                    Block belowBlock = signLoc.clone().subtract(0, 1, 0).getBlock();
                    if (block.getType() == Material.AIR && belowBlock.getType().isSolid() && !isInLineOfSight(player, signLoc)) {
                        block.setType(Material.OAK_SIGN);
                        Sign sign = (Sign) block.getState();

                        // Select random lines from the library
                        String[] randomLines = signLinesLibrary.get(ThreadLocalRandom.current().nextInt(signLinesLibrary.size()));
                        sign.setLine(0, ChatColor.DARK_PURPLE + randomLines[0]);
                        sign.setLine(1, ChatColor.DARK_RED + randomLines[1]);
                        sign.update();
                        player.sendMessage(ChatColor.DARK_PURPLE + "A strange sign appears nearby...");
                        return;
                    }
                }
            }
        }
    }

    // Event: Create fire near the player
    private void createRandomFire(Player player, int escalationLevel) {
        Location loc = player.getLocation();
        int radius = 10 + (int) (escalationLevel * 1.5);
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    Location fireLoc = loc.clone().add(x, y, z);
                    Block block = fireLoc.getBlock();
                    if (block.getType() == Material.AIR && !isInLineOfSight(player, fireLoc)) {
                        block.setType(Material.FIRE);
                        player.sendMessage(ChatColor.RED + "You see flames erupting nearby!");
                        return;
                    }
                }
            }
        }
    }

    // Event: Change the weather around the player
    private void changeWeatherNearPlayer(Player player, int escalationLevel) {
        World world = player.getWorld();
        WeatherType newWeather = (escalationLevel > 5) ? WeatherType.CLEAR : WeatherType.DOWNFALL;
        world.setStorm(newWeather == WeatherType.DOWNFALL);
        world.setThundering(newWeather == WeatherType.DOWNFALL);
        player.sendMessage(ChatColor.GRAY + "The weather around you changes abruptly...");
    }


    // Play random eerie sounds around the player
    private void playRandomEerieSounds(Player player, int escalationLevel) {
        Sound sound = escalationLevel > 3 ? Sound.ENTITY_WITHER_AMBIENT : Sound.AMBIENT_CAVE;
        float pitch = 1.0f - (escalationLevel * 0.1f);
        player.playSound(player.getLocation(), sound, 1, pitch);
        player.sendMessage(ChatColor.DARK_PURPLE + "You hear eerie sounds...");
    }

    private void escalateAIBehavior(Player player, int escalationLevel) {
        int interactions = playerInteractionMap.getOrDefault(player.getUniqueId(), 0);
        interactions += escalationLevel;  // Escalates faster as the level increases
        playerInteractionMap.put(player.getUniqueId(), interactions);
        player.sendMessage(ChatColor.GOLD + "AI interaction level increased to: " + interactions);
    }

    private void playCreepyMusicDisc(Player player, int escalationLevel) {
        Sound disc = escalationLevel > 5 ? Sound.MUSIC_DISC_11 : Sound.MUSIC_DISC_13;
        player.playSound(player.getLocation(), disc, 1, 0.5f);
        player.sendMessage(ChatColor.DARK_RED + "You hear unsettling music...");
    }

    private void strikeLightning(Player player, int escalationLevel) {
        Location loc = player.getLocation();
        for (int i = 0; i < escalationLevel; i++) {  // Strike lightning multiple times based on escalation level
            loc.getWorld().strikeLightningEffect(loc.add(ThreadLocalRandom.current().nextInt(-5, 5), 0, ThreadLocalRandom.current().nextInt(-5, 5)));
        }
        player.sendMessage(ChatColor.YELLOW + "Lightning strikes crash all around you!");
    }

    private void giveRandomItem(Player player, int escalationLevel) {
        Material[] items = {Material.DIAMOND, Material.GOLD_INGOT, Material.EMERALD};
        for (int i = 0; i < escalationLevel; i++) {  // Give more items as escalation level increases
            Material randomItem = items[ThreadLocalRandom.current().nextInt(items.length)];
            player.getInventory().addItem(new ItemStack(randomItem));
        }
        player.sendMessage(ChatColor.GREEN + "You receive mysterious items!");
    }

    private void teleportRandomly(Player player, int escalationLevel) {
        Location loc = player.getLocation();
        int randomX = ThreadLocalRandom.current().nextInt(-10 * escalationLevel, 10 * escalationLevel);
        int randomZ = ThreadLocalRandom.current().nextInt(-10 * escalationLevel, 10 * escalationLevel);
        Location teleportLocation = loc.clone().add(randomX, 0, randomZ);
        teleportLocation.setY(teleportLocation.getWorld().getHighestBlockYAt(teleportLocation));

        player.teleport(teleportLocation);
        player.sendMessage(ChatColor.AQUA + "You have been teleported to a far-off location!");
    }

    private boolean isSurfaceVisible(Location location) {
        // Check if the mineshaft is exposed to the surface
        int highestBlockY = location.getWorld().getHighestBlockYAt(location);
        return location.getY() >= highestBlockY;  // Mineshaft is too close to the surface
    }

    private boolean isOnWater(Location location) {
        // Check if the block below the location is water
        return location.getBlock().getRelative(BlockFace.DOWN).getType() == Material.WATER;
    }

    private boolean isTooCloseToExistingMineshaft(Location location) {
        for (Location mineshaftLoc : mineshaftLocations) {
            if (location.distance(mineshaftLoc) < 50) { // 50 blocks distance
                return true;
            }
        }
        return false;
    }

    private boolean isInLineOfSight(Player player, Location targetLocation) {
        // Perform a ray trace from the player's eye location to the target block location
        Location eyeLocation = player.getEyeLocation();
        Vector direction = targetLocation.toVector().subtract(eyeLocation.toVector()).normalize();

        // Step through the line between the player and the target location to check for obstructions
        for (double distance = 0; distance <= eyeLocation.distance(targetLocation); distance += 0.5) {
            Location step = eyeLocation.clone().add(direction.clone().multiply(distance));
            Block block = step.getBlock();

            if (block.getType().isOccluding()) {
                return false;
            }
        }
        return true;  // No obstructions, line of sight is clear
    }


    // Track player interaction count for escalation
    private int getPlayerInteractionCount(Player player) {
        return playerInteractionMap.getOrDefault(player.getUniqueId(), 0);
    }

    // Update player interaction count
    private void updatePlayerInteractionCount(Player player, int count) {
        playerInteractionMap.put(player.getUniqueId(), count);
    }
}
