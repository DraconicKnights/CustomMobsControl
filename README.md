# Title: CustomMobControl

## Java Plugin Project Repo

A custom MC plugin that creates and handles custom mob data

## Work in Progress

## Features

## Custom Mob Mobs YML contents and Examples

```yml
#If enabled custom mobs will be able to spawn
customMobsEnabled: true

#The spawn distance for the custom mobs
spawningDistanceMin: 25
spawningDistanceMax: 50

#The Default Custom Mobs, Add a new mob starting with name and all fields are required!
customMobs:
  - skeletonFlameArcher:
    name: "&6Skeleton Flame Archer"
    mobID: skeletonFlameArcher
    champion: false
    maxHealth: 20.0
    spawnChance: 45
    entityType: SKELETON
    weapon:
      material: BOW
      amount: 1
      enchanted: true
      enchantments:
        - ARROW_FIRE
        - ARROW_DAMAGE
      enchantmentLevels:
        - 1
        - 3
      glow: false
      unbreakable: true
      hide: true
      weaponName: "&eFlaming Bow"
      weaponLore: "Infused with the power of fire."
    weaponDropChance: 2.0
    hasArmour: true
    armour:
      - CHAINMAIL_BOOTS
      - CHAINMAIL_LEGGINGS
    lootDrops:
      - item: 'SKELETON_SKULL'
        count: 1
        dropChance: 0.05
      - item: 'BONE'
        count: 3
        dropChance: 0.5

  - skeletonFlameAttacker:
    name: "&6Skeleton Flame Attacker"
    mobID: skeletonFlameAttacker
    champion: false
    maxHealth: 30.0
    spawnChance: 45
    entityType: SKELETON
    weapon:
      material: NETHERITE_SWORD
      amount: 1
      enchanted: true
      enchantments:
        - FIRE_ASPECT
        - SHARPNESS
      enchantmentLevels:
        - 2
        - 3
      glow: false
      unbreakable: true
      hide: true
      weaponName: "&eBlazing Sword"
      weaponLore: "Sword with a burning touch."
    weaponDropChance: 1.0
    hasArmour: true
    armour:
      - CHAINMAIL_BOOTS
      - CHAINMAIL_CHESTPLATE
    lootDrops:
      - item: 'SKELETON_SKULL'
        count: 1
        dropChance: 0.05
      - item: 'BONE'
        count: 3
        dropChance: 0.5

  - creeperMiner:
    name: "&6Creeper Miner"
    mobID: creeperMiner
    champion: false
    maxHealth: 15.0
    spawnChance: 30
    entityType: CREEPER
    weapon:
      material: STONE_PICKAXE
      amount: 1
      enchanted: true
      enchantments:
        - DIG_SPEED
      enchantmentLevels:
        - 3
      glow: false
      unbreakable: true
      hide: true
      weaponName: "&eMiner's Foe"
      weaponLore: "Creeper's choice tool."
    weaponDropChance: 1.0
    hasArmour: false
    lootDrops:
      - item: 'TNT'
        count: 1
        dropChance: 0.01
      - item: 'GUNPOWDER'
        count: 3
        dropChance: 0.5

  - skeletonIceMage:
    name: "&6Skeleton Ice Mage"
    mobID: skeletonIceMage
    champion: true
    maxHealth: 35.0
    spawnChance: 40
    entityType: SKELETON
    weapon:
      material: STICK
      amount: 1
      enchanted: true
      enchantments:
        - ARROW_SLOW
      enchantmentLevels:
        - 3
      glow: true
      unbreakable: true
      hide: false
      weaponName: "&eWand of Frost"
      weaponLore: "Casts freezing spells."
    weaponDropChance: 1.5
    hasArmour: true
    armour:
      - LEATHER_BOOTS
      - LEATHER_TUNIC
      - LEATHER_HELMET
    lootDrops:
      - item: 'SNOWBALL'
        count: 4
        dropChance: 0.10
      - item: 'ICE'
        count: 2
        dropChance: 0.05

  - spiderNetSpinner:
    name: "&6Spider Net Spinner"
    mobID: spiderNetSpinner
    champion: false
    maxHealth: 20.0
    spawnChance: 35
    entityType: SPIDER
    weapon:
      material: WOODEN_PICKAXE
      amount: 1
      enchanted: false
      glow: false
      unbreakable: true
      hide: true
      weaponName: "&eSpinner's Tear"
      weaponLore: "Some webs are stronger."
    weaponDropChance: 3.0
    hasArmour: false
    lootDrops:
      - item: 'SPIDER_EYE'
        count: 3
        dropChance: 0.4
      - item: 'COBWEB'
        count: 1
        dropChance: 0.2

  - witchBrewMaster:
    name: "&6Witch Brew Master"
    mobID: witchBrewMaster
    champion: true
    maxHealth: 30.0
    spawnChance: 40
    entityType: WITCH
    hasArmour: false
    lootDrops:
      - item: 'POTION'
        count: 1
        dropChance: 0.05
        potion:
          type: 'INSTANT_DAMAGE'
          extended: false
          upgraded: true
      - item: 'REDSTONE'
        count: 2
        dropChance: 0.4

  - ironGolemProtector:
    name: "&6Iron Golem Protector"
    mobID: ironGolemProtector
    champion: true
    maxHealth: 80.0
    spawnChance: 50
    entityType: IRON_GOLEM
    hasArmour: false
    lootDrops:
      - item: 'IRON_INGOT'
        count: 5
        dropChance: 0.50
      - item: 'POPPY'
        count: 1
        dropChance: 0.40

  - phantomNightmare:
    name: "&6Phantom Nightmare"
    mobID: phantomNightmare
    champion: false
    maxHealth: 30.0
    spawnChance: 60
    entityType: PHANTOM
    hasArmour: false
    lootDrops:
      - item: 'PHANTOM_MEMBRANE'
        count: 1
        dropChance: 0.20
      - item: 'ENDER_PEARL'
        count: 1
        dropChance: 0.10

  - endermanPortalMaster:
    name: "&6Enderman Portal Master"
    mobID: endermanPortalMaster
    champion: false
    maxHealth: 40.0
    spawnChance: 45
    entityType: ENDERMAN
    hasArmour: false
    lootDrops:
      - item: 'ENDER_PEARL'
        count: 1
        dropChance: 0.50
      - item: 'ENDER_EYE'
        count: 1
        dropChance: 0.10

  - vindicatorChief:
    name: "&6Vindicator Chief"
    mobID: vindicatorChief
    champion: true
    maxHealth: 50.0
    spawnChance: 50
    entityType: VINDICATOR
    weapon:
      material: DIAMOND_AXE
      amount: 1
      enchanted: true
      enchantments:
        - DAMAGE_ALL
      enchantmentLevels:
        - 5
      glow: true
      unbreakable: true
      hide: false
      weaponName: "&eChief's Beheader"
      weaponLore: "Respect is earned."
    weaponDropChance: 3.0
    hasArmour: false
    lootDrops:
      - item: 'EMERALD'
        count: 2
        dropChance: 0.20
      - item: 'TOTEM_OF_UNDYING'
        count: 1
        dropChance: 0.01

  - ghastFlameBringer:
    name: "&6Ghast Flame Bringer"
    mobID: ghastFlameBringer
    champion: false
    maxHealth: 30.0
    spawnChance: 65
    entityType: GHAST
    hasArmour: false
    lootDrops:
      - item: 'GUNPOWDER'
        count: 2
        dropChance: 0.30
      - item: 'GHAST_TEAR'
        count: 1
        dropChance: 0.10

customBossMobs:
  - skeletonKing:
    name: 'Skeleton King'
    mobID: 'skeletonKing'
    champion: true
    maxHealth: 125.0
    spawnChance: 20
    entityType: 'SKELETON'
    weapon:
      material: 'DIAMOND_SWORD'
      amount: 1
      enchanted: true
      enchantments:
        - 'SHARPNESS'
        - 'KNOCKBACK'
      enchantmentLevels:
        - 5
        - 2
      glow: true
      unbreakable: true
      hide: true
      weaponName: 'Example Sword'
      weaponLore: 'A sword that once belonged to the Skeleton King.'
    weaponDropChance: 0.5
    hasArmour: true
    armour:
      - 'DIAMOND_BOOTS'
      - 'DIAMOND_LEGGINGS'
      - 'DIAMOND_CHESTPLATE'
      - 'DIAMOND_HELMET'
    abilities:
      - 'INCREASE_DAMAGE'
      - 'SPEED'
    abilityLevels:
      - 2
      - 1
    attacks:
      - 'FIREBALL_ATTACK'
      - 'TELEPORT_ATTACK'
      - 'METEOR_ATTACK'
    lootDrops:
      - item: 'DIAMOND'
        count: 5
        dropChance: 0.5
      - item: 'GOLD_INGOT'
        count: 3
        dropChance: 0.4
```

