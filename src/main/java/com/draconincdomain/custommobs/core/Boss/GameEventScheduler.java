package com.draconincdomain.custommobs.core.Boss;

import org.bukkit.entity.LivingEntity;

public class GameEventScheduler {

    public static void scheduleSpecialAttack(LivingEntity target, String attack) {
        switch (attack) {
            case "FIREBALL_ATTACK":
                new FireballAttack(target);
                break;

            // handle other attack types ...

            default:
                System.err.println("Unknown attack type: " + attack);
                return;
        }
    }
}
