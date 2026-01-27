package com.bfs.papertoss;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;

import com.bfs.papertoss.platform.Config;
import com.bfs.papertoss.platform.Evt;
import com.bfs.papertoss.platform.Globals;
import com.bfs.papertoss.vector.v2f;

/* loaded from: classes.dex */
public class PapertossGLSurfaceView extends GLSurfaceView {
    private Evt evt;
    private PapertossRenderer renderer;

    public PapertossGLSurfaceView(Context context) {
        super(context);
        this.renderer = new PapertossRenderer();
        setRenderer(this.renderer);
        this.evt = Evt.getInstance();
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        float x;
        try {
            float x2 = event.getX();
            float y = Globals.SURFACE_H - event.getY();
            if (Globals.HI_RES) {
                x = Globals.VIEWPORT_X + Config.ORTHO_ADJUSTMENT_F + ((Config.ADJUSTED_ORTHO_WIDTH / Globals.VIEWPORT_W) * x2);
            } else {
                x = Globals.VIEWPORT_X + ((320.0f / Globals.VIEWPORT_W) * x2);
            }
            final v2f point = new v2f(x, Globals.VIEWPORT_Y + ((480.0f / Globals.VIEWPORT_H) * y));
            String eventName = "";
            int action = event.getAction();
            switch (action) {
                case 0:
                    eventName = "onPtrDown";
                    break;
                case 1:
                case 3:
                    eventName = "onPtrUp";
                    break;
                case 2:
                    eventName = "onPtrMove";
                    break;
                default:
                    Log.e("BFS", String.format("Something very weird is up with touch events: %X", Integer.valueOf(action)));
                    break;
            }
            final String finalEventName = eventName;
            queueEvent(() -> {
                try {
                    PapertossGLSurfaceView.this.evt.publish(finalEventName, point);
                } catch (Exception e) {
                    PaperTossApplication.logErrorWithFlurry("onTouchEvent", e, "PapertossGLSurfaceView");
                }
            });
        } catch (Exception e1) {
            PaperTossApplication.logErrorWithFlurry("onTouchEvent", e1, "PapertossGLSurfaceView");
        }
        try {
            Thread.sleep(20L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }
}
