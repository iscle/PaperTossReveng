package com.bfs.papertoss;

import android.app.Application;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import androidx.core.content.ContextCompat;

import com.bfs.papertoss.platform.Config;
import com.bfs.papertoss.platform.Globals;
import com.bfs.papertoss.platform.SaveData;
import com.bfs.papertoss.platform.SoundMgr;

public class PaperTossApplication extends Application {
    private static final String TAG = "PaperTossApplication";
    private static PaperTossApplication m_instance = null;

    public PaperTossApplication() {
        m_instance = this;
    }

    public static PaperTossApplication getInstance() {
        return m_instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "PaperTossApplication.onCreate");
        Globals.m_context = this;
        Globals.mainHandler = new Handler();
        if (Globals.soundMgr == null) {
            Globals.soundMgr = new SoundMgr();
        }
        Display display = ContextCompat.getSystemService(this, WindowManager.class).getDefaultDisplay();
        if (display.getHeight() > Config.SCREEN_HEIGHT) {
            Globals.HI_RES = true;
            Globals.SCALE_FACTOR = 0.613027f;
        } else {
            Globals.HI_RES = false;
            Globals.SCALE_FACTOR = 1.0f;
        }
        SaveData.load();
        Globals.STARTS_ANY_VERSION = SaveData.read(0, "STARTS_ANY_VERSION");
        Globals.STARTS_ANY_VERSION++;
        SaveData.write(Globals.STARTS_ANY_VERSION, "STARTS_ANY_VERSION");
        SaveData.save();
    }
}
