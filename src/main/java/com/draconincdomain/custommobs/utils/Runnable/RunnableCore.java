package com.draconincdomain.custommobs.utils.Runnable;

import com.draconincdomain.custommobs.CustomMobsControl;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class RunnableCore extends BukkitRunnable {
    private final CustomMobsControl plugin;
    private final long delayTicks;
    private final long periodTicks;

    public RunnableCore(CustomMobsControl plugin, long delaySeconds, long periodSeconds) {
        this.plugin = plugin;
        this.delayTicks = delaySeconds * 20L;
        this.periodTicks = periodSeconds * 20L;
    }

    protected abstract void event();

    @Override
    public void run() {
        event();
    }

    public void startTimedTask() {
        this.runTaskTimer(plugin, delayTicks, periodTicks);
    }

    public void startTimedAsyncTask() {
        this.runTaskTimerAsynchronously(plugin, delayTicks, periodTicks);
    }

    protected void startTaskLater() {
        this.runTaskLater(plugin, delayTicks);
    }

    protected void startTask() {
        this.runTask(plugin);
    }

    public void stop() {
        this.cancel();
    }
}
