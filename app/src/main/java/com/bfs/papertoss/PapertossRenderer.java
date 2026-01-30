package com.bfs.papertoss;

import android.opengl.GLSurfaceView;
import android.util.Log;

import com.bfs.papertoss.cpp.Papertoss;
import com.bfs.papertoss.platform.Evt;
import com.bfs.papertoss.platform.Globals;
import com.bfs.papertoss.platform.Util;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/* JADX INFO: loaded from: classes.dex */
public class PapertossRenderer implements GLSurfaceView.Renderer {
    private static final String TAG = "PapertossRenderer";
    double m_time = Util.getTime();

    @Override // android.opengl.GLSurfaceView.Renderer
    public void onDrawFrame(GL10 gl) {
        try {
            Globals.GL = gl;
            double time = Util.getTime();
            double elapsed = time - this.m_time;
            this.m_time = time;
            Papertoss.update(elapsed);
            if (Globals.HI_RES) {
                Globals.GL.glClear(16384);
            }
            Papertoss.render();
        } catch (Exception e) {
            PaperTossApplication.logErrorWithFlurry("onDrawFrame", e, "PapertossRenderer");
        }
    }

    @Override // android.opengl.GLSurfaceView.Renderer
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        Log.i(TAG, "PaperTossRenderer.onSurfaceChanged(): " + width + "x" + height);
        Globals.GL = gl;
        Globals.VIEWPORT_W = width;
        Globals.VIEWPORT_H = height;
        Globals.VIEWPORT_X = 0;
        Globals.VIEWPORT_Y = 0;
        Globals.SURFACE_H = height;
        Globals.GL.glViewport(Globals.VIEWPORT_X, Globals.VIEWPORT_Y, Globals.VIEWPORT_W, Globals.VIEWPORT_H);
        Globals.GL.glTexParameterf(3553, 10242, 33071.0f);
        Globals.GL.glTexParameterf(3553, 10243, 33071.0f);
        Globals.GL.glClear(16384);
        Evt.getInstance().publish("sizeGl");
    }

    @Override // android.opengl.GLSurfaceView.Renderer
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        try {
            Globals.GL = gl;
            Papertoss.initialize();
            Papertoss.unShutdown();
            Globals.GL.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
            if (Globals.first_launch) {
                Papertoss.activate();
                Globals.first_launch = false;
            }
        } catch (Exception e) {
            Log.e(TAG, "PaperTossRenderer.onSurfaceCreated", e);
        }
    }
}
