package com.draconincdomain.custommobs.core.Boss;

import com.draconincdomain.custommobs.CustomMobsControl;
import com.draconincdomain.custommobs.core.Boss.Attacks.FireballAttack;
import com.draconincdomain.custommobs.core.Boss.Attacks.MultiMeteorAttack;
import com.draconincdomain.custommobs.core.Boss.Attacks.TeleportAttack;
import com.draconincdomain.custommobs.core.enums.LoggerLevel;
import com.draconincdomain.custommobs.utils.Runnable.RunnableCore;
import org.bukkit.entity.LivingEntity;

import java.util.*;

public class GameEventScheduler extends RunnableCore {
    private static Map<UUID, SpecialAttack> specialAttacks = new HashMap<>();
    private LivingEntity target;
    private String[] attacks;

    public GameEventScheduler(LivingEntity target, String... attacks) {
        super(CustomMobsControl.getInstance(), 0, 20);  // Run at fixed rate, every 1200 ticks

        this.target = target;
        this.attacks = attacks;
        this.startTimedTask();
    }

    @Override
    protected void event() {  // Override the 'event' method to provide your own logic.
        // If the target is alive, schedule a special attack
        if (!target.isDead()) {
            scheduleSpecialAttack(target, attacks);
            CustomMobsControl.getInstance().CustomMobLogger("Scheduled a special attack for: " + target.getName(), LoggerLevel.INFO);  // Log message
        } else {
            this.cancel();  // Use the inherited 'cancel' method to stop the task.
            CustomMobsControl.getInstance().CustomMobLogger("The target is dead. Stopped scheduling attacks for: " + target.getName(), LoggerLevel.INFO);  // Log message
        }
    }

    public static void scheduleSpecialAttack(LivingEntity target, String... attacks) {
        // Select a random attack from the available ones
        Random random = new Random();
        String selectedAttack = attacks[random.nextInt(attacks.length)];

        SpecialAttack attack;
        switch (selectedAttack) {
            case "FIREBALL_ATTACK":
                attack = new FireballAttack(target);
                CustomMobsControl.getInstance().CustomMobLogger("Fireball Attack has been set Active for: " + target.getName(), LoggerLevel.INFO);
                break;

            case "TELEPORT_ATTACK":
                attack = new TeleportAttack(target);
                CustomMobsControl.getInstance().CustomMobLogger("Teleport Attack has been set Active for: " + target.getName(), LoggerLevel.INFO);
                break;

            case "METEOR_ATTACK":
                attack = new MultiMeteorAttack(target);
                CustomMobsControl.getInstance().CustomMobLogger("Meteor Attack has been set Active for: " + target.getName(), LoggerLevel.INFO);
                break;

            // handle other attack types ...

            default:
                System.err.println("Unknown attack type: " + selectedAttack);
                return;
        }

        // Start and store reference to the attack
        attack.executeAttack();
        specialAttacks.put(target.getUniqueId(), attack);
    }
}
