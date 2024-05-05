package com.draconincdomain.custommobs.core.RPGData;

import com.draconincdomain.custommobs.core.RPGMobs.BossMob;
import com.draconincdomain.custommobs.utils.Arrays.CustomEntityArrayHandler;
import com.draconincdomain.custommobs.utils.Data.MobDataHandler;

import java.util.Map;

public class BossMobData {
    private static BossMobData instance;

    public BossMobData() {
        instance = this;
        getData();
    }

    public void getData() {
        try {
            // Iterates over each boss mob configuration.
            for (Map<?, ?> bossMobMap : MobDataHandler.GetConfig().getMapList("customBossMobs")) {
                BossMob bossMob = BossMobCreation.fromMap(bossMobMap);
                CustomEntityArrayHandler.getRegisteredBossMobs().put(bossMob.getMobID(), bossMob);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static BossMob getRandomBossMob() {
        if (CustomEntityArrayHandler.getRegisteredBossMobs().isEmpty()) return null;
        int randomIndex = (int) (Math.random() * CustomEntityArrayHandler.getRegisteredBossMobs().size());
        return CustomEntityArrayHandler.getRegisteredBossMobs().get(randomIndex);
    }

    public boolean isBossMobsEnabled() {
        return MobDataHandler.isEnabled;
    }

    public static BossMobData getInstance() {
        return instance;
    }
}
