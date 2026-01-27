package com.bfs.papertoss.cpp;

import com.bfs.papertoss.platform.SaveData;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/* loaded from: classes.dex */
public class Scores {
    private static String keyForLevel(int levelIndex) {
        return "level_" + levelIndex + "_best";
    }

    private static String oldStyleKey(int levelIndex) {
        return "level_" + levelIndex;
    }

    public static void init() {
    }

    public static void saveBest(int score, int levelIndex) throws NoSuchAlgorithmException, IOException {
        SaveData.obfuscatedWrite(score, keyForLevel(levelIndex));
        SaveData.save();
    }

    public static int readBest(int levelIndex) {
        return SaveData.obfuscatedRead(0, keyForLevel(levelIndex));
    }

    public static void saveSubmitted(int score, int levelIndex) throws IOException {
        SaveData.write(Integer.valueOf(score), oldStyleKey(levelIndex), "submitted");
        SaveData.save();
    }

    public static int readSubmitted(int levelIndex) {
        return SaveData.read(0, oldStyleKey(levelIndex), "submitted");
    }
}
