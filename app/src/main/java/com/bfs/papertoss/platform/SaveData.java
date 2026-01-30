package com.bfs.papertoss.platform;

import android.util.Log;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class SaveData {
    private static final String DEFAULT_GROUP = "DEFAULT_GROUP";
    private static final String OBFUSCATED = "OBFUSCATED";
    private static final String SAVE_FILE = "savedata.xml";
    private static final String[] BUCKETS = {"raught8734yewrliu", "y34qq87rweyufhie", "UYGO*^Tyg^*tYJGo86tU", "GO7ti76RFhgF76rTJYT", "LUYTi76rtf&^r", "$#@tr3w$T@qRWasT$@Qwq1", "_)I[oI0[i{io-0iO:i", "MBNVCHGcmnBYT%786%76%", "��TERWy54wTE", "�a���o�y�����������"};
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

    public static double read(double def, String key, String group) {
        return ((Double) read(Double.valueOf(def), key, group)).doubleValue();
    }

    public static String read(String def, String key, String group) {
        return (String) read((Object) def, key, group);
    }

    public static void obfuscatedWrite(int n, String key) {
        int time = (int) System.currentTimeMillis();
        String hash = getSHA(n + "-" + time);
        write(Integer.valueOf(n), key, OBFUSCATED);
        write(Integer.valueOf(time), key + "modifier", OBFUSCATED);
        write(hash, key + "hash", OBFUSCATED);
    }

    public static int obfuscatedRead(int def, String key) {
        int n = read(0, key, OBFUSCATED);
        int time = read(0, key + "modifier", OBFUSCATED);
        String hash = read("", key + "hash", OBFUSCATED);
        String newHash = getSHA(n + "-" + time);
        return newHash.compareTo(hash) == 0 ? n : def;
    }

    public static boolean obfuscatedKeyExists(String key) {
        return keyExists(key, OBFUSCATED);
    }

    public static void deleteKey(String key, String group) {
        getGroup(group).remove(key);
        modified = true;
    }

    public static void deleteGroup(String group) {
        save_data.remove(group);
        modified = true;
    }

    public static boolean groupExists(String group) {
        if (group == null || group.compareTo("") == 0) {
            return true;
        }
        return save_data.containsKey(group);
    }

    public static boolean keyExists(String key, String group) {
        return getGroup(group).containsKey(key);
    }

    public static String getSHA(String text) {
        try {
            String text2 = text + BUCKETS[Math.abs(text.hashCode()) % BUCKETS.length];
            MessageDigest md = MessageDigest.getInstance("SHA");
            byte[] bArr = new byte[32];
            md.update(text2.getBytes("iso-8859-1"), 0, text2.length());
            byte[] md5hash = md.digest();
            return convertToHex(md5hash);
        } catch (Exception e) {
            return "";
        }
    }

    private static String convertToHex(byte[] data) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            int halfbyte = (data[i] >>> 4) & 15;
            int two_halfs = 0;
            while (true) {
                if (halfbyte >= 0 && halfbyte <= 9) {
                    buf.append((char) (halfbyte + 48));
                } else {
                    buf.append((char) ((halfbyte - 10) + 97));
                }
                halfbyte = data[i] & 15;
                int two_halfs2 = two_halfs + 1;
                if (two_halfs >= 1) {
                    break;
                }
                two_halfs = two_halfs2;
            }
        }
        return buf.toString();
    }

    public static void main(String[] args) {
    }
}
