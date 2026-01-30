package com.bfs.papertoss.platform;

import android.util.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class SaveData {
    private static final String DEFAULT_GROUP = "DEFAULT_GROUP";
    private static final String SAVE_FILE = "savedata.xml";
    private static HashMap save_data = null;
    private static boolean modified = false;

    public static HashMap getGroup(String group) {
        if (group == null || group.compareTo("") == 0) {
            return save_data;
        }
        Object v = save_data.get(group);
        if (v != null) {
            if (v instanceof HashMap) {
                return (HashMap) v;
            }
            Log.w("SaveData", "Inavlid group for save data: " + group + ". Value will not be saved.");
            return null;
        }
        HashMap new_group = new HashMap();
        save_data.put(group, new_group);
        return new_group;
    }

    public static boolean load() {
        modified = false;
        FileInputStream input_stream = null;
        try {
            input_stream = Globals.m_context.openFileInput(SAVE_FILE);
        } catch (FileNotFoundException e) {
        }
        if (input_stream != null) {
            try {
                ObjectInputStream object_stream = new ObjectInputStream(input_stream);
                save_data = (HashMap) object_stream.readObject();
                object_stream.close();
                return true;
            } catch (Exception e2) {
                Log.w("SaveData", "There was an error reading the saved data.", e2);
                e2.printStackTrace();
            }
        }
        save_data = new HashMap();
        return false;
    }

    public static void save() {
        if (save_data != null && modified) {
            try {
                FileOutputStream output_stream = Globals.m_context.openFileOutput(SAVE_FILE, 0);
                ObjectOutputStream object_stream = new ObjectOutputStream(output_stream);
                object_stream.writeObject(save_data);
                object_stream.close();
                modified = false;
            } catch (Exception e) {
                Log.w("SaveData", "There was an error writing the saved data.", e);
                e.printStackTrace();
            }
        }
    }

    public static void write(Object o, String key, String group) {
        getGroup(group).put(key, o);
        modified = true;
    }

    public static void write(Object o, String key) {
        write(o, key, DEFAULT_GROUP);
    }

    public static Object read(Object def, String key, String group) {
        Object o = getGroup(group).get(key);
        return o != null ? o : def;
    }

    public static boolean read(boolean def, String key, String group) {
        return ((Boolean) read(Boolean.valueOf(def), key, group)).booleanValue();
    }

    public static boolean read(boolean def, String key) {
        return read(def, key, DEFAULT_GROUP);
    }

    public static int read(int def, String key, String group) {
        return ((Integer) read(Integer.valueOf(def), key, group)).intValue();
    }

    public static int read(int def, String key) {
        return ((Integer) read(Integer.valueOf(def), key, DEFAULT_GROUP)).intValue();
    }

    public static void main(String[] args) {
    }
}
