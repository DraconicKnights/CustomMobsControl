package com.draconincdomain.custommobs.core.Boss;

import org.bukkit.entity.LivingEntity;

public class GameEventScheduler {

    public static void scheduleSpecialAttack(LivingEntity target, String attack) {
        BossEventScheduler attackTask;
        switch (attack) {
            case "FIREBALL_ATTACK":
                attackTask = new FireballAttack(target);
                break;

            // handle other attack types ...

            default:
                System.err.println("Unknown attack type: " + attack);
                return;
        }
    }
}
