package com.draconincdomain.custommobs.events;

import com.draconincdomain.custommobs.CustomMobsControl;
import com.draconincdomain.custommobs.core.Annotations.Events;
import com.draconincdomain.custommobs.core.CustomMobManager;
import com.draconincdomain.custommobs.core.Inventory.MobGUI.Attributes.ArmourAttributesMenu;
import com.draconincdomain.custommobs.core.Inventory.MobGUI.Attributes.WeaponAttributesMenu;
import com.draconincdomain.custommobs.core.Inventory.MobGUI.Menu;
import com.draconincdomain.custommobs.core.Inventory.MobGUI.MobCreationMenu;
import com.draconincdomain.custommobs.core.Inventory.MobGUI.MobListMenu;
import com.draconincdomain.custommobs.core.RPGData.CustomEntityData;
import com.draconincdomain.custommobs.core.RPGMobs.CustomMob;
import com.draconincdomain.custommobs.core.enums.LoggerLevel;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Events
public class PlayerInventoryEvents implements Listener {
    @EventHandler
    public void onInventoryClickMenu(InventoryClickEvent event) {
        if (!(event.getInventory().getHolder() instanceof Menu)) return;

        Player player = (Player) event.getWhoClicked();

        event.setCancelled(true);

        switch (event.getSlot()) {
            case 3:
                MobListMenu mobListMenu = new MobListMenu();
                mobListMenu.open(player);
                player.updateInventory();
                break;
            case 5:
                MobCreationMenu mobCreationMenu = new MobCreationMenu();
                mobCreationMenu.open(player);
                player.updateInventory();
        }
    }

    @EventHandler
    public void onInventoryClickMobListMenu(InventoryClickEvent event) {
        if (!(event.getInventory().getHolder() instanceof MobListMenu)) return;

        Player player = (Player) event.getWhoClicked();

        event.setCancelled(true);

        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem != null) {
            ItemMeta meta = clickedItem.getItemMeta();
            if (meta != null && meta.hasDisplayName()) {
                String mobNameId = meta.getDisplayName();

                CustomMob mob = CustomEntityData.getCustomMobByName(mobNameId);

                if (mob != null) {
                    CustomMobManager.getInstance().setMobLevelAndSpawn(player, mob, player.getLocation());
                    CustomMobsControl.getInstance().CustomMobLogger("Spawning entity at player: " + player.getName(), LoggerLevel.INFO);
                } else {
                    CustomMobsControl.getInstance().CustomMobLogger("Mob not found: " + mobNameId, LoggerLevel.ERROR);
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();
        if (!(event.getInventory().getHolder() instanceof MobCreationMenu)) return;

        event.setCancelled(true);
        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null || clickedItem.getType() == Material.GRAY_STAINED_GLASS_PANE) return;

        Player player = (Player) event.getWhoClicked();
        handleItemClick(clickedItem, player, inventory);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Inventory inventory = event.getInventory();
        if (!(inventory.getHolder() instanceof MobCreationMenu)) return;

        saveMobData(inventory);
    }

    private void handleItemClick(ItemStack clickedItem, Player player, Inventory inventory) {
        String displayName = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName());

        switch (displayName) {
            case "Set Name":
                player.closeInventory();
                player.sendMessage("Type the name of the mob in chat.");
                break;
            case "Set Champion Status":
                ItemStack item = inventory.getItem(3); // Get the item at slot 3
                ItemMeta meta = item.getItemMeta();
                String currentStatus = ChatColor.stripColor(meta.getLore().get(0));
                boolean isChampion = currentStatus.equals("true");
                meta.setLore(Collections.singletonList(String.valueOf(!isChampion)));
                item.setItemMeta(meta);
                break;
            case "Set Health":
                player.closeInventory();
                player.sendMessage("Type the maximum health of the mob in chat.");
                break;
            case "Set Spawn Chance":
                player.closeInventory();
                player.sendMessage("Type the spawn chance of the mob in chat (0-100).");
                break;
            case "Set Entity Type":
                List<EntityType> entityTypes = Arrays.asList(EntityType.ZOMBIE, EntityType.SKELETON, EntityType.CREEPER);
                ItemStack entityTypeItem = inventory.getItem(22);
                ItemMeta entityTypeMeta = entityTypeItem.getItemMeta();
                String currentEntityType = ChatColor.stripColor(entityTypeMeta.getLore().get(0));
                int currentIndex = entityTypes.indexOf(EntityType.valueOf(currentEntityType));
                int nextIndex = (currentIndex + 1) % entityTypes.size();
                entityTypeMeta.setLore(Collections.singletonList(entityTypes.get(nextIndex).name()));
                entityTypeItem.setItemMeta(entityTypeMeta);
                break;
            case "Set Weapon Attributes":
                break;
            case "Set Armor":
                break;
            default:
                break;
        }
    }

    private void saveMobData(Inventory inventory) {
        CustomMob mob = createCustomMobFromInventory(inventory);
        if (mob != null) {
            CustomEntityData.getInstance().registerCustomMob(mob);
        }
    }

    private CustomMob createCustomMobFromInventory(Inventory inventory) {
        String name = ChatColor.stripColor(inventory.getItem(1).getItemMeta().getDisplayName());
        String mobID = name.toLowerCase().replace(" ", "_");
        boolean isChampion = Boolean.parseBoolean(ChatColor.stripColor(inventory.getItem(3).getItemMeta().getLore().get(0)));
        double maxHealth = Double.parseDouble(ChatColor.stripColor(inventory.getItem(5).getItemMeta().getLore().get(0)));
        int spawnChance = Integer.parseInt(ChatColor.stripColor(inventory.getItem(7).getItemMeta().getLore().get(0)));
        EntityType entityType = EntityType.valueOf(ChatColor.stripColor(inventory.getItem(22).getItemMeta().getLore().get(0)));

//        return new CustomMob(name, mobID, isChampion, maxHealth, spawnChance, entityType, null, 0, null, -1);
        return null;
    }

}
