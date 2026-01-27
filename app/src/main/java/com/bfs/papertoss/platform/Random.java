package com.bfs.papertoss.platform;

/* loaded from: classes.dex */
public class Random {
    public static int randomi(int low, int high) {
        int randi = (int) (Math.random() * 2.147483647E9d);
        return (randi % ((high - low) + 1)) + low;
    }

    public static float randomf(float low, float high) {
        return (float) ((Math.random() * (high - low)) + low);
    }

    public static void main(String[] args) {
        while (true) {
            System.out.println(randomf(1.0f, 2.0f));
        }
    }
}
