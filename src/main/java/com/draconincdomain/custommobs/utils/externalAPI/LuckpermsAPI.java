package com.draconincdomain.custommobs.utils.externalAPI;

import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

import javax.annotation.Nullable;

public class LuckpermsAPI {

    private static LuckpermsAPI Instance;
    public static LuckPerms luckPerms;

    public LuckpermsAPI() {
        Instance = this;
        @Nullable RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);

        luckPerms = provider.getProvider();
    }

    public LuckpermsAPI getInstance() {
        return Instance;
    }

}
