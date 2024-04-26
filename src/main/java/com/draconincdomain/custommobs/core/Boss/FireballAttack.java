package com.draconincdomain.custommobs.core.Boss;

import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;

public class FireballAttack extends BossEventScheduler{
    public FireballAttack(LivingEntity target) {
        super(target);
    }

    @Override
    protected void event() {
        // Check if the target is dead. If true, stop the task.
        if(target == null || target.isDead()) {
            this.stop();
        } else {
            // launch a fireball towards the target
            if (this.target != null && !this.target.isDead() && this.target.getWorld() != null) {
                Fireball fireball = this.target.getWorld().spawn(this.target.getLocation().add(0, 1, 0), Fireball.class);
                fireball.setDirection(this.target.getLocation().getDirection());
            }
        }
    }
}
