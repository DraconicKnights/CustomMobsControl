package com.draconincdomain.custommobs.player;

import com.draconincdomain.custommobs.core.CustomMob;
import com.draconincdomain.custommobs.core.CustomMobCreation;
import com.draconincdomain.custommobs.utils.Arrays.CustomEntityArrayHandler;
import com.draconincdomain.custommobs.utils.Data.PlayerDataHandler;

import java.util.Map;

public class CustomPlayerData {

    private static CustomPlayerData Instance;

    public CustomPlayerData() {
        Instance = this;
    }

    public void GetData() {

        try {

            for (Map<?, ?> mobMap : PlayerDataHandler.GetConfig().getMapList("customMobs")) {

                CustomMob mob = CustomMobCreation.fromMap(mobMap);
                CustomEntityArrayHandler.getRegisteredCustomMobs().put(mob.getMobID(), mob);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static CustomPlayerData getInstance() {
        return Instance;
    }
}
