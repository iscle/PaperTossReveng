package com.bfs.papertoss;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.bfs.papertoss.platform.Globals;
import com.bfs.papertoss.platform.SaveData;
import com.bfs.papertoss.platform.SoundMgr;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;

/* loaded from: classes.dex */
public class PaperTossApplication extends Application {
    private static final String TAG = "PaperTossApplication";
    private static PaperTossApplication m_instance = null;
    private int m_runCount = 0;

    public PaperTossApplication() {
        m_instance = this;
    }

    public static PaperTossApplication getInstance() {
        return m_instance;
    }

    @Override // android.app.Application
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "PaperTossApplication.onCreate");
        Globals.m_context = this;
        Globals.mainHandler = new Handler();
        if (Globals.soundMgr == null) {
            Globals.soundMgr = new SoundMgr();
        }
        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        if (display.getHeight() > 480) {
            Globals.HI_RES = true;
            Globals.SCALE_FACTOR = 0.613027f;
        } else {
            Globals.HI_RES = false;
            Globals.SCALE_FACTOR = 1.0f;
        }
        SaveData.load();
        try {
            PackageManager pm = getPackageManager();
            PackageInfo pi = pm.getPackageInfo(getPackageName(), 0);
            String VersionName = pi.versionName;
            this.m_runCount = SaveData.read(0, VersionName, "STARTS");
            this.m_runCount++;
            SaveData.write(Integer.valueOf(this.m_runCount), VersionName, "STARTS");
        } catch (Exception e) {
            Log.e(TAG, "PaperTossApplication.onCreate", e);
        }
        Globals.STARTS_ANY_VERSION = SaveData.read(0, "STARTS_ANY_VERSION");
        Globals.STARTS_ANY_VERSION++;
        SaveData.write(Integer.valueOf(Globals.STARTS_ANY_VERSION), "STARTS_ANY_VERSION");
        SaveData.save();
    }

    @Override // android.app.Application, android.content.ComponentCallbacks
    public void onLowMemory() {
        super.onLowMemory();
        Log.i(TAG, "PaperTossApplication.onLowMemory");
    }

    @Override // android.app.Application
    public void onTerminate() {
        super.onTerminate();
        Log.i(TAG, "PaperTossApplication.onTerminate");
    }

    public static void logErrorWithFlurry(String id, Exception e, String classtype) {
        try {
            Log.d("BFS", "logErrorWithFlurry", e);
            Writer result = new StringWriter();
            PrintWriter printWriter = new PrintWriter(result);
            e.printStackTrace(printWriter);
            String stacktrace = result.toString();
            String Report = "" + stacktrace;
            String Report2 = Report + "\nCause : \n";
            for (Throwable cause = e.getCause(); cause != null; cause = cause.getCause()) {
                cause.printStackTrace(printWriter);
                Report2 = Report2 + result.toString();
            }
            printWriter.close();
            int causedByIndex = Report2.indexOf("Caused by");
            String causedBy = "empty";
            if (causedByIndex >= 0) {
                causedBy = Report2.substring(causedByIndex, Math.min(causedByIndex + 250, Report2.length()));
            }
            HashMap<String, String> map = new HashMap<>();
            map.put("Trace", Report2.substring(0, Math.min(250, Report2.length())));
            map.put("Cause", causedBy);
            map.put("In method", id);
        } catch (Exception e2) {
        }
    }

    public static String getFirstLineOfException(Exception e) {
        try {
            Log.d("BFS", "getFirstLineOfException", e);
            Writer result = new StringWriter();
            PrintWriter printWriter = new PrintWriter(result);
            e.printStackTrace(printWriter);
            String stacktrace = result.toString();
            int size = Math.min(250, stacktrace.length());
            return stacktrace.substring(0, size);
        } catch (Exception e2) {
            return "exception - getFirstLineOfException";
        }
    }
}
