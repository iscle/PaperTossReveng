package com.bfs.papertoss.platform;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.HashMap;

public class Util {
    static long start_time = System.nanoTime();
    static int m_requires_pot = -1;

    public static boolean requiresPowerOfTwo() {
        String result;
        String result2;
        if (m_requires_pot != -1) {
            return m_requires_pot == 1;
        }
        HashMap<String, String> map = new HashMap<>();
        String ext = Globals.GL.glGetString(7939);
        if (ext.contains("GL_ARB_texture_non_power_of_two")) {
            result = "NO";
            m_requires_pot = 0;
        } else {
            result = "YES";
            m_requires_pot = 1;
        }
        map.put("POT", result);
        if (ext.contains("GL_IMG_texture_compression_pvrtc")) {
            result2 = "YES";
        } else if (ext.contains("atitc")) {
            result2 = "ATITC";
        } else {
            result2 = "NO";
        }
        map.put("PVRTC", result2);
        String version = Globals.GL.glGetString(7938);
        try {
            ActivityManager am = (ActivityManager) Globals.m_activity.getSystemService(Context.ACTIVITY_SERVICE);
            ConfigurationInfo info = am.getDeviceConfigurationInfo();
            Field f = info.getClass().getField("reqGlEsVersion");
            int i = f.getInt(info);
            version = String.format("%X", Integer.valueOf(i));
        } catch (Exception e) {
        }
        map.put("VERSION", version);
        String renderer = Globals.GL.glGetString(7937);
        String vendor = Globals.GL.glGetString(7936);
        map.put("RENDERER/VENDOR", renderer + "/" + vendor);
        map.put("RENDERER/VENDOR/MODEL", renderer + "/" + vendor + "/" + Build.MODEL);
        return m_requires_pot == 1;
    }

    public static int nextPowerOfTwo(int n) {
        for (int i = 1; i < Integer.MAX_VALUE; i *= 2) {
            if (i >= n) {
                return i;
            }
        }
        return 0;
    }

    public static Bitmap makePowerOfTwo(Bitmap original) {
        if (requiresPowerOfTwo()) {
            int width = original.getWidth();
            int height = original.getHeight();
            int pow_width = nextPowerOfTwo(width);
            int pow_height = nextPowerOfTwo(height);
            Bitmap data = Bitmap.createBitmap(pow_width, pow_height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(data);
            canvas.drawBitmap(original, 0.0f, 0.0f, new Paint());
            original.recycle();
            return data;
        }
        return original;
    }

    public static FloatBuffer getFloatBufferFromFloatArray(float[] array) {
        ByteBuffer tempBuffer = ByteBuffer.allocateDirect(array.length * 4);
        tempBuffer.order(ByteOrder.nativeOrder());
        FloatBuffer buffer = tempBuffer.asFloatBuffer();
        buffer.put(array);
        buffer.position(0);
        return buffer;
    }

    public static double getTime() {
        long ms = System.nanoTime() - start_time;
        double t = ms * 0.001d * 1.0E-6d;
        return t;
    }

    public static float radians(float d) {
        return (3.1415927f * d) / 180.0f;
    }

    public static float degrees(float r) {
        return (180.0f * r) / 3.1415927f;
    }
}
