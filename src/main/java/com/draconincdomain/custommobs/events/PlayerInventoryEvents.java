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
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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

                // Use the mobName to get the correct CustomMob object
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
    public void onInventoryClickMobCreationMenu(InventoryClickEvent event) {
        if (!(event.getInventory().getHolder() instanceof MobCreationMenu)) return;

        Player player = (Player) event.getWhoClicked();

        event.setCancelled(true);
        switch (event.getSlot()) {
            case 12:
                new WeaponAttributesMenu().open(player);
                player.updateInventory();
                break;
            case 14:
                new ArmourAttributesMenu().open(player);
                player.updateInventory();
                break;
        }

    }

}
