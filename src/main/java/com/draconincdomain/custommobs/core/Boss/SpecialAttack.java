package com.draconincdomain.custommobs.core.Boss;

import org.bukkit.entity.LivingEntity;

public abstract class SpecialAttack {

    protected LivingEntity target;

    public SpecialAttack(LivingEntity target) {
        this.target = target;
    }

    protected abstract void executeAttack();

}
