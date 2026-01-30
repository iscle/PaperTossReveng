package com.bfs.papertoss.platform;

public class Random {
    public static int randomi(int low, int high) {
        int randi = (int) (Math.random() * 2.147483647E9d);
        return (randi % ((high - low) + 1)) + low;
    }

    public static float randomf(float low, float high) {
        return (float) ((Math.random() * ((double) (high - low))) + ((double) low));
    }
}
