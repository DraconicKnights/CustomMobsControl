package com.draconincdomain.custommobs.runnables;

import com.draconincdomain.custommobs.CustomMobsControl;
import com.draconincdomain.custommobs.core.Annotations.Runnable;
import com.draconincdomain.custommobs.utils.Runnable.RunnableCore;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@Runnable
public class Example extends RunnableCore {

    public Example() {
        super(CustomMobsControl.getInstance(), 60, 60);
        this.startTimedAsyncTask();
    }

    @Override
    protected void event() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage("This is working");
        }
    }
}
