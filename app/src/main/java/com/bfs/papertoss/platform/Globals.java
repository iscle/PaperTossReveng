package com.bfs.papertoss.platform;

import android.content.Context;
import android.os.Handler;
import com.bfs.papertoss.PaperTossActivity;
import com.bfs.papertoss.cpp.TextureMgr;
import javax.microedition.khronos.opengles.GL10;

/* JADX INFO: loaded from: classes.dex */
public class Globals {
    public static boolean HI_RES;
    public static float SCALE_FACTOR;
    public static int SURFACE_H;
    public static int VIEWPORT_H;
    public static int VIEWPORT_W;
    public static int VIEWPORT_X;
    public static int VIEWPORT_Y;
    public static PaperTossActivity m_activity = null;
    public static Context m_context = null;
    public static GL10 GL = null;
    public static TextureMgr texture_mgr = new TextureMgr();
    public static boolean first_launch = true;
    public static Handler mainHandler = null;
    public static SoundMgr soundMgr = null;
    public static int STARTS_ANY_VERSION = 0;
}
