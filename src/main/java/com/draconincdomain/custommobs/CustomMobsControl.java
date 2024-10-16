package com.draconincdomain.custommobs;

import com.draconincdomain.custommobs.core.Annotations.Commands;
import com.draconincdomain.custommobs.core.Annotations.Events;
import com.draconincdomain.custommobs.core.Annotations.Runnable;
import com.draconincdomain.custommobs.core.RPGData.BossMobData;
import com.draconincdomain.custommobs.core.RPGData.CustomEntityData;
import com.draconincdomain.custommobs.core.CustomMobManager;
import com.draconincdomain.custommobs.core.Player.PlayerDataManager;
import com.draconincdomain.custommobs.utils.Data.PlayerDataHandler;
import com.draconincdomain.custommobs.utils.Desing.ColourCode;
import com.draconincdomain.custommobs.utils.Data.MobDataHandler;
import com.draconincdomain.custommobs.core.enums.LoggerLevel;
import com.draconincdomain.custommobs.utils.Handlers.RegionManager;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

import java.util.Set;

/**
 * Core class and entry point for the Plugin.
 * Handles and deals with object instantiation, managing and logging
 */
public final class CustomMobsControl extends JavaPlugin {

    private static CustomMobsControl Instance;

    @Override
    public void onEnable() {
        // Plugin startup logic

        CustomMobLogger("Plugin is initializing", LoggerLevel.STARTUP);

        setInstance();
        registerPluginCore();
        registerObjects();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        CustomMobLogger("Plugin is shutting down", LoggerLevel.SHUTDOWN);

        MobDataHandler.getInstance().RemoveAllMobs();
    }

    private void registerPluginCore() {
        new MobDataHandler();
        new PlayerDataHandler();
        new CustomEntityData();
        new BossMobData();
        new CustomMobManager();
        new PlayerDataManager();
        new RegionManager();
    }

    private void registerObjects() {
        registerPluginCommands();
        registerEvents();
        registerRunnables();
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

        CustomMobLogger("Commands Registered Successfully", LoggerLevel.INFO);
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

        CustomMobLogger("Events Registered Successfully", LoggerLevel.INFO);
    }

    private void registerRunnables() {
        Reflections reflections = new Reflections("com.draconincdomain.custommobs.runnables", new TypeAnnotationsScanner(), new SubTypesScanner());
        Set<Class<?>> customRunnableClasses = reflections.getTypesAnnotatedWith(Runnable.class);

        for (Class<?> runableClass : customRunnableClasses) {
            try {
                runableClass.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        CustomMobLogger("Runnable Events Registered Successfully", LoggerLevel.INFO);
    }

    public void CustomMobLogger(String log, LoggerLevel loggerLevel) {

        switch (loggerLevel) {
            case INFO:
                Bukkit.getConsoleSender().sendMessage(ColourCode.colour("&5&l[CustomMobLogger]: &r" + log));
                break;
            case ERROR:
                Bukkit.getConsoleSender().sendMessage(ColourCode.colour("&4&l[CustomMobLogger]: &r" + log ));
                break;
            case STARTUP:
                Bukkit.getConsoleSender().sendMessage(ColourCode.colour("&e&l[CustomMobLogger]: &r" + log));
                break;
            case SHUTDOWN:
                Bukkit.getConsoleSender().sendMessage(ColourCode.colour("&3&l[CustomMobLogger]: &r" + log));
                break;
        }
    }

    private void setInstance() {
        Instance = this;
    }

    public static CustomMobsControl getInstance() {
        return Instance;
    }
}