Makes use of a custom comamnd system for ease of use and registration of commands
```java
/**
 * CommandCore, Used as a base for all in-game plugins commands with built in cooldown system and perm check
 */
public abstract class CommandCore implements CommandExecutor, TabExecutor {
    protected String commandName;
    protected String permission;
    protected int cooldownDuration;
    protected Map<UUID, Long> cooldowns = new HashMap<>();

    public CommandCore(String cmdName, @Nullable String permission, int cooldown) {
        CustomMobsControl.getInstance().getCommand(cmdName).setExecutor(this);
        this.commandName = cmdName;
        this.permission = permission;
        this.cooldownDuration = cooldown;
    }

    protected abstract void execute(Player player, String[] args);
    protected abstract List<String> commandCompletion(Player player, Command command, String[] strings);

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (commandSender instanceof Player) {

            Player player = (Player) commandSender;

            if (!player.hasPermission(this.permission)) {
                player.sendMessage(ChatColor.RED + "You don't have permission to use this command, please contact a server administrator");
                return true;
            }

            UUID playerID = player.getUniqueId();
            if (cooldowns.containsKey(playerID)) {
                long cooldownEnds = cooldowns.get(playerID);
                if (cooldownEnds > System.currentTimeMillis()) {
                    return true;
                }
            }
            cooldowns.put(playerID, System.currentTimeMillis() + cooldownDuration * 1000);

            execute(player, strings);
            return true;
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return commandCompletion((Player) commandSender, command, strings);
    }

}
```
