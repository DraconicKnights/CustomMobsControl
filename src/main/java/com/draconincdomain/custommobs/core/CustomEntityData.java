package com.draconincdomain.custommobs.core;


import com.draconincdomain.custommobs.utils.Arrays.CustomEntityArrayHandler;
import com.draconincdomain.custommobs.utils.Data.MobDataHandler;

import java.util.Map;

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

    public boolean isCustomMobsEnabled() {
        return MobDataHandler.isEnabled;
    }

    public static CustomEntityData getInstance() {
        return Instance;
    }

}
