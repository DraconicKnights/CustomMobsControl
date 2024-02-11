package com.draconincdomain.custommobs;

import com.draconincdomain.custommobs.commands.ReloadConfigCommand;
import com.draconincdomain.custommobs.commands.SpawnCustomEntity;
import com.draconincdomain.custommobs.events.CustomEntitySpawnEvent;
import com.draconincdomain.custommobs.core.CustomEntityData;
import com.draconincdomain.custommobs.utils.DataHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;


public final class CustomMobsControl extends JavaPlugin {

    private static CustomMobsControl Instance;

    @Override
    public void onEnable() {
        // Plugin startup logic

        CustomMobLogger("Plugin is initializing");

        setInstance();

        new DataHandler();
        new CustomEntityData();

        new SpawnCustomEntity();
        new ReloadConfigCommand();

        DataHandler.getInstance().load();
        CustomEntityData.getInstance().GetData();

        getServer().getPluginManager().registerEvents(new CustomEntitySpawnEvent(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        CustomMobLogger("Plugin is shutting down");
    }

    private void setInstance() {
        Instance = this;
    }

    public static CustomMobsControl getInstance() {
        return Instance;
    }

    public void CustomMobLogger(String log) {
        Bukkit.getServer().getLogger().info(ChatColor.DARK_PURPLE + "[CustomMobLogger]: " + ChatColor.WHITE + log);
    }
}
