package com.draconincdomain.custommobs.utils;

public class Random {

    private static final java.util.Random random = new java.util.Random();

    public static boolean CustomSpawn(int value) {
        return random.nextInt(value) < 40;
    }

    public static boolean SpawnChance(int value) {
        return random.nextInt(value) < 15;
    }

}
