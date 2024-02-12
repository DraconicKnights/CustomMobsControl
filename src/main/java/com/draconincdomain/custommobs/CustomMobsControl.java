package com.draconincdomain.custommobs;

import com.draconincdomain.custommobs.commands.CommandCore;
import com.draconincdomain.custommobs.core.Annotations.Commands;
import com.draconincdomain.custommobs.core.Annotations.Events;
import com.draconincdomain.custommobs.core.CustomEntityData;
import com.draconincdomain.custommobs.utils.DataHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

import java.util.Set;


public final class CustomMobsControl extends JavaPlugin {

    private static CustomMobsControl Instance;

    @Override
    public void onEnable() {
        // Plugin startup logic

        CustomMobLogger("Plugin is initializing");

        setInstance();
        registerPluginCore();
        registerPluginCommands();
        registerEvents();

        DataHandler.getInstance().load();
        CustomEntityData.getInstance().GetData();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        CustomMobLogger("Plugin is shutting down");
    }

    private void registerPluginCore() {
        new DataHandler();
        new CustomEntityData();
    }

    private void registerPluginCommands() {
        Reflections reflections = new Reflections("com.draconincdomain.custommobs.commands", new TypeAnnotationsScanner(), new SubTypesScanner());
        Set<Class<?>> customCommandClasses = reflections.getTypesAnnotatedWith(Commands.class);

        for (Class<?> commandClass : customCommandClasses) {
            try {
                commandClass.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void registerEvents() {
        Reflections reflections = new Reflections("com.draconincdomain.custommobs.events", new TypeAnnotationsScanner(), new SubTypesScanner());
        Set<Class<?>> customEventClasses = reflections.getTypesAnnotatedWith(Events.class);

        for (Class<?> eventClass : customEventClasses) {
            try {
                Listener listener = (Listener) eventClass.getDeclaredConstructor().newInstance();
                Bukkit.getServer().getPluginManager().registerEvents( listener, this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
