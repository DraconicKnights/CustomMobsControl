package com.draconincdomain.custommobs.core.Boss;

import com.draconincdomain.custommobs.CustomMobsControl;
import com.draconincdomain.custommobs.utils.Runnable.RunnableCore;
import org.bukkit.entity.LivingEntity;

public abstract class BossEventScheduler extends RunnableCore {
    protected LivingEntity target;

    public BossEventScheduler(LivingEntity target) {
        super(CustomMobsControl.getInstance(), 60, 60);
        this.target = target;
        this.startTimedAsyncTask();
    }

    public void setTarget(LivingEntity target) {
        this.target = target;
    }

    @Override
    protected abstract void event();  // Make event() abstract
}
