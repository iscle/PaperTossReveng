package com.bfs.papertoss;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;

import com.bfs.papertoss.platform.Config;
import com.bfs.papertoss.platform.Evt;
import com.bfs.papertoss.platform.Globals;
import com.bfs.papertoss.vector.v2f;

public class PapertossGLSurfaceView extends GLSurfaceView {
    private static final String TAG = "PapertossGLSurfaceView";
    private final Evt evt;

    public PapertossGLSurfaceView(Context context) {
        super(context);
        setRenderer(new PapertossRenderer());
        this.evt = Evt.getInstance();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        try {
            float x2 = event.getX();
            float x;
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
                case MotionEvent.ACTION_DOWN:
                    eventName = "onPtrDown";
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    eventName = "onPtrUp";
                    break;
                case MotionEvent.ACTION_MOVE:
                    eventName = "onPtrMove";
                    break;
                default:
                    Log.e("BFS", String.format("Something very weird is up with touch events: %X", action));
                    break;
            }
            final String finalEventName = eventName;
            queueEvent(() -> {
                try {
                    PapertossGLSurfaceView.this.evt.publish(finalEventName, point);
                } catch (Exception e) {
                    Log.e(TAG, "onTouchEvent: ", e);
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "onTouchEvent: ", e);
        }
        try {
            Thread.sleep(20L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }
}
