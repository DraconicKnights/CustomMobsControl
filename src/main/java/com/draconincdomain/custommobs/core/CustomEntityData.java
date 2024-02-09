package com.draconincdomain.custommobs.core;


import com.draconincdomain.custommobs.utils.CustomEntityArrayHandler;
import com.draconincdomain.custommobs.utils.DataHandler;

import java.util.Map;

public class CustomEntityData {

    private static CustomEntityData Instance;



    public CustomEntityData() {
        Instance = this;
    }

    public void GetData() {

        try {

            for (Map<?, ?> mobMap : DataHandler.GetConfig().getMapList("customMobs")) {

                Map<?, ?> weaponMap = (Map<?, ?>) mobMap.get("weapon");
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

    public boolean isCustomMobsEnabled() {
        return (boolean) DataHandler.GetConfig().get("customMobsEnabled");
    }

    public static CustomEntityData getInstance() {
        return Instance;
    }

}
