package com.draconincdomain.custommobs.core.CustomEvents;

import com.draconincdomain.custommobs.core.RPGMobs.CustomMob;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class CustomEntityEvent extends Event {

    private final Player player;
    private final CustomMob customMob;

    public CustomEntityEvent(Player player, CustomMob customMob) {
        this.player = player;
        this.customMob = customMob;
    }

    public Player getPlayer() {
        return player;
    }

    public CustomMob getCustomMob() {
        return customMob;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    private static final HandlerList handlers = new HandlerList();
}
