package com.draconincdomain.custommobs.utils;

import com.draconincdomain.custommobs.core.CustomMob;

import java.util.ArrayList;
import java.util.List;

public class CustomEntityArrayHandler {

    public static List<CustomMob> registeredMobs = new ArrayList<>();

    public static List<CustomMob> getRegisteredCustomMobs() {
        return registeredMobs;
    }


}
