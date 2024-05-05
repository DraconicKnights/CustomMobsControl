package com.draconincdomain.custommobs.core.Boss.Attacks;

import com.draconincdomain.custommobs.core.Boss.SpecialAttack;
import com.draconincdomain.custommobs.core.RPGMobs.BossMob;
import org.bukkit.entity.LivingEntity;

public class BossBarActive extends SpecialAttack {
    public BossBarActive(LivingEntity target) {
        super(target);
    }

    @Override
    protected void executeAttack() {
        if (this.target instanceof BossMob) {
            BossMob boss = (BossMob) this.target;
            boss.updateNearbyPlayers();
            double progress = boss.getHealth() / boss.getMaxHealth();
            boss.getBossBar().progress((float) progress);
        }
    }
}
