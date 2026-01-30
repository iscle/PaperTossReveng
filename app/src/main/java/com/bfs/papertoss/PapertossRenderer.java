package com.bfs.papertoss;

import static javax.microedition.khronos.opengles.GL10.GL_CLAMP_TO_EDGE;
import static javax.microedition.khronos.opengles.GL10.GL_COLOR_BUFFER_BIT;
import static javax.microedition.khronos.opengles.GL10.GL_TEXTURE_2D;
import static javax.microedition.khronos.opengles.GL10.GL_TEXTURE_WRAP_S;
import static javax.microedition.khronos.opengles.GL10.GL_TEXTURE_WRAP_T;

import android.opengl.GLSurfaceView;
import android.util.Log;

import com.bfs.papertoss.cpp.Papertoss;
import com.bfs.papertoss.platform.Evt;
import com.bfs.papertoss.platform.Globals;
import com.bfs.papertoss.platform.Util;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class PapertossRenderer implements GLSurfaceView.Renderer {
    private static final String TAG = "PapertossRenderer";
    double m_time = Util.getTime();

    @Override
    public void onDrawFrame(GL10 gl) {
        try {
            Globals.GL = gl;
            double time = Util.getTime();
            double elapsed = time - this.m_time;
            this.m_time = time;
            Papertoss.update(elapsed);
            if (Globals.HI_RES) {
                Globals.GL.glClear(GL_COLOR_BUFFER_BIT);
            }
            Papertoss.render();
        } catch (Exception e) {
            Log.e(TAG, "onDrawFrame: ", e);
        }
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        Log.i(TAG, "PaperTossRenderer.onSurfaceChanged(): " + width + "x" + height);
        Globals.GL = gl;
        Globals.VIEWPORT_W = width;
        Globals.VIEWPORT_H = height;
        Globals.VIEWPORT_X = 0;
        Globals.VIEWPORT_Y = 0;
        Globals.SURFACE_H = height;
        Globals.GL.glViewport(Globals.VIEWPORT_X, Globals.VIEWPORT_Y, Globals.VIEWPORT_W, Globals.VIEWPORT_H);
        Globals.GL.glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        Globals.GL.glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        Globals.GL.glClear(GL_COLOR_BUFFER_BIT);
        Evt.getInstance().publish("sizeGl");
    }

    @Override
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
