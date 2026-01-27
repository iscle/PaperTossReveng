package com.bfs.papertoss;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import com.backflipstudios.android.debug.BFSDebug;
import com.backflipstudios.android.engine.io.BFSAssetFile;
import com.bfs.papertoss.platform.Globals;
import com.bfs.papertoss.platform.SaveData;
import com.bfs.papertoss.platform.SoundMgr;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/* loaded from: classes.dex */
public class PaperTossApplication extends Application {
    private static PaperTossApplication m_instance = null;
    private ErrorReporter errorReporter = null;
    private int m_runCount = 0;
    private HashMap<String, String> m_configurationParameters = null;

    public PaperTossApplication() {
        m_instance = this;
    }

    public static PaperTossApplication getInstance() {
        return m_instance;
    }

    @Override // android.app.Application
    public void onCreate() throws JSONException, PackageManager.NameNotFoundException, IOException {
        super.onCreate();
        BFSDebug.i("PaperTossApplication.onCreate");
        Globals.m_context = this;
        Globals.mainHandler = new Handler();
        if (Globals.soundMgr == null) {
            Globals.soundMgr = new SoundMgr();
        }
        Display display = ((WindowManager) getSystemService("window")).getDefaultDisplay();
        if (display.getHeight() > 480) {
            Globals.HI_RES = true;
            Globals.SCALE_FACTOR = 0.613027f;
        } else {
            Globals.HI_RES = false;
            Globals.SCALE_FACTOR = 1.0f;
        }
        SaveData.load();
        if (this.errorReporter == null) {
            this.errorReporter = new ErrorReporter();
            this.errorReporter.Init(this);
        }
        try {
            PackageManager pm = getPackageManager();
            PackageInfo pi = pm.getPackageInfo(getPackageName(), 0);
            String VersionName = pi.versionName;
            this.m_runCount = SaveData.read(0, VersionName, "STARTS");
            this.m_runCount++;
            SaveData.write(Integer.valueOf(this.m_runCount), VersionName, "STARTS");
        } catch (Exception e) {
            BFSDebug.e("PaperTossApplication.onCreate", e);
        }
        Globals.STARTS_ANY_VERSION = SaveData.read(0, "STARTS_ANY_VERSION");
        Globals.STARTS_ANY_VERSION++;
        SaveData.write(Integer.valueOf(Globals.STARTS_ANY_VERSION), "STARTS_ANY_VERSION");
        SaveData.save();
        loadConfigurationParameters();
    }

    @Override // android.app.Application, android.content.ComponentCallbacks
    public void onLowMemory() {
        super.onLowMemory();
        BFSDebug.i("PaperTossApplication.onLowMemory");
    }

    @Override // android.app.Application
    public void onTerminate() {
        super.onTerminate();
        BFSDebug.i("PaperTossApplication.onTerminate");
    }

    public int getRunCount() {
        return this.m_runCount;
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

    private void checkForErrorLog() throws IOException {
        if (SaveData.groupExists("CRASH")) {
            String trace = SaveData.read("empty", "trace", "CRASH");
            String cause = SaveData.read("empty", "cause", "CRASH");
            String model = SaveData.read("empty", "model", "CRASH");
            String os = SaveData.read("empty", "os", "CRASH");
            String freeMem = SaveData.read("empty", "freeMem", "CRASH");
            String totalMem = SaveData.read("empty", "TotalMem", "CRASH");
            String myFingerprint = SaveData.read("empty", "myFingerprint", "CRASH");
            Log.d("BFS", "Found crash report: " + trace + cause + model + os + freeMem + totalMem);
            HashMap<String, String> map = new HashMap<>();
            map.put("Trace", trace);
            map.put("Cause", cause);
            map.put("Model", model);
            map.put("OS", os);
            map.put("FreeMem", freeMem);
            map.put("TotalMem", totalMem);
            map.put("Model/OS", model + "/" + os);
            map.put("MyFingerprint", myFingerprint);
            if (SaveData.keyExists("Report", "CRASH")) {
                String Report = SaveData.read("", "Report", "CRASH");
                HashMap<String, String> map2 = new HashMap<>();
                for (int i = 0; Report.length() > 0 && i < 10; i++) {
                    int index = Math.min(250, Report.length());
                    String sub = Report.substring(0, index);
                    map2.put("" + i, sub);
                    Report = Report.substring(index);
                }
            }
            SaveData.deleteGroup("CRASH");
            SaveData.save();
        }
    }

    public String getBuildConfigurationParameter(String name) {
        return (this.m_configurationParameters == null || !this.m_configurationParameters.containsKey(name)) ? "" : this.m_configurationParameters.get(name);
    }

    public boolean checkBuildConfigurationParameter(String name, String value) {
        if (this.m_configurationParameters != null && this.m_configurationParameters.containsKey(name)) {
            String actualvalue = this.m_configurationParameters.get(name);
            if (actualvalue.equals(value)) {
                return true;
            }
        }
        return false;
    }

    private void loadConfigurationParameters() throws JSONException {
        try {
            byte[] data = BFSAssetFile.readContentsOfFile("configuration.json");
            if (data != null) {
                JSONTokener tokener = new JSONTokener(new String(data));
                JSONObject root = (JSONObject) tokener.nextValue();
                Iterator rootKeys = root.keys();
                while (rootKeys.hasNext()) {
                    String rootKey = rootKeys.next();
                    if ("parameters".equals(rootKey)) {
                        JSONObject parametersRoot = root.getJSONObject(rootKey);
                        Iterator parameterKeys = parametersRoot.keys();
                        while (parameterKeys.hasNext()) {
                            String parameterKey = parameterKeys.next();
                            String value = parametersRoot.optString(parameterKey);
                            if (this.m_configurationParameters == null) {
                                this.m_configurationParameters = new HashMap<>();
                            }
                            this.m_configurationParameters.put(parameterKey, value);
                        }
                    }
                }
            }
        } catch (Exception e) {
            BFSDebug.e("BFS", "BFSEngineApplication.loadConfigurationParameters()", e);
        }
    }
}
