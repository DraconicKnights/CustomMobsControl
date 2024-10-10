package com.draconincdomain.custommobs.core.RPGData;


import com.draconincdomain.custommobs.core.RPGMobs.CustomMob;
import com.draconincdomain.custommobs.utils.Arrays.CustomEntityArrayHandler;
import com.draconincdomain.custommobs.utils.Data.MobDataHandler;

import java.util.Map;

/**
 * Custom Entity Data
 * Instantiated upon plugin initialization and passes the data from the yml towards the mob builder
 */
public class CustomEntityData {

    private static CustomEntityData Instance;

    public CustomEntityData() {
        Instance = this;
        GetData();
    }

    public void GetData() {

        try {

            for (Map<?, ?> mobMap : MobDataHandler.GetConfig().getMapList("customMobs")) {

                CustomMob mob = CustomMobCreation.fromMap(mobMap);
                CustomEntityArrayHandler.getRegisteredCustomMobs().put(mob.getMobID(), mob);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static CustomMob getRandomMob() {
        if (CustomEntityArrayHandler.getRegisteredCustomMobs().isEmpty()) return null;
        int randomIndex = (int) (Math.random() * CustomEntityArrayHandler.getRegisteredCustomMobs().size());

        return CustomEntityArrayHandler.getRegisteredCustomMobs().get(randomIndex);
    }

    public static CustomMob getCustomMobByName(String mobNameID) {
        return CustomEntityArrayHandler.getRegisteredCustomMobs().values().stream()
                .filter(mob -> mob.getMobNameID().equals(mobNameID))
                .findFirst()
                .orElse(null);
    }

    public void registerCustomMob(CustomMob mob) {
        CustomEntityArrayHandler.getRegisteredCustomMobs().put(mob.getMobID(), mob);
        MobDataHandler.saveCustomMobData();
    }

    public boolean isCustomMobsEnabled() {
        return MobDataHandler.isEnabled;
    }

    public static CustomEntityData getInstance() {
        return Instance;
    }

}
